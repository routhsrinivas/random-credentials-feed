/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VerifyBrokenLinks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author srinivas
 */
public class HeuristicURLDetectionScore {
    
        public int checkForNullLinks(String url, String parseHtml) throws Exception {
     
        int output = 0;
        try {
            Document doc=Jsoup.parse(parseHtml);
            String webident = "";
             URL web = new URL(url);
            String webhost = web.getHost();
            //************** login form detection *******************************//
            Elements inputElements = doc.getElementsByTag("input");

            boolean status = false;
            String key = "";
            String value = "";
            for (Element inputElement : inputElements) {
                key = inputElement.attr("name");
                value = inputElement.attr("type");
                if (value.equals("password")) {
                    status = true;
                    System.out.println("\nlogin form detected");
                }
            }
            if (!status)
                System.out.println("\nlogin form not detected");
         //   v13=Boolean.toString(status);
            //************************ login form detection ended ***************//

            Elements anchorstag1 = doc.select("div[id~=bottom|footer] a");
            Elements anchorsclass1 = doc.select("div[class~=footer|bottom] a");
            Elements footertag = doc.select("footer a");
            Elements bottomtag = doc.select("bottom a");
           // Elements copyright = doc.select(":containsOwn(©)");
            Elements linkfrequency = doc.select("a[href]");
            //String titleContent = "";
            boolean status2 = false, title = false;
            
                //************************************************ image phishing******************************
            // sri = doc.toString();//both to string and html here works same better to use html
            //System.out.println(sri);
            boolean status3 = false;
            boolean nullinbody = false;
            if(doc.body()!=null)
            {
                
            
            Elements bodylink = doc.body().select("a[href]");
            if (bodylink.isEmpty()) {
                status3 = true;
                System.out.println("Zero links in body is present  i.e image based phishing\n");
            } else {
                int count = 0;
                for (Element blink : bodylink) {
                    if (blink.attr("href").startsWith("#")) {

                        count++;
                        nullinbody = true;
                    }
                }
                if ((bodylink.size()) == count) {
                    status3 = true;
                }
            }
        }
            if (status3 && !nullinbody) {
              //  result.append("entered url is phishing site based on image phishing based detection\n");
                System.out.println("\n Entered url is phishing site based on image phishing based detection\n");
                output = 1;
             //   v19=1;
            } else if (status3 && nullinbody) {
               // result.append("entered url is phishing site based on null links body based detection\n");
                System.out.println("\n Entered url is phishing site based on null links body based detection\n");
                output = 1;
               // v19=1;
            }
            else
            {
                System.out.println("\n Entered url has links in body section\n");
             //   v19=0;
            }
             //*****************************************null links ratio in entire website detection *****************************************
            int nullLinksStatus = 0;
            if (!linkfrequency.isEmpty()) {
                for (Element ftag : linkfrequency) {
                    if (ftag.attr("href").startsWith("#")) {
                        // System.out.println("string link is :" + anchor.attr("href"));
                        nullLinksStatus++;
                    }
                }
            }
            if (nullLinksStatus>0) 
            {
                float nbval=(float)nullLinksStatus/linkfrequency.size();
                System.out.println("\n Null links ratio(website) :"+nbval );
                //v20= Float.toString(nbval); 
            } 
            else {
              //  v20="10";
                System.out.println("\n No Null links ratio(website)\n");
            }

            //*****************************************null footer links detection *****************************************
            boolean status1 = false;
            nullLinksStatus = 0;
            if (!footertag.isEmpty()) {
                for (Element ftag : footertag) {
                    if (ftag.attr("href").startsWith("#")) {
                        // System.out.println("string link is :" + anchor.attr("href"));
                        status1 = true;
                        nullLinksStatus++;
                    }
                }
            } else if (!bottomtag.isEmpty()) {
                for (Element btag : bottomtag) {
                    if (btag.attr("href").startsWith("#")) {
                        status1 = true;
                        nullLinksStatus++;
                    }
                }
            } else if (!anchorstag1.isEmpty()) {
                for (Element anchor : anchorstag1) {
                    if (anchor.attr("href").startsWith("#")) {
                        status1 = true;
                        nullLinksStatus++;
                    }
                }
            } else if (!anchorsclass1.isEmpty()) {
                Elements alinks = anchorsclass1.select("a[href]");
                for (Element link : alinks) {
                    if (link.attr("href").startsWith("#")) {
                        status1 = true;
                        nullLinksStatus++;
                    }
                }
            }
            int totalLinksFooter=0;
            totalLinksFooter=footertag.size()+bottomtag.size()+anchorstag1.size()+anchorsclass1.size();
            if (status1) {
               // System.out.println("\nEntered URL is detected as phishing site based on null footer links\n");
              //  result.append("Entered URL is detected as phishing site based on null footer links\n");
                float nfval=(float)nullLinksStatus/totalLinksFooter;
                 System.out.println("\n Null links ratio(footer) :"+ nfval);
                output = 1;
              //  v21=Float.toString(nfval);
            } else {
              //  v21="10";
                System.out.println("\n No Null links ratio(footer)\n");
            }
            //*************** Entire URL Matching in footer ***************
            Map<String, Integer> footerLink = new HashMap<>();
            if (!footertag.isEmpty()) {
        for (Element linkfreq : footertag) {
            Integer k = footerLink.get(linkfreq.attr("abs:href"));
            if (k == null) {
                k = 1;
            } else {
                k++;
            }
            footerLink.put(linkfreq.attr("abs:href"), k);
        }}
            if (!bottomtag.isEmpty()) {
        for (Element linkfreq : bottomtag) {
            Integer k = footerLink.get(linkfreq.attr("abs:href"));
            if (k == null) {
                k = 1;
            } else {
                k++;
            }
            footerLink.put(linkfreq.attr("abs:href"), k);
        }}
         if (!anchorstag1.isEmpty()) {    
        for (Element linkfreq : anchorstag1) {
            Integer k = footerLink.get(linkfreq.attr("abs:href"));
            if (k == null) {
                k = 1;
            } else {
                k++;
            }
            footerLink.put(linkfreq.attr("abs:href"), k);
        }}
         int anchorsClassSize=0;
         if (!anchorsclass1.isEmpty()) {
        Elements alinks=anchorsclass1.select("a[href]");
        anchorsClassSize=alinks.size();
        for (Element linkfreq : alinks) {
            Integer k = footerLink.get(linkfreq.attr("abs:href"));
            if (k == null) {
                k = 1;
            } else {
                k++;
            }
            footerLink.put(linkfreq.attr("abs:href"), k);
        }
         }
        int large4 = 0;
        for (Map.Entry m : footerLink.entrySet()) {

            String compare = m.getValue().toString();
            if (large4 < (Integer.parseInt(compare))) {
                large4 = Integer.parseInt(compare);
            }
        }
        if( !footertag.isEmpty()||!anchorsclass1.isEmpty()||!anchorstag1.isEmpty()||!bottomtag.isEmpty() ){
            int totalSize=footertag.size()+anchorstag1.size()+bottomtag.size()+anchorsClassSize;
            if(totalSize>1)
            {
                float umfooter=(float)large4 / totalSize;
            System.out.println("\n URL Matching Ratio(footer) : "+umfooter  );
            //v22=Float.toString(umfooter) ;
            }
        }
         else
            {
                System.out.println("\n No URL Matching Ratio(footer)"); 
            //    v22="10";
            }
        /******************* ends  *********************************/
      //*************************** URL Path Matching for entire website ***************************
        Map<String, Integer> simLink4 = new HashMap<>();
        for (Element linkfreq : linkfrequency) {
            Integer k = simLink4.get(linkfreq.attr("abs:href"));
            if (k == null) {
                k = 1;
            } else {
                k++;
            }
            simLink4.put(linkfreq.attr("abs:href"), k);
        }
        int linkScore = 0;
        for (Map.Entry m : simLink4.entrySet()) {

            String compare = m.getValue().toString();
            if (linkScore < (Integer.parseInt(compare))) {
                linkScore = Integer.parseInt(compare);
            }
        }
        if( !linkfrequency.isEmpty())
        {
           float umwebsite=(float)linkScore / linkfrequency.size();
            System.out.println("\n URL Matching Ratio(website) : "+umwebsite );
           // v23=Float.toString(umwebsite);
        }
        else{
             
            System.out.println("\n No URL Matching Ratio(website) "); 
        // v23="10";   
        }
        /*{ 
        if (large4 / links.size() == 1) {
            System.out.println(" All the links have same url and is phishing based on same path phishing");
        } else if (large4 / links.size() > 0.6) {
            System.out.println("two thirds of the links have same url and is phishing based on same path phishing");
        } else if (large4 / links.size() > 0.5) {
            System.out.println("Might be phishing based on same path phishing");
        } else {
            System.out.println("Might be legitimate based on same path phishing");
        } 
        }*/
            
            
             //******************************************** website identity with only anchor tags***********************  
           Map<String, Integer> map = new HashMap<>();
            for (Element lfreq : linkfrequency) {
                Integer n = map.get(getWebsiteIdentity(lfreq.attr("abs:href"), 0));
                if (n == null) {
                    n = 1;
                } else {
                    n++;
                }

                String webfreq = getWebsiteIdentity(lfreq.attr("abs:href"), 0);
                map.put(webfreq, n);
            }

            int largest = 0;
            for (Map.Entry m : map.entrySet()) {

                String compare = m.getValue().toString();
                if (largest < (Integer.parseInt(compare))) {
                    largest = Integer.parseInt(compare);
                }
            }
            for (Map.Entry r : map.entrySet()) {
                if (largest == (Integer.parseInt(r.getValue().toString()))) {
                    webident = (r.getKey().toString());
                }
            }
            if (getWebsiteIdentity(url, 0).equals(webident)) {
                //result.append("current domain and target domain are same.\n");
               System.out.println("\n Current domain and target domain are same.\n");
              // v24="0.5";
            } else {
                if (!webident.equalsIgnoreCase("")) {
                    //result.append("Entered URL is targeting  " + webident + "\n");
                     System.out.println("\nBased on anchor links website identity is Entered URL is targeting  " + webident + "\n");
                    // v24="1";
                }
                else {
                   // result.append("\nThere are no links to compare with suspicious link");
               System.out.println("\nThere are no links to compare with suspicious link ( Based on Anchor links website identity)");
              // v24="0";
                }
                output = 1;
            }
            
          //**************************** action field check **************************
     
        Elements actionAttribute=doc.select("form[action]");
        print("\n Action Fields:(%d)",actionAttribute.size());
        if( !actionAttribute.isEmpty()){
        String actionURL="";
         for (Element link : actionAttribute) {
            print(" * a: %s", link.attr("abs:action"));
            actionURL=link.attr("abs:action").toString();
        }
         URL newActionURL = new URL(actionURL);
            String actionURLHost = newActionURL.getHost();
            if(!StringUtils.equalsIgnoreCase(webhost,actionURLHost)){
                System.out.println("\n Action link is pointing to foreign domain");
                output=1;
            //    v30="true";
            }
            else{ 
                System.out.println("\n Action link is same as suspicious url domain");
            // v30="false";   
            }
        }
   //     else v30="false*";
           //********************************** existing of similar urls in entire page ends here***********************************//
        
              //***************************** forgot password section starts here **************************//
            Elements forgotPwdText = doc.select(":containsOwn(Forgot)");
            int forgotPWDResponse=200;
            String forgotPWDLink="";
            URL forgotLink;
            if (!forgotPwdText.isEmpty()) {
                System.out.println("forgot pwd link is" + forgotPwdText.last().ownText());
                Elements forgotLinks = forgotPwdText.last().select("a[href]");
                for (Element lfreq : forgotLinks) {
                    if(!lfreq.attr("abs:href").equalsIgnoreCase(""))
                    {    
                    if (lfreq.attr("href").startsWith("#")) {
                  //      v31="1";
                        System.out.println("forgot password links have null link");
                    }
                 forgotLink=new URL(lfreq.attr("abs:href"));
                 forgotPWDLink=forgotLink.getHost();
                  if(!StringUtils.equalsIgnoreCase(webhost,forgotPWDLink))
                  {
                     System.out.println("\n forgot link is pointing to foreign domain");
                     output=1;
                  //   v31="1";
                  }
                  else
                   //   v31="0";
                    System.out.println("forgot password links http status starts ");
                    System.out.println(lfreq.attr("abs:href"));
                   forgotPWDResponse= notFoundLinks(lfreq.attr("abs:href"));
                }
                  //  else v31="0.5";
                }
                if(forgotPWDResponse!=200&&forgotPWDResponse!=0)
                {
                //    v31="1";
                    System.out.println("\nNot found link found in password section");
                System.out.println("\nforgot password links ends here");
                }
                System.out.println("\nforgot password links ends here");
            }
            else
            {
                System.out.println("\n forgot password text is not found in this html");
                //v31="0.5";
            }

            //********************** not found links  checking section ****************************
           int countNotFoundLinks=0;
           int response=0;
            for (Element lfreq : linkfrequency) {
                response=notFoundLinks(lfreq.attr("abs:href"));
                if(response!=200)
                countNotFoundLinks++;
            }
            if(!linkfrequency.isEmpty())
            {
                float nflinks=(float)countNotFoundLinks/linkfrequency.size();
            System.out.println("\nRatio of not found links to total links in entire website =" + nflinks );
          //  v32=Float.toString(nflinks);
            }
            else
            {
                System.out.println("\n No links found to check Ratio of not found links to total links in entire website ");
              //  v32="10";
            }            //****************************** ends here***********************************
            
            
            
        //    printAllVariables();
         //   feedTOExcel(rowcount);
           // rowcount++;
           
        } catch (IOException ex) {
            System.out.println("2. " + ex);
            return 2;
        } catch (NullPointerException e) {
           // result.append("NullPointerException caught");
            return 2;
        }
       
            catch (Exception ex) {
                System.out.println("3. " + ex);
               //  result.append("3."+ex);
            return 2;
            }
        return output;
    }
          private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
             public int notFoundLinks(String uri) {
        /**
         * ********************** Identifying NOT FOund links ***************
         */
        URL url;
        try {
            url = new URL(uri);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            System.out.println("code is: " + code);
        return code;   
        } catch (MalformedURLException ex) {
            System.out.println(ex);
            return 0;
        } catch (IOException ex) {
            System.out.println(ex);
            return 0;
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Caught an IllegalArgumentException..." + e.getMessage());
            return 0;
        }
        catch (Exception ex) {
            System.out.println(ex);
            return 0;
        }
        // return ;
        /**
         * ***********************ends here **********************************
         */
    }
              public String getWebsiteIdentity(String address, int countfreq) throws FileNotFoundException, MalformedURLException, IOException {
      StringBuilder builder = new StringBuilder();
       try{ 
        URL url = new URL(address);
        String host = url.getHost();
        boolean score1 = false;
        String TLDs = "C:\\Users\\srinivas\\Documents\\NetBeansProjects\\TLDs.txt";
        ArrayList<String> pdomain = new ArrayList<>();
        String[] words = host.split("\\.");              //splitting the url at '.' and storing it in a word array
        for (String word : words) {
            pdomain.add(word);
        }
        int i = pdomain.size();
        if(i<2)          // this is written when syntactic url is given as input to this function it just returns null string 
            return "";
         BufferedReader br = new BufferedReader(new FileReader(TLDs));
            String list = br.readLine();
            while (list != null) {
                if(!score1)
                score1 = StringUtils.equalsIgnoreCase(pdomain.get(i - 2), list);
                if (score1) {
                    for (String s : pdomain) //
                    {
                        if (countfreq >= i - 3) {
                            builder.append(s);
                            if (countfreq != i - 1) {
                                builder.insert(builder.length(), ".");
                            }
                            countfreq++;
                        } else {
                            countfreq++;
                        }
                    }
                   break;
                }
                list = br.readLine();
            }
            if (!score1) {
                for (String s : pdomain) {
                    if (countfreq >= i - 2) {
                        builder.append(s);
                        if (countfreq != i - 1) {
                            builder.insert(builder.length(), ".");
                            countfreq++;
                        }
                    } else {
                        countfreq++;
                    }
                }
            }
        }
       /* catch (IOException ex) {
       System.out.println("2. " + ex);      // this is for
       return "";
       }*/
        catch (Exception ex) {
            return "";
        }
        
        return builder.toString();
    }
}
