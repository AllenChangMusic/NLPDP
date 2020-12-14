package com.violinmd.nlpdp.RecyclerItem;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.multilevelview.MultiLevelAdapter;
import com.multilevelview.models.RecyclerViewItem;
import com.violinmd.nlpdp.Medication;
import com.violinmd.nlpdp.MedicationView;
import com.violinmd.nlpdp.NLPDP;
import com.violinmd.nlpdp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class RecyclerViewAdapterMed extends MultiLevelAdapter {

    private final Context mContext;
    private final List<RecyclerViewItem> mListItems;

    public RecyclerViewAdapterMed(Context mContext, List<RecyclerViewItem> mListItems) {
        super(mListItems);
        this.mListItems = mListItems;
        this.mContext = mContext;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclermed, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mViewHolder = (Holder) holder;
        RecyclerItem mItem = (RecyclerItem) mListItems.get(position);

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

    private static class Holder extends RecyclerView.ViewHolder {

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
                        final PackageManager packageManager = v.getContext().getPackageManager();
                        List<?> list = packageManager.queryIntentActivities(browserIntent, PackageManager.MATCH_DEFAULT_ONLY);
                        if(list.size()>0){
                            Intent chooser = Intent.createChooser(browserIntent, "Open PDF file using:");
                            chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional
                            v.getContext().startActivity(chooser);
                        } else {
                            Toast toast = Toast.makeText(v.getContext(),"No supported PDF application found!",Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.BOTTOM,0,200);
                            toast.show();
                        }

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
