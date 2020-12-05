package com.violinmd.nlpdp;

import java.io.Serializable;
import java.util.ArrayList;

public class Medication implements Serializable {

    public String brand_name;
    public String auth;
    public String generic_name;
    public String strength;
    public String form;
    public String DIN;
    public String manufacturer;
    public String limitation;
    public ArrayList<String[]> pricing = new ArrayList<>();
    public ArrayList<String[]> interchangeable_product = new ArrayList<>();
    public String interchangeable_price;
    public String atc;
    public String url;
    public String auth_url;

}
