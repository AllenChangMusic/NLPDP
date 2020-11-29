package com.violinmd.nlpdp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.multilevelview.MultiLevelRecyclerView;
import com.multilevelview.models.RecyclerViewItem;
import com.violinmd.nlpdp.NLPDP;
import com.violinmd.nlpdp.R;
import com.violinmd.nlpdp.RecyclerItem.RecyclerItem;
import com.violinmd.nlpdp.RecyclerItem.RecyclerViewAdapter;

import org.violinMD.Medication;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SelectionFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_selection, container, false);
        Spinner spinner = root.findViewById(R.id.editTextselection);
        final String[] arraySpinner = new String[NLPDP.options.size() + 1];
        arraySpinner[NLPDP.options.size()] = "Select a Medication";
        for (int i = 0; i < NLPDP.options.size(); i++) {
            arraySpinner[i] = NLPDP.options.get(i);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, arraySpinner) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        return super.getDropDownView(position, convertView, parent);
                    }

                    @Override
                    public int getCount() {
                        return arraySpinner.length - 1;
                    }
                };
                adapter.setDropDownViewResource(R.layout.layout_simplespinner);
                spinner.setAdapter(adapter);
                spinner.setSelection(arraySpinner.length - 1);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        //Log.i("Spinner", spinner.getItemAtPosition(position).toString());
                        if (position != arraySpinner.length - 1) {
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });

            }
        });

        Button button = root.findViewById(R.id.button_selection);

        button.setOnClickListener(v -> {
            if(spinner.getSelectedItemPosition()!=spinner.getCount()){
                Thread network = new Thread() {
                    public void run() {
                        ArrayList<Medication> meds = NLPDP.NLPDPsearchSelect(spinner.getSelectedItem().toString().replaceAll(" ","+"));
                        if(meds!=null){

                            getActivity().runOnUiThread(() -> {
                                LinearLayout scrollView = root.findViewById(R.id.scroll_select);
                                scrollView.removeAllViews();

                                MultiLevelRecyclerView multiLevelRecyclerView = new MultiLevelRecyclerView(getContext());
                                multiLevelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                List<RecyclerItem> itemList = (List<RecyclerItem>) recursivePopulate1(0, meds);
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), itemList, multiLevelRecyclerView);
                                multiLevelRecyclerView.setAdapter(myAdapter);
                                scrollView.addView(multiLevelRecyclerView);
                            });
                        }

                    }
                };
                network.start();
            }
        });

        return root;
    }

    private List<?> recursivePopulate1(int levelNumber, List<Medication> meds) {
        List<RecyclerViewItem> itemList = new ArrayList<>();

        for (int i = 0; i < meds.size(); i++) {
            RecyclerItem item = new RecyclerItem(levelNumber);
            item.setSecondText("["+meds.get(i).schedule_name+"]");
            item.setText(meds.get(i).brand_name);
            item.addChildren((List<RecyclerViewItem>) recursivePopulate2(1, meds.get(i)));
            itemList.add(item);
        }
        return itemList;
    }

    private List<?> recursivePopulate2(int levelNumber, Medication med) {
        List<RecyclerViewItem> itemList = new ArrayList<>();

        RecyclerItem item = new RecyclerItem(levelNumber);
        item.setSecondText("   AUTH:");
        item.setText(med.schedule_name);
        itemList.add(item);

        RecyclerItem item2 = new RecyclerItem(levelNumber);
        item2.setSecondText("PHARM:");
        item2.setText(med.pharmaceutical_form_name);
        itemList.add(item2);

        RecyclerItem item3 = new RecyclerItem(levelNumber);
        item3.setSecondText("   DOSE:");
        item3.setText(med.din);
        itemList.add(item3);

        RecyclerItem item4 = new RecyclerItem(levelNumber);
        item4.setSecondText("ROUTE:");
        item4.setText(med.route_of_administration_name);
        itemList.add(item4);

        return itemList;
    }
}