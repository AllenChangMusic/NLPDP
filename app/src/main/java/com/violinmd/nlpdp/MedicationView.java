package com.violinmd.nlpdp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.multilevelview.MultiLevelRecyclerView;
import com.multilevelview.models.RecyclerViewItem;
import com.violinmd.nlpdp.RecyclerItem.RecyclerItem;
import com.violinmd.nlpdp.RecyclerItem.RecyclerViewAdapterMed;

import java.util.ArrayList;
import java.util.List;

public class MedicationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        Intent i = getIntent();
        Medication med = (Medication) i.getSerializableExtra("medication");
        String name = "";
        for(int k=1;k<med.brand_name.length();k++){
            if(Character.isDigit(med.brand_name.charAt(k))&&Character.isSpaceChar(med.brand_name.charAt(k-1))){
                name = med.brand_name.substring(0,k-1)+"\r\n"+med.brand_name.substring(k);
                break;
            }
        }
        if (name.length()==0){
            name=med.brand_name;
        }
        toolBarLayout.setTitle(name);
        if(med.auth.toLowerCase().contains("open")){
            toolBarLayout.setBackgroundColor(Color.parseColor("#089000"));
            toolbar.setBackgroundColor(Color.parseColor("#089000"));
        } else if (med.auth.toLowerCase().contains("special")){
            toolBarLayout.setBackgroundColor(Color.parseColor("#990000"));
            toolbar.setBackgroundColor(Color.parseColor("#990000"));
        }

        LinearLayout nestedScrollView = findViewById(R.id.med_scroll);
        MultiLevelRecyclerView multiLevelRecyclerView = new MultiLevelRecyclerView(getApplicationContext());
        multiLevelRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<RecyclerItem> itemList = (List<RecyclerItem>) recursivePopulate1(med);
        RecyclerViewAdapterMed myAdapter = new RecyclerViewAdapterMed(getApplicationContext(), itemList, multiLevelRecyclerView);
        multiLevelRecyclerView.setAdapter(myAdapter);
        nestedScrollView.addView(multiLevelRecyclerView);

    }

    private List<?> recursivePopulate1(Medication med) {
        List<RecyclerViewItem> itemList = new ArrayList<>();

        RecyclerItem item = new RecyclerItem(0);
        item.setSecondText("BRAND NAME:");
        item.setText(med.brand_name);
        itemList.add(item);

        RecyclerItem item2 = new RecyclerItem(0);
        item2.setSecondText("GENERIC NAME:");
        item2.setText(med.generic_name);
        itemList.add(item2);

        RecyclerItem item3 = new RecyclerItem(0);
        item3.setSecondText("STRENGTH:");
        item3.setText(med.strength);
        itemList.add(item3);

        RecyclerItem item4 = new RecyclerItem(0);
        item4.setSecondText("DIN:");
        item4.setText(med.DIN);
        itemList.add(item4);

        RecyclerItem item5 = new RecyclerItem(0);
        item5.setSecondText("DOSAGE FORM:");
        item5.setText(med.form);
        itemList.add(item5);

        RecyclerItem item6 = new RecyclerItem(0);
        item6.setSecondText("MANUFACTURER:");
        item6.setText(med.manufacturer);
        itemList.add(item6);

        RecyclerItem item7 = new RecyclerItem(0);
        item7.setSecondText("BENEFIT STATUS:");
        item7.setText(med.auth);
        if(med.auth_url!= null && med.auth_url.length()>0){
            item7.setUrl(med.auth_url);
        }
        itemList.add(item7);

        RecyclerItem item8 = new RecyclerItem(0);
        item8.setSecondText("LIMITATIONS:");
        item8.setText(med.limitation);
        itemList.add(item8);

        RecyclerItem item9 = new RecyclerItem(0);
        item9.setSecondText("PRICING:");
        if(med.pricing.size()==0){
            item9.setText("NONE");
            itemList.add(item9);
        } else {
            item9.setText(med.pricing.get(0)[0] + med.form+" / $"+med.pricing.get(0)[1]);
            itemList.add(item9);
            for(int k=1;k<med.pricing.size();k++){
                RecyclerItem tempitem = new RecyclerItem(0);
                tempitem.setText(med.pricing.get(k)[0] + med.form+" / $"+med.pricing.get(k)[1]);
                itemList.add(tempitem);
            }
        }

        RecyclerItem item10 = new RecyclerItem(0);
        item10.setSecondText("INTERCHANGEABLE DRUG PRODUCTS:");
        if(med.interchangeable_product.size()==0){
            item10.setText("None");
            itemList.add(item10);
        } else {
            item10.setText(med.interchangeable_product.get(0)[0]);
            item10.setUrl(med.interchangeable_product.get(0)[1]);
            itemList.add(item10);
            for(int k=1;k<med.interchangeable_product.size();k++){
                RecyclerItem tempitem = new RecyclerItem(0);
                tempitem.setText(med.interchangeable_product.get(k)[0]);
                tempitem.setUrl(med.interchangeable_product.get(k)[1]);
                itemList.add(tempitem);
            }
        }


        RecyclerItem item11 = new RecyclerItem(0);
        item11.setSecondText("INTERCHANGEABLE UNIT PRICE:");
        item11.setText(med.interchangeable_price);
        itemList.add(item11);

        RecyclerItem item12 = new RecyclerItem(0);
        item12.setSecondText("ATC:");
        item12.setText(med.atc);
        itemList.add(item12);

        return itemList;
    }



}