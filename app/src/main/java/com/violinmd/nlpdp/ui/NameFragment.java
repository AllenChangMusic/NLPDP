package com.violinmd.nlpdp.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import static android.content.Context.INPUT_METHOD_SERVICE;

public class NameFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_name, container, false);
        EditText editText = root.findViewById(R.id.editTextName);
        Button button = root.findViewById(R.id.button_name);

        editText.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                button.performClick();
                return true;
            }
            return false;
        });

        button.setOnClickListener(v -> {
            if(editText.getText().toString().trim().length()>0){
                Thread network = new Thread() {
                    public void run() {
                        ArrayList<Medication>meds = NLPDP.NLPDPsearchName(editText.getText().toString().trim());
                        if(meds!=null && meds.size()>0){

                            InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);

                            getActivity().runOnUiThread(() -> {
                                LinearLayout scrollView = root.findViewById(R.id.scroll_name);
                                scrollView.removeAllViews();

                                MultiLevelRecyclerView multiLevelRecyclerView = new MultiLevelRecyclerView(getContext());
                                multiLevelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                List<RecyclerItem> itemList = (List<RecyclerItem>) recursivePopulate1(meds);
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), itemList, multiLevelRecyclerView);
                                multiLevelRecyclerView.setAdapter(myAdapter);
                                scrollView.addView(multiLevelRecyclerView);
                            });
                        } else {
                            getActivity().runOnUiThread(() -> {
                                LinearLayout scrollView = root.findViewById(R.id.scroll_name);
                                scrollView.removeAllViews();

                                TextView t1 = new TextView(getContext());
                                t1.setText("NO DRUGS FOUND!");
                                t1.setTextColor(Color.RED);
                                t1.setTextSize(20);
                                t1.setPadding(10,0,10,10);
                                t1.setTypeface(null, Typeface.BOLD);
                                TextView t2 = new TextView(getContext());
                                t2.setText("Check the spelling, or try an alternative generic or brand name.");
                                switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                                    case Configuration.UI_MODE_NIGHT_YES:
                                        t2.setTextColor(Color.WHITE);
                                        break;
                                    case Configuration.UI_MODE_NIGHT_NO:
                                        t2.setTextColor(Color.BLACK);
                                        break;
                                }
                                t2.setTextSize(18);
                                t2.setPadding(10,0,10,10);
                                scrollView.addView(t1);
                                scrollView.addView(t2);
                            });
                        }

                    }
                };
                network.start();
            } else {
                LinearLayout scrollView = root.findViewById(R.id.scroll_name);
                scrollView.removeAllViews();
            }
        });
        return root;
    }

    private List<?> recursivePopulate1(List<Medication> meds) {
        List<RecyclerViewItem> itemList = new ArrayList<>();

        for (int i = 0; i < meds.size(); i++) {
            RecyclerItem item = new RecyclerItem(0);
            item.setSecondText("["+meds.get(i).auth+"]");
            item.setText(meds.get(i).brand_name);
            item.addChildren((List<RecyclerViewItem>) recursivePopulate2(meds.get(i)));
            itemList.add(item);
        }
        return itemList;
    }

    private List<?> recursivePopulate2(Medication med) {
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
        item5.setMedication(med);
        itemList.add(item5);

        return itemList;
    }
}