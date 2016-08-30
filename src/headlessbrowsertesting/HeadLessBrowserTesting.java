/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package headlessbrowsertesting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
/**
 *
 * @author srinivas
 */
public class HeadLessBrowserTesting {

    /**
     * @param args the command line arguments
     */
     String whiteurl = "C:\\Users\\srinivas\\Documents\\NetBeansProjects\\URL.txt";
    String whitelist = "C:\\Users\\srinivas\\Documents\\NetBeansProjects\\Whitelist.txt";
     String v0="url", v2="Error", v3="Error",v4="Error",v6="Error",v7="Error",v9="Error",v12="False",v13="Error",v30="Error";
    int v1=100,v5=2000,v8=20000,v11=-100,v19=100,v10=100;
    String v14="Error",v15="Error",v20="Error",v21="Error",v22="Error",v23="Error",v24="Error",v25="Error",v26="Error",v27="Error",v28="Error",v29="Error",v31="Error",v32="Error",v16="Error",v17="Error",v18="Error";
   int rowcount=0;
    public static void main(String[] args) {
        File src=new File("C:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
        System.setProperty("phantomjs.binary.path", src.getAbsolutePath());
        WebDriver driver=new PhantomJSDriver();
        driver.get("http://www.fb.com");
      //System.out.println(driver.getPageSource());
      /* TakesScreenshot ts = (TakesScreenshot)driver;
        File imgsrc=ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(imgsrc, new File("C:\\Users\\srinivas\\Documents\\NetBeansProjects\\HeadLessBrowserTesting\\Screenshots\\1.png"));
        } catch (IOException ex) {
            Logger.getLogger(HeadLessBrowserTesting.class.getName()).log(Level.SEVERE, null, ex);
        }
       */
        String parseHtml=driver.getPageSource().toString();
      //  String parseHtml="<html><head><title>this is srinivas</title><meta keyword='simple application'><body><div class='sri'> <p> this is paragraph</p></div></body></html>";
        Document doc=Jsoup.parse(parseHtml);
       String titleContent = doc.title().toString();
       System.out.println(titleContent);
       /*   doc.traverse(new NodeVisitor() {
       public void head(Node node, int depth) {
       System.out.println("Entering tag: " + node.nodeName());
       }
       public void tail(Node node, int depth) {
       System.out.println("Exiting tag: " + node.nodeName());
       }
       });*/
    }
       /** public int checkForNullLinks(String url) throws Exception {
       v19=100;
     v14="Error";
     v15="Error";
     v20="Error";
     v21="Error";
     v22="Error";
     v23="Error";
     v24="Error";
     v25="Error";
     v26="Error";
     v27="Error";
     v28="Error";
     v29="Error";
     v31="Error";
     v32="Error";
     v16="Error";
     v17="Error";
     v18="Error";
    v13="Error";
    v30="Error";
        int output = 0;
        try {
            //File input1 = new File("C:/Users/fuck/Contacts/Desktop/copyrightwithfooternonnull - Copy.html");
            //Document doc = Jsoup.parse(input1, "UTF-8", "http://example.com/");
            Document doc = Jsoup.connect(url)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .timeout(12000) // optional
                    .header("Accept-Language", "pt-BR,pt;q=0.8") // missing
                    .header("Accept-Encoding", "gzip,deflate,sdch") // missing
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36") // missing
                    .referrer("http://www.google.com") // optional
                    .execute()
                    .parse();
            //Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0").referrer("http://www.google.com").timeout(1000 * 5).get();
            //   int statusCode = doc.statusCode();
            String webident = "";
             URL web = new URL(url);
            String webhost = web.getHost();
            //************** login form detection *******************************/
   /**         Elements inputElements = doc.getElementsByTag("input");

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
            v13=Boolean.toString(status);
            //************************ login form detection ended ***************/
/**
            Elements anchorstag1 = doc.select("div[id~=bottom|footer] a");
            Elements anchorsclass1 = doc.select("div[class~=footer|bottom] a");
            Elements footertag = doc.select("footer a");
            Elements bottomtag = doc.select("bottom a");
            Elements copyright = doc.select(":containsOwn(©)");
            Elements linkfrequency = doc.select("a[href]");
            String titleContent = "";
            boolean status2 = false, title = false;

// **************** copyright compared with whitelist here*********************
            String nenu = "";
            try {

                if (!copyright.isEmpty()) {
                    nenu = copyright.last().ownText();
                    System.out.println("\nlocal copyright with ©: " + nenu);
                } else {
                        Elements copyrightReserved = doc.select(":containsOwn(RIGHTS RESERVED)");
                        if (!copyrightReserved.isEmpty()) {
                            nenu = copyrightReserved.last().ownText();
                            System.out.println("\ncopyright with Reserved text: " + nenu);
                        }
                    
                }
                if (!nenu.isEmpty()) {
                    ArrayList<String> copyrightsearch = new ArrayList<>();
                    String[] words = nenu.split(" |\\.");
                    for (String word : words) {
                        copyrightsearch.add(word);
                    }
                    for (int i = 0; i < copyrightsearch.size(); i++) {
                        if (copyrightsearch.get(i).length() > 2) {
                            try (BufferedReader br1 = new BufferedReader(new FileReader(whitelist))) {
                                String list1 = br1.readLine();
                                while (list1 != null) {
                                    if (list1.toLowerCase().contains(copyrightsearch.get(i).toLowerCase())) {
                                        status2 = true;
                                    }
                                    list1 = br1.readLine();
                                }
                            } catch (Exception ex) {
                                System.out.println("3: " + ex);
                            }
                        }

                    }
                    v14="0.5";
                }
                else
                {
                    System.out.println("\n no copyright to be extracted from website\n");
                    v14="0";
                    v17="0.5";
                }
            } catch (NullPointerException e) {
                System.out.print("NullPointerException caught");
            }
            if (status2) {
              //  result.append("\nEntered URL is phishing site based on Copyright\n");
                System.out.println("\nEntered URL is phishing site based on Copyright\n");
                output = 1;
                 v14="1";
            }

            // **************************title extraction starts here ********************
            titleContent = doc.title().toString();
            if(!titleContent.isEmpty())
            {
            System.out.println("Title Content is displayed below " + "\n" + titleContent);
            v15="0.5";
            }
            else
            {
               System.out.println("\n No Title to be extracted from website");
               v15="0";
               v16="0.5";
            }
            if (!titleContent.isEmpty()) {
                ArrayList<String> titleText = new ArrayList<>();
                String[] words = titleContent.split(" |\\.");
                for (String word : words) {
                    titleText.add(word);
                }
                for (int i = 0; i < titleText.size(); i++) {
                    if (titleText.get(i).length() > 2) {
                        try (BufferedReader br1 = new BufferedReader(new FileReader(whitelist))) {
                            String list1 = br1.readLine();
                            while (list1 != null) {
                                if (list1.toLowerCase().contains(titleText.get(i).toLowerCase())) {
                                    title = true;
                                }
                                list1 = br1.readLine();
                            }
                        } catch (Exception ex) {
                            System.out.println("3: " + ex);
                        }
                    }

                }
            }
            if (title) {
            //    result.append("Entered URL is phishing site based on Title\n");
                System.out.println("Entered URL is phishing site based on Title\n");
                output = 1;
                v15="1";
            }
            else
                 System.out.println("Entered URL cannot be detected based on Title compared with whitelist\n");
            //***********************************************title extraction ends here ***********

            //******************************************* Description extraction starts here ***************
            String attr = "description";
            String descript = "";
            // String content="og:image";
            Elements elements = doc.select("meta[name=" + attr + "]");
            for (Element element : elements) {
                descript = element.attr("content");
                if (descript != null) {
                    System.out.println("Description Content is displayed below " + "\n" + descript);
                }

            }
            if(descript=="")
            {
                System.out.println("\n No description to be extracted from website\n");
             v18="0.5";   
            }

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
                v19=1;
            } else if (status3 && nullinbody) {
              //  result.append("entered url is phishing site based on null links body based detection\n");
                System.out.println("\n Entered url is phishing site based on null links body based detection\n");
                output = 1;
                v19=1;
            }
            else
            {
                System.out.println("\n Entered url has links in body section\n");
                v19=0;
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
                v20= Float.toString(nbval); 
            } 
            else {
                v20="10";
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
                v21=Float.toString(nfval);
            } else {
                v21="10";
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
            v22=Float.toString(umfooter) ;
            }
        }
         else
            {
                System.out.println("\n No URL Matching Ratio(footer)"); 
                v22="10";
            }
        /******************* ends  *********************************/
      //*************************** URL Path Matching for entire website ***************************
     /**   Map<String, Integer> simLink4 = new HashMap<>();
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
            v23=Float.toString(umwebsite);
        }
        else{
             
            System.out.println("\n No URL Matching Ratio(website) "); 
         v23="10";   
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
    /**       Map<String, Integer> map = new HashMap<>();
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
               // result.append("current domain and target domain are same.\n");
               System.out.println("\n Current domain and target domain are same.\n");
               v24="0.5";
            } else {
                if (!webident.equalsIgnoreCase("")) {
                 //   result.append("Entered URL is targeting  " + webident + "\n");
                     System.out.println("\nBased on anchor links website identity is Entered URL is targeting  " + webident + "\n");
                     v24="1";
                }
                else {
                 //   result.append("\nThere are no links to compare with suspicious link");
               System.out.println("\nThere are no links to compare with suspicious link ( Based on Anchor links website identity)");
               v24="0";
                }
                output = 1;
            }
            
          

            // ******************************* Existence of similar urls ***********************
       
        Elements media = doc.select("img[src]");
        Elements scripts = doc.select("script[src]");
        Elements imports = doc.select("link[href]");
Elements actionAttribute=doc.select("form[action]");
//*************media img tags ******************************************
        print("\nMedia: (%d)", media.size());
        for (Element link : media) {
            print(" * %s <%s>", link.tagName(), link.attr("abs:src"));
        }
        //**************************scripts ********************************
        print("\nScripts: (%d)", scripts.size());
        for (Element link : scripts) {
            print(" * %s <%s>", link.tagName(), link.attr("abs:src"));
        }
        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> ", link.tagName(), link.attr("abs:href"));
        }
        //***************links **************************
        print("\nLinks: (%d)", linkfrequency.size());
        for (Element link : linkfrequency) {
            print(" * a: %s", link.attr("abs:href"));
        }
           //******************************************** website identity  from media***********************  
        webident="";
            Map<String, Integer> mapMedia = new HashMap<>();
            for (Element lfreq : media) {
                Integer n = mapMedia.get(getWebsiteIdentity(lfreq.attr("abs:src"), 0));
                if (n == null) {
                    n = 1;
                } else {
                    n++;
                }

                String webfreq = getWebsiteIdentity(lfreq.attr("abs:src"), 0);
                mapMedia.put(webfreq, n);
            }
             int largestScript = 0;
            for (Map.Entry m : mapMedia.entrySet()) {

                String compare = m.getValue().toString();
                if (largestScript < (Integer.parseInt(compare))) {
                    largestScript = Integer.parseInt(compare);
                }
            }
            for (Map.Entry r : mapMedia.entrySet()) {
                if (largestScript == (Integer.parseInt(r.getValue().toString()))) {
                    webident = (r.getKey().toString());
                }
            }
            if (getWebsiteIdentity(url, 0).equals(webident)) {
               // result.append("current domain and target domain are same.\n");
               System.out.println("\n Media (img): current domain and target domain are same.\n");
               v25="0.5";
            } else {
                if (!webident.equalsIgnoreCase("")) {
               //     result.append("Entered URL is targeting  " + webident + "\n");
                     System.out.println("\n Media(img): Entered URL is targeting  " + webident + "\n");
                     v25="1";
                }
                else {
               //     result.append("\nThere are no media links to compare with suspicious link\n");
               System.out.println("\n No Media Links \n");
               v25="0";
                }
                
            }
            
            //***********************website identity  from scripts ***********************
              webident="";
               Map<String, Integer> mapScript = new HashMap<>();
  for (Element lfreq : scripts) {
                Integer n = mapScript.get(getWebsiteIdentity(lfreq.attr("abs:src"), 0));
                if (n == null) {
                    n = 1;
                } else {
                    n++;
                }

                String webfreq = getWebsiteIdentity(lfreq.attr("abs:src"), 0);
                mapScript.put(webfreq, n);
            }

  largestScript = 0;
            for (Map.Entry m : mapScript.entrySet()) {

                String compare = m.getValue().toString();
                if (largestScript < (Integer.parseInt(compare))) {
                    largestScript = Integer.parseInt(compare);
                }
            }
            for (Map.Entry r : mapScript.entrySet()) {
                if (largestScript == (Integer.parseInt(r.getValue().toString()))) {
                    webident = (r.getKey().toString());
                }
            }
            if (getWebsiteIdentity(url, 0).equals(webident)) {
              //  result.append("current domain and target domain are same.\n");
                System.out.println("\n Scripts: current domain and target domain are same.\n");
                v26="0.5";
            } else {
                if (!webident.equalsIgnoreCase("")) {
              //      result.append("Entered URL is targeting  " + webident + "\n");
                     System.out.println("Based on website identity through scripts: Entered URL is targeting  " + webident + "\n");
                     v26="1";
                }
                else {
               //     result.append("There are no script links to compare with suspicious link\n");
               System.out.println("No script links to compare with suspicious link ( Based on Scripts links website identity)\n");
               v26="0";
                }
                
            }
            
  //*********************************website identity  from Imports ****************
              webident="";
               Map<String, Integer> mapImports = new HashMap<>();
    for (Element lfreq : imports) {
                Integer n = mapImports.get(getWebsiteIdentity(lfreq.attr("abs:href"), 0));
                if (n == null) {
                    n = 1;
                } else {
                    n++;
                }

                String webfreq = getWebsiteIdentity(lfreq.attr("abs:href"), 0);
                mapImports.put(webfreq, n);
            }
            largestScript = 0;
            for (Map.Entry m : mapImports.entrySet()) {

                String compare = m.getValue().toString();
                if (largestScript < (Integer.parseInt(compare))) {
                    largestScript = Integer.parseInt(compare);
                }
            }
            for (Map.Entry r : mapImports.entrySet()) {
                if (largestScript == (Integer.parseInt(r.getValue().toString()))) {
                    webident = (r.getKey().toString());
                }
            }
            if (getWebsiteIdentity(url, 0).equals(webident)) {
                //result.append("current domain and target domain are same.\n");
               System.out.println("\n Imports (img): current domain and target domain are same.\n");
               v27="0.5";
            } else {
                if (!webident.equalsIgnoreCase("")) {
                 //   result.append("Entered URL is targeting  " + webident + "\n");
                     System.out.println(" Imports(css and icons .png) Entered URL is targeting  " + webident + "\n");
                     v27="1";
                }
                else {
                  //  result.append("There are no links to compare with suspicious link");
               System.out.println(" No import links to compare with suspicious link");
               v27="0";
                }
               
            }
            if((mapMedia.size()+mapScript.size()+mapImports.size())<0)
            {
                System.out.println("The Website is phishing based on the combined count of the links of imports, scripts and media\n but here might be a image in inline css or any other");
                output=1;
            }
            //**************************** website identity  from scripts img and css (total) **************
                webident="";
            Map<String, Integer> mapTotal = new HashMap<>();
            for (Element lfreq : media) {
                Integer n = mapTotal.get(getWebsiteIdentity(lfreq.attr("abs:src"), 0));
                if (n == null) {
                    n = 1;
                } else {
                    n++;
                }

                String webfreq = getWebsiteIdentity(lfreq.attr("abs:src"), 0);
                mapTotal.put(webfreq, n);
            }
             for (Element lfreq : imports) {
                Integer n = mapTotal.get(getWebsiteIdentity(lfreq.attr("abs:href"), 0));
                if (n == null) {
                    n = 1;
                } else {
                    n++;
                }

                String webfreq = getWebsiteIdentity(lfreq.attr("abs:href"), 0);
                mapTotal.put(webfreq, n);
            }
             for (Element lfreq : scripts) {
                Integer n = mapTotal.get(getWebsiteIdentity(lfreq.attr("abs:src"), 0));
                if (n == null) {
                    n = 1;
                } else {
                    n++;
                }

                String webfreq = getWebsiteIdentity(lfreq.attr("abs:src"), 0);
                mapTotal.put(webfreq, n);
            }
             int largestTotal = 0;
            for (Map.Entry m : mapTotal.entrySet()) {

                String compare = m.getValue().toString();
                if (largestTotal < (Integer.parseInt(compare))) {
                    largestTotal = Integer.parseInt(compare);
                }
            }
            for (Map.Entry r : mapTotal.entrySet()) {
                if (largestTotal == (Integer.parseInt(r.getValue().toString()))) {
                    webident = (r.getKey().toString());
                }
            }
            if (getWebsiteIdentity(url, 0).equals(webident)) {
              //  result.append("current domain and target domain are same.\n");
               System.out.println("\n  (3) Media (img), scripts and imports: current domain and target domain are same.\n");
               v28="0.5";
            } else {
                if (!webident.equalsIgnoreCase("")) {
                  //  result.append("Entered URL is targeting  " + webident + "\n");
                     System.out.println("(3) media (img),scripts and imports: Entered URL is targeting  " + webident + "\n");
                     v28="1";
                }
                else {
                  //  result.append("There are no  links to compare with suspicious link");
               System.out.println(" (3) no  links to compare with suspicious link( Based on img,scripts and CSS( 3 ) links website identity)");
               v28="0";
                }
                output=1;
            }
      //**************************** website identity  from scripts, img, css, anchors (total) **************
                webident="";
            Map<String, Integer> AllMapTotal = new HashMap<>();
            for (Element lfreq : media) {
                Integer n = AllMapTotal.get(getWebsiteIdentity(lfreq.attr("abs:src"), 0));
                if (n == null) {
                    n = 1;
                } else {
                    n++;
                }

                String webfreq = getWebsiteIdentity(lfreq.attr("abs:src"), 0);
                AllMapTotal.put(webfreq, n);
            }
             for (Element lfreq : imports) {
                Integer n = AllMapTotal.get(getWebsiteIdentity(lfreq.attr("abs:href"), 0));
                if (n == null) {
                    n = 1;
                } else {
                    n++;
                }

                String webfreq = getWebsiteIdentity(lfreq.attr("abs:href"), 0);
                AllMapTotal.put(webfreq, n);
            }
             for (Element lfreq : scripts) {
                Integer n = AllMapTotal.get(getWebsiteIdentity(lfreq.attr("abs:src"), 0));
                if (n == null) {
                    n = 1;
                } else {
                    n++;
                }

                String webfreq = getWebsiteIdentity(lfreq.attr("abs:src"), 0);
                AllMapTotal.put(webfreq, n);
            }
               for (Element lfreq : linkfrequency) {
                Integer n = AllMapTotal.get(getWebsiteIdentity(lfreq.attr("abs:href"), 0));
                if (n == null) {
                    n = 1;
                } else {
                    n++;
                }

                String webfreq = getWebsiteIdentity(lfreq.attr("abs:href"), 0);
                AllMapTotal.put(webfreq, n);
            }
             int Total = 0;
            for (Map.Entry m : AllMapTotal.entrySet()) {

                String compare = m.getValue().toString();
                if (Total < (Integer.parseInt(compare))) {
                    Total = Integer.parseInt(compare);
                }
            }
            for (Map.Entry r : AllMapTotal.entrySet()) {
                if (Total == (Integer.parseInt(r.getValue().toString()))) {
                    webident = (r.getKey().toString());
                }
            }
            if (getWebsiteIdentity(url, 0).equals(webident)) {
             //   result.append("current domain and target domain are same.\n");
               System.out.println("\n (4) Media (img), scripts imports and anchors: current domain and target domain are same.\n");
               v29="0.5";
            } else {
                if (!webident.equalsIgnoreCase("")) {
             //       result.append("Entered URL is targeting  " + webident + "\n");
                     System.out.println("(4) Media (img),scripts  imports and anchors: Entered URL is targeting  " + webident + "\n");
                     v29="1";
                }
                else {
            //        result.append("There are no  links to compare with suspicious link");
               System.out.println("(4) No  links to compare with suspicious link ( Based on img,css,scripts and Anchor links ( 4 )website identity)");
               v29="0";
                }
                output=1;
            }
            
            
            //*********************************** ends here *******************************/
  /**      print("\n Action Fields:(%d)",actionAttribute.size());
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
                v30="true";
            }
            else{ 
                System.out.println("\n Action link is same as suspicious url domain");
             v30="false";   
            }
        }
        else v30="false*";
           //********************************** existing of similar urls in entire page ends here***********************************/
        
              //***************************** forgot password section starts here **************************//
     /**       Elements forgotPwdText = doc.select(":containsOwn(Forgot)");
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
                        v31="1";
                        System.out.println("forgot password links have null link");
                    }
                 forgotLink=new URL(lfreq.attr("abs:href"));
                 forgotPWDLink=forgotLink.getHost();
                  if(!StringUtils.equalsIgnoreCase(webhost,forgotPWDLink))
                  {
                     System.out.println("\n forgot link is pointing to foreign domain");
                     output=1;
                     v31="1";
                  }
                  else
                      v31="0";
                    System.out.println("forgot password links http status starts ");
                    System.out.println(lfreq.attr("abs:href"));
                   forgotPWDResponse= notFoundLinks(lfreq.attr("abs:href"));
                }
                    else v31="0.5";
                }
                if(forgotPWDResponse!=200&&forgotPWDResponse!=0)
                {
                    v31="1";
                    System.out.println("\nNot found link found in password section");
                System.out.println("\nforgot password links ends here");
                }
                System.out.println("\nforgot password links ends here");
            }
            else
            {
                System.out.println("\n forgot password text is not found in this html");
                v31="0.5";
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
            v32=Float.toString(nflinks);
            }
            else
            {
                System.out.println("\n No links found to check Ratio of not found links to total links in entire website ");
                v32="10";
            }            //****************************** ends here***********************************
            
            
            //****************************** Bing Search results status with title copyright description *************/
   /*         BingSearchEngineAPI bingSearchEngineAPI = new BingSearchEngineAPI();
            
            if (!titleContent.isEmpty()) {
                if (bingSearchEngineAPI.bingResultsStatus(webhost, titleContent)) {
                    System.out.println("suspicious link is found in bing results based on title search");
                } else {
                    System.out.println("suspicious link is not found in bing results based on title search");
                }
            } else {
                System.out.println("There is no title text");
            }
            if (!nenu.isEmpty()) {
                if (bingSearchEngineAPI.bingResultsStatus(webhost, nenu)) {
                    System.out.println("suspicious link is found in bing results based on copyright search");
                } else {
                    System.out.println("suspicious link is not found in bing results based on copyright search");
                }
            } else {
                System.out.println("There is no copyright text");
            }
            if (!descript.isEmpty()) {
                if (bingSearchEngineAPI.bingResultsStatus(webhost, descript)) {
                    System.out.println("suspicious link is found in bing results based on description search");
                } else {
                    System.out.println("suspicious link is not found in bing results based on description search");
                }
            } else {
                System.out.println("There is no description text");
            }
  */
    //***************************** Bing search results checking is over ******************************
            
/**            printAllVariables();
            feedTOExcel(rowcount);
            rowcount++;
           
        } catch (IOException ex) {
            System.out.println("2. " + ex);
            return 2;
        } catch (NullPointerException e) {
          //  result.append("NullPointerException caught");
            return 2;
        }
       
            catch (Exception ex) {
                System.out.println("3. " + ex);
           //      result.append("3."+ex);
            return 2;
            }
        return output;
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
     /**   catch (Exception ex) {
            return "";
        }
        
        return builder.toString();
    }
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
    public int notFoundLinks(String uri) {
        /**
         * ********************** Identifying NOT FOund links ***************
         */
    /**    URL url;
     /**   try {
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
 /**   }
  public void printAllVariables(){
        System.out.println("\nv0:"+v0+"\nv1:"+ v1+"\nv2:" +v2 +"\nv3:"+v3+"\nv4:"+v4+"\nv5:"+v5+"\nv6:"+v6+"\nv7:"+v7+"\nv8:"+v8+"\nv9:"+v9+"\nv10:"+v10+"\nv11:"+v11+"\nv12:"+v12+"\nv13:"+v13+"\nv14:"+v14+"\nv15:"+ v15+"\nv16:"+v16+"\nv17:"+v17+"\nv18:"+v18
                +"\nv19:"+v19+"\nv20:"+v20+"\nv21:"+v21+"\nv22:"+v22+"\nv23:"+v23+"\nv24:"+v24+"\nv25:"+v25+"\nv26:"+v26+"\nv27:"+v27+"\nv28:"+v28+"\nv29:"+ v29+"\nv30:" +v30 +"\nv31:"+v31+"\nv32:"+v32);
  }
 public void feedTOExcel(int rcount) throws FileNotFoundException, IOException, InvalidFormatException{
            InputStream inp = new FileInputStream("latestleg.xlsx");
          Workbook wb = WorkbookFactory.create(inp);
   Sheet sheet = wb.getSheetAt(0);
    int rowCount=rcount;
     int columnCount = -1;
    Object[][] bookData = {
         {v0,v1, v2, v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25,v26,v27,v28,v29,v30,v31,v32},
         /* {"Effective Java", "Joshua Bloch", 36},
         {"Clean Code", "Robert martin", 42},
         {"Thinking in Java", "Bruce Eckel", 35},*/
 /**    };
     for (Object[] aBook : bookData)
     {
       Row row = sheet.createRow(rowCount);    
         for (Object field : aBook) {
             Cell cell = row.createCell(++columnCount);
             if (field instanceof String) {
                 cell.setCellValue((String) field);
             } else if (field instanceof Integer) {
                 cell.setCellValue((Integer) field);
             }
         }
         
     }
    FileOutputStream fileOut = new FileOutputStream("latestleg.xlsx");
    wb.write(fileOut);
    fileOut.close();
 }
 */
    
}
