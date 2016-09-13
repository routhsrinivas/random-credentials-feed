/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package headlessbrowsertesting;

import com.google.common.net.InternetDomainName;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author srinivas
 */
public class URLHeuristicScore {

    public float nullLinkScore(Document doc) {
     
       int nullLinksStatus = 0;
       
        //************** for Null Links in anchors of first page or second page*****
       //<editor-fold defaultstate="collapsed" desc="comment">
       /*List<WebElement> anchors = driver.findElements(By.tagName("a"));
       for(int i=0; i<anchors.size();i++)
       {
       if(anchors.get(i).isDisplayed())
       {
       /*System.out.println(anchors.get(i).getAttribute("href"));
       System.out.println(anchors.get(i).getAttribute("pathname"));*/
       /*   if(anchors.get(i).getAttribute("href").startsWith("#"))
       nullLinksCount++;
       }
       }
       return nullLinksCount;*/
//</editor-fold>
         
          Elements linkfrequency = doc.select("a[href]");
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
                return nbval;
            } 
            else {
                
                System.out.println("\n No Null links ratio(website)\n");
                return 0;
            }
           
    }
    
  public int zeroLinkScore(Document doc) {  
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
                
                System.out.println("\n Entered url is phishing site based on image phishing based detection\n");
               return 1;
                
            } else if (status3 && nullinbody) {
               
                System.out.println("\n Entered url is phishing site based on null links body based detection\n");
                return 1;
            }
            else
            {
                System.out.println("\n Entered url has links in body section\n");
                return 0;
            }
     
  }
  public float URLPatternMatching(Document doc){
      Elements linkfrequency = doc.select("a[href]");
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
            return umwebsite; 
        }
        else{
             
            System.out.println("\n No URL Matching Ratio(website) "); 
        return 0;   
        }
  }
  public String websiteIdentityAnchors(Document doc, String url,URLHeuristicScore mainObj) throws URISyntaxException{
      Elements linkfrequency = doc.select("a[href]");
      if(linkfrequency.size()==0){
          return "no links to calculate website identity";
      }
        String webident = "";
      Map<String, Integer> map = new HashMap<>();
            for (Element lfreq : linkfrequency)
            {
                Integer n = map.get(getPrimaryDomain(lfreq.attr("abs:href"),mainObj));
                if (n == null) 
                {
                    n = 1;
                } 
                else 
                {
                    n++;
                }
                String webfreq = getPrimaryDomain(lfreq.attr("abs:href"),mainObj);
                map.put(webfreq, n);
            }
            int largest = 0;
            for (Map.Entry m : map.entrySet()) 
            {

                String compare = m.getValue().toString();
                if (largest < (Integer.parseInt(compare))) 
                {
                    largest = Integer.parseInt(compare);
                }
            }
            for (Map.Entry r : map.entrySet()) 
            {
                if (largest == (Integer.parseInt(r.getValue().toString()))) 
                {
                    webident = (r.getKey().toString());
                }
            }
            if (getPrimaryDomain(url,mainObj).equals(webident)) 
            {
               System.out.println("\n Current domain and target domain are same.\n");
               return "Current domain and target domain are same"; 
            } else {
                if (!webident.equalsIgnoreCase("")) {
                   
                     System.out.println("\nBased on anchor links website identity is Entered URL is targeting  " + webident + "\n");
                     return webident;
                }
                else {
                    
               System.out.println("\nThere are no links to compare with suspicious link ( Based on Anchor links website identity)");
             return "no links to calculate website identity";
                }
           //     output = 1;
            }
  }
   public String getPrimaryDomain(String URL, URLHeuristicScore obj ) throws URISyntaxException{
    try{
       String host = new URI(URL).getHost();
       InternetDomainName domainName = InternetDomainName.from(host);
    return domainName.topPrivateDomain().toString();
    }
    catch(Exception e){
        System.out.println(e);
        return "javascript or differentURL";
    }
     }
    
    public int URLScoreInterface(WebDriver driver,String URL) throws URISyntaxException {
        URLHeuristicScore mainObj=new URLHeuristicScore();
         float nullScore=-1;
         int zeroLinkScore=-1;
         String anchorlink="";
         float URLPatternMatchingScore=-1;
         String websiteIdentityAnchors="";
         String pageSource=driver.getPageSource();
         Document doc=Jsoup.parse(pageSource);
        // String pageSource=driver.getPageSource();
        // System.out.println("Source code is: \n"+pageSource);
        List<WebElement> links = driver.findElements(By.cssSelector("a[href]"));
         for(int i=0;i<links.size();i++)
             anchorlink=links.get(i).getAttribute("href");
         nullScore=mainObj.nullLinkScore(doc);
         System.out.println("NullLinkScore is : "+nullScore);
         zeroLinkScore=mainObj.zeroLinkScore(doc);
         System.out.println("ZeroBodyLinkScore is : "+zeroLinkScore);
         URLPatternMatchingScore=mainObj.URLPatternMatching(doc);
         websiteIdentityAnchors=mainObj.websiteIdentityAnchors(doc,URL,mainObj);
         if(nullScore==1||zeroLinkScore==1||URLPatternMatchingScore==1){
             System.out.println("Targeted website is "+websiteIdentityAnchors);
             return 1;
         }
         else
             return 0;
    }
    
    public boolean domComparisonFirstSecond(String firstPageSource,String secondPageSource){
         Document docFirst=Jsoup.parse(firstPageSource);
         Document docSecond=Jsoup.parse(secondPageSource);
        Elements elementsFirstPage = docFirst.getAllElements();
        Elements elementsSecondPage = docSecond.getAllElements();
        if(elementsFirstPage.equals(elementsSecondPage))
        return true;
        else return false;
    }
      
}
