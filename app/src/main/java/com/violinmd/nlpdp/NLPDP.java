package com.violinmd.nlpdp;

import org.violinMD.Medication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class NLPDP {

    public static ArrayList<String> options = new ArrayList<>();

    public static ArrayList<Medication> NLPDPsearchName(String s) {
        try {
            URL url = new URL("https://www.health.gov.nl.ca/health/prescription/search_results.asp");
            String urlParameters  = "GName="+s+"&GReturn=GenericName&From_Formulary=1";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }
            if (conn.getResponseCode() == 200) {
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                ArrayList<Medication> meds = new ArrayList<>();
                Medication m = new Medication();
                int counter = 0;
                while((output=br.readLine())!=null) {
                    if(output.contains("<td valign=\"top\">")) {
                        counter++;
                        if(counter>5) {
                            counter=counter-5;
                        }
                        if(counter==1) {
                            m = new Medication();
                            String temp = br.readLine().trim();
                            m.brand_name = temp.substring(temp.indexOf(">")+1, temp.lastIndexOf("<"));
                            m.tc_atc_number = "https://www.health.gov.nl.ca/"+ temp.substring(temp.indexOf("\""),temp.lastIndexOf("\""));
                        }
                        if(counter==2) {
                            m.schedule_name = br.readLine().trim();
                        }
                        if(counter==3) {
                            m.pharmaceutical_form_name = br.readLine().trim();
                        }
                        if(counter==4) {
                            m.din = br.readLine().trim();
                        }
                        if(counter==5) {
                            m.route_of_administration_name = br.readLine().trim();
                            meds.add(m);
                        }
                    }
                }
                return meds;
            } else if (conn.getResponseCode() != 200) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("REST GET Error:" + e);
        }
        return null;
    }

    public static ArrayList<Medication> NLPDPsearchDIN(String s) {
        try {
            URL url = new URL("https://www.health.gov.nl.ca/health/prescription/search_results.asp");
            String urlParameters  = "MDIN="+s+"&GReturn=DrugDin&submit=Search";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }
            if (conn.getResponseCode() == 200) {
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                ArrayList<Medication> meds = new ArrayList<>();
                Medication m = new Medication();
                int counter = 0;
                while((output=br.readLine())!=null) {
                    if(output.contains("<td valign=\"top\">")) {
                        counter++;
                        if(counter>5) {
                            counter=counter-5;
                        }
                        if(counter==1) {
                            m = new Medication();
                            String temp = br.readLine().trim();
                            m.brand_name = temp.substring(temp.indexOf(">")+1, temp.lastIndexOf("<"));
                            m.tc_atc_number = "https://www.health.gov.nl.ca/"+ temp.substring(temp.indexOf("\""),temp.lastIndexOf("\""));
                        }
                        if(counter==2) {
                            m.schedule_name = br.readLine().trim();
                        }
                        if(counter==3) {
                            m.pharmaceutical_form_name = br.readLine().trim();
                        }
                        if(counter==4) {
                            m.din = br.readLine().trim();
                        }
                        if(counter==5) {
                            m.route_of_administration_name = br.readLine().trim();
                            meds.add(m);
                        }
                    }
                }
                return meds;
            } else if (conn.getResponseCode() != 200) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("REST GET Error:" + e);
        }
        return null;
    }

    public static ArrayList<Medication> NLPDPsearchSelect(String s) {
        try {
            URL url = new URL("https://www.health.gov.nl.ca/health/prescription/search_results.asp");
            String urlParameters  = "MDESCRIPT="+s+"&GReturn=Subgroup&submit=Search+";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }
            if (conn.getResponseCode() == 200) {
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                ArrayList<Medication> meds = new ArrayList<>();
                Medication m = new Medication();
                int counter = 0;
                while((output=br.readLine())!=null) {
                    if(output.contains("<td valign=\"top\">")) {
                        counter++;
                        if(counter>5) {
                            counter=counter-5;
                        }
                        if(counter==1) {
                            m = new Medication();
                            String temp = br.readLine().trim();
                            m.brand_name = temp.substring(temp.indexOf(">")+1, temp.lastIndexOf("<"));
                            m.tc_atc_number = "https://www.health.gov.nl.ca/"+ temp.substring(temp.indexOf("\""),temp.lastIndexOf("\""));
                        }
                        if(counter==2) {
                            m.schedule_name = br.readLine().trim();
                        }
                        if(counter==3) {
                            m.pharmaceutical_form_name = br.readLine().trim();
                        }
                        if(counter==4) {
                            m.din = br.readLine().trim();
                        }
                        if(counter==5) {
                            m.route_of_administration_name = br.readLine().trim();
                            meds.add(m);
                        }
                    }
                }
                return meds;
            } else if (conn.getResponseCode() != 200) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("REST GET Error:" + e);
        }
        return null;
    }

    public static boolean loadOptions(){
        try {
            URL oracle = new URL("https://www.health.gov.nl.ca/health/prescription/newformulary.asp");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if(inputLine.contains("option value")) {
                    options.add(inputLine.substring(inputLine.indexOf(">")+1, inputLine.lastIndexOf("<")));
                }
            }
            in.close();
            options.remove(0);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
