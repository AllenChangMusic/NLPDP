package com.violinmd.nlpdp.RecyclerItem;

import com.multilevelview.models.RecyclerViewItem;
import com.violinmd.nlpdp.Medication;

public class RecyclerItem extends RecyclerViewItem {

    String text="";

    String secondText = "";

    Medication medication;

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
