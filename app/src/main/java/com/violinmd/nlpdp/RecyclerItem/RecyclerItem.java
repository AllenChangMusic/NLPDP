package com.violinmd.nlpdp.RecyclerItem;

import com.multilevelview.models.RecyclerViewItem;

public class RecyclerItem extends RecyclerViewItem {

    String text="";

    String secondText = "";

    public String getSecondText() {
        return secondText;
    }

    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public RecyclerItem(int level) {
        super(level);
    }
}
