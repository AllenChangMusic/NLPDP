package com.violinmd.nlpdp.RecyclerItem;

import com.multilevelview.models.RecyclerViewItem;
import com.violinmd.nlpdp.Medication;

public class RecyclerItem extends RecyclerViewItem {

    public String text="";

    public String secondText = "";

    public Medication medication;

    public String url = "";

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

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
    public Medication getMedication() {
        return medication;
    }




    public RecyclerItem(int level) {
        super(level);
    }
}
