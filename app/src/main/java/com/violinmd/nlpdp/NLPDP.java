package com.violinmd.nlpdp;

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
                            m.url = "https://www.health.gov.nl.ca"+ temp.substring(temp.indexOf("\"")+1,temp.lastIndexOf("\""));
                        }
                        if(counter==2) {
                            m.auth = br.readLine().trim();
                        }
                        if(counter==3) {
                            m.generic_name = br.readLine().trim();
                        }
                        if(counter==4) {
                            m.strength = br.readLine().trim();
                        }
                        if(counter==5) {
                            m.form = br.readLine().trim();
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
                            m.url = "https://www.health.gov.nl.ca"+ temp.substring(temp.indexOf("\"")+1,temp.lastIndexOf("\""));
                        }
                        if(counter==2) {
                            m.auth = br.readLine().trim();
                        }
                        if(counter==3) {
                            m.generic_name = br.readLine().trim();
                        }
                        if(counter==4) {
                            m.strength = br.readLine().trim();
                        }
                        if(counter==5) {
                            m.form = br.readLine().trim();
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
                            m.url = "https://www.health.gov.nl.ca"+ temp.substring(temp.indexOf("\"")+1,temp.lastIndexOf("\""));
                        }
                        if(counter==2) {
                            m.auth = br.readLine().trim();
                        }
                        if(counter==3) {
                            m.generic_name = br.readLine().trim();
                        }
                        if(counter==4) {
                            m.strength = br.readLine().trim();
                        }
                        if(counter==5) {
                            m.form = br.readLine().trim();
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

    public static Medication loadInfo(String urlstring) {
        Medication med = new Medication();
        med.url = urlstring;
        try {
            URL url = new URL(urlstring);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            while((output=br.readLine())!=null && !output.contains("<td align=\"left\" width=\"54%\">"));
            med.brand_name= br.readLine().trim();
            while((output=br.readLine())!=null && !output.contains("<td align=\"left\" width=\"54%\">"));
            med.generic_name = br.readLine().trim();
            while((output=br.readLine())!=null && !output.contains("<td align=\"left\" width=\"54%\">"));
            med.strength = br.readLine().trim();
            while((output=br.readLine())!=null && !output.contains("<td align=\"left\" width=\"54%\">"));
            med.DIN = br.readLine().trim();
            while((output=br.readLine())!=null && !output.contains("<td align=\"left\" width=\"54%\">"));
            med.form = br.readLine().trim();
            while((output=br.readLine())!=null && !output.contains("<td align=\"left\" width=\"54%\">"));
            med.manufacturer = br.readLine().trim().replace("</td>", "");
            while((output=br.readLine())!=null && !output.contains("<td align=\"left\" width=\"54%\">"));
            while((output=br.readLine())!=null && output.trim().length()==0);
            if(output.contains("href")){
                med.auth_url = "https://www.health.gov.nl.ca"+ output.substring(output.indexOf("'")+1,output.lastIndexOf("'"));
                med.auth = output.substring(output.indexOf(">")+1,output.lastIndexOf("<")).trim();
            } else {
                med.auth = output.trim();
            }
            while((output=br.readLine())!=null && !output.contains("<td align=\"left\" width=\"54%\">"));
            med.limitation = br.readLine().trim();
            while((output=br.readLine())!=null && !output.contains("Package Size"));
            String[] temp = new String[2];
            while((output=br.readLine())!=null && !output.contains("</table>")) {
                if(output.contains("<tr>")) {
                    temp[0] = br.readLine().trim().replace("<td valign=\"top\" width=\"80\" align=\"center\">", "").replace("</td>", "");
                    temp[1] = br.readLine().trim().replace("<td valign=\"top\" width=\"80\" align=\"center\">", "").replace("</td>", "").trim();
                    med.pricing.add(temp);
                    temp = new String[2];
                }
            }
            while((output=br.readLine())!=null && !output.contains("<td align=\"left\" width=\"54%\">"));
            temp = new String[2];
            while((output=br.readLine())!=null && !output.contains("</td>")) {
                if(output.contains("href")) {
                    temp[1] = "https://www.health.gov.nl.ca"+ output.substring(output.indexOf("\"")+1,output.lastIndexOf("\""))+"0";
                } else if (output.contains("</a></br>")) {
                    temp[0] = output.trim().replace("</a></br>", "");
                    med.interchangeable_product.add(temp);
                    temp = new String[2];
                }
            }
            while((output=br.readLine())!=null && !output.contains("<td align=\"left\" width=\"\">"));
            med.interchangeable_price = br.readLine().trim();
            while((output=br.readLine())!=null && !output.contains("<td align=\"left\" width=\"54%\">"));
            med.atc = br.readLine().trim();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return med;
    }


}
