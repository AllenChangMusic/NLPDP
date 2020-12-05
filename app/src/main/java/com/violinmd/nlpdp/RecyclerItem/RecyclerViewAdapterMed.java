package com.violinmd.nlpdp.RecyclerItem;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.multilevelview.MultiLevelAdapter;
import com.multilevelview.MultiLevelRecyclerView;
import com.violinmd.nlpdp.Medication;
import com.violinmd.nlpdp.MedicationView;
import com.violinmd.nlpdp.NLPDP;
import com.violinmd.nlpdp.R;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapterMed extends MultiLevelAdapter {

    private Holder mViewHolder;
    private final Context mContext;
    private List<RecyclerItem> mListItems = new ArrayList<>();
    private RecyclerItem mItem;
    private final MultiLevelRecyclerView mMultiLevelRecyclerView;
    private String url;

    public RecyclerViewAdapterMed(Context mContext, List<RecyclerItem> mListItems, MultiLevelRecyclerView mMultiLevelRecyclerView) {
        super(mListItems);
        this.mListItems = mListItems;
        this.mContext = mContext;
        this.mMultiLevelRecyclerView = mMultiLevelRecyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclermed, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mViewHolder = (Holder) holder;
        mItem = mListItems.get(position);

        switch (getItemViewType(position)) {
            default:
                break;
        }
        mViewHolder.urlText = mItem.getUrl();
        mViewHolder.mTitle.setText(mItem.getText());
        switch (mContext.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                mViewHolder.mTitle.setTextColor(Color.WHITE);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                mViewHolder.mTitle.setTextColor(Color.BLACK);
                break;
        }
        if (mItem.getLevel()==0){
            mViewHolder.mTitle.setTypeface(null, Typeface.BOLD);
        }
        mViewHolder.mSubtitle.setText(mItem.getSecondText().toUpperCase());
        mViewHolder.mSubtitle.setTypeface(null, Typeface.BOLD);
        switch (mContext.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                mViewHolder.mSubtitle.setTextColor(Color.WHITE);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                mViewHolder.mSubtitle.setTextColor(Color.BLACK);
                break;
        }
        if(mItem.getSecondText().toLowerCase().contains("benefit")){
            if(mItem.getText().toLowerCase().contains("open")){
                mViewHolder.mTitle.setTextColor(Color.GREEN);
            } else if (mItem.getText().toLowerCase().contains("special")){
                mViewHolder.mTitle.setTextColor(Color.RED);
            }
        }
    }

    private class Holder extends RecyclerView.ViewHolder {

        TextView mTitle, mSubtitle;
        String urlText;
        LinearLayout mTextBox;

        Holder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mSubtitle = itemView.findViewById(R.id.subtitle);
            mTextBox = itemView.findViewById(R.id.text_box);

            // The following code snippets are only necessary if you set multiLevelRecyclerView.removeItemClickListeners(); in MainActivity.java
            // this enables more than one click event on an item (e.g. Click Event on the item itself and click event on the expand button)
            itemView.setOnClickListener(v -> {
                if (urlText.length()>0){
                    if(urlText.contains("pdf")){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setDataAndType(Uri.parse(urlText), "application/pdf");
                        Intent chooser = Intent.createChooser(browserIntent, "Open PDF file using:");
                        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional
                        v.getContext().startActivity(chooser);
                    } else {
                        Thread network = new Thread() {
                            public void run() {
                                Medication newmed = NLPDP.loadInfo(urlText);
                                Intent intent = new Intent(v.getContext(), MedicationView.class);
                                intent.putExtra("medication",newmed);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                v.getContext().startActivity(intent);
                            }
                        };
                        network.start();
                    }

                }
            });
        }
    }

}
