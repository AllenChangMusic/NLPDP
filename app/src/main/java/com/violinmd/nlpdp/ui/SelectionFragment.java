package com.violinmd.nlpdp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.multilevelview.MultiLevelRecyclerView;
import com.multilevelview.models.RecyclerViewItem;
import com.violinmd.nlpdp.Medication;
import com.violinmd.nlpdp.NLPDP;
import com.violinmd.nlpdp.R;
import com.violinmd.nlpdp.RecyclerItem.RecyclerItem;
import com.violinmd.nlpdp.RecyclerItem.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectionFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_selection, container, false);
        AutoCompleteTextView spinner = root.findViewById(R.id.editTextselection);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, NLPDP.options);
        spinner.setAdapter(statusAdapter);

        Button button = root.findViewById(R.id.button_selection);

        button.setOnClickListener(v -> {
            Thread network = new Thread() {
                public void run() {
                    ArrayList<Medication> meds = NLPDP.NLPDPsearchSelect(spinner.getText().toString().replaceAll(" ","+"));
                    if(meds!=null){

                        requireActivity().runOnUiThread(() -> {
                            LinearLayout scrollView = root.findViewById(R.id.scroll_select);
                            scrollView.removeAllViews();

                            MultiLevelRecyclerView multiLevelRecyclerView = new MultiLevelRecyclerView(requireContext());
                            multiLevelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            List<RecyclerViewItem> itemList = recursivePopulate1(meds);
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), itemList);
                            multiLevelRecyclerView.setAdapter(myAdapter);
                            scrollView.addView(multiLevelRecyclerView);
                        });
                    }

                }
            };
            network.start();
        });

        return root;
    }

    private List<RecyclerViewItem> recursivePopulate1(List<Medication> meds) {
        List<RecyclerViewItem> itemList = new ArrayList<>();
        for (int i = 0; i < meds.size(); i++) {
            RecyclerItem item = new RecyclerItem(0);
            item.setSecondText("["+meds.get(i).auth+"]");
            item.setText(meds.get(i).brand_name);
            item.addChildren(recursivePopulate2(meds.get(i)));
            itemList.add(item);
        }
        return itemList;
    }

    private List<RecyclerViewItem> recursivePopulate2(Medication med) {
        List<RecyclerViewItem> itemList = new ArrayList<>();

        RecyclerItem item = new RecyclerItem(1);
        item.setSecondText("AUTH:");
        item.setText(med.auth);
        itemList.add(item);

        RecyclerItem item2 = new RecyclerItem(1);
        item2.setSecondText("PHARM:");
        item2.setText(med.generic_name);
        itemList.add(item2);

        RecyclerItem item3 = new RecyclerItem(1);
        item3.setSecondText("DOSE:");
        item3.setText(med.strength);
        itemList.add(item3);

        RecyclerItem item4 = new RecyclerItem(1);
        item4.setSecondText("ROUTE:");
        item4.setText(med.form);
        itemList.add(item4);

        RecyclerItem item5 = new RecyclerItem(1);
        item5.setText("<CLICK FOR MORE INFO>");
        item5.setUrl(med.url);
        itemList.add(item5);

        return itemList;
    }
}