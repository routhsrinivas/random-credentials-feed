/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package headlessbrowsertesting;

import com.google.common.net.InternetDomainName;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author srinivas
 */
public class SecondPage {
     public String secondPageLoginCheck(WebDriver driver, String pwdAttribute, String firstPageURL,String firstPageSource) {
         SecondPage obj=new SecondPage();
        String id = "null";
        String loginText = "";
        String secondPageURL="";
        WebElement textElement = null;
        WebElement pwd = null;
        boolean status=true;
        int URLHeuristicScore=-1;
        secondPageURL=driver.getCurrentUrl();
         boolean primaryDomainCheck=false;
        // String secondPageSource=driver.getPageSource();
         URLHeuristicScore mainObj=new URLHeuristicScore();
         try {
              primaryDomainCheck=comparePrimarydomain(firstPageURL,secondPageURL,obj);
         } catch (URISyntaxException ex) {
             Logger.getLogger(SecondPage.class.getName()).log(Level.SEVERE, null, ex);
         }
         if(primaryDomainCheck)
         {
             //<editor-fold defaultstate="collapsed" desc="comment">
             //********** no identifiers in input fields and to check login status in second page
             /* if(pwdAttribute.equalsIgnoreCase("null"))
             {
             if(mainObj.domComparisonFirstSecond(firstPageSource,secondPageSource))
             {
             try {
             URLHeuristicScore=mainObj.URLScoreInterface(driver,secondPageURL);
             } catch (URISyntaxException ex) {
             Logger.getLogger(SecondPage.class.getName()).log(Level.SEVERE, null, ex);
             }
             driver.quit();
             if(URLHeuristicScore!=1)
             status=false;
             else status=true;
             }
             else return "the webpage is Phishing based on successful logging in second page based on no identifier pwd";
             }
             else*/
//</editor-fold>
       try {
            pwd = driver.findElement(By.cssSelector("input[type='password']"));
            if(pwd.isDisplayed())
            {
                status=false;
                URLHeuristicScore=mainObj.URLScoreInterface(driver,secondPageURL);
                 driver.quit();
            if(URLHeuristicScore!=1)
            status=false;
            else status=true;
            }
            //<editor-fold defaultstate="collapsed" desc="comment">
            /*         List<WebElement> inputFields = driver.findElements(By.tagName("input"));
            System.out.println("Total inputfields are " + inputFields.size());
            for (int i = 0; i < inputFields.size(); i++) {
            try {
            if (!(inputFields.get(i).getAttribute("name").isEmpty())) {
            id = inputFields.get(i).getAttribute("name");
            textElement = driver.findElement(By.name(id));
            } else {
            if (!inputFields.get(i).getAttribute("id").isEmpty()) {
            id = inputFields.get(i).getAttribute("id");
            textElement = driver.findElement(By.id(id));
            } else {
            if (!inputFields.get(i).getAttribute("class").isEmpty()) {
            id = inputFields.get(i).getAttribute("class");
            textElement = driver.findElement(By.className(id));
            }
            }
            }
            if (textElement.isDisplayed()) {
            if (pwd.equals(textElement)) {
            if(id.equalsIgnoreCase(pwdAttribute))
            {
            //  System.out.println("The webpage is genuine and is safe to visit");
            
            URLHeuristicScore=mainObj.URLScoreInterface(driver,secondPageURL);
            driver.quit();
            if(URLHeuristicScore!=1)
            status=false;
            break;
            }
            }
            status=true;
            }
            } catch (Exception e) {
            System.out.println(e);
            }
            }*/
//</editor-fold>
        } catch (NoSuchElementException e) {
           // System.out.println("no password field in second page");
            System.out.println("the webpage is phish as pwd field is not in second page ");
            driver.quit();
            return "the webpage is phish as pwd field is not in second page ";
        }    catch (URISyntaxException ex) {
                 Logger.getLogger(SecondPage.class.getName()).log(Level.SEVERE, null, ex);
             }
     }
         else
         {
             System.out.println("phishing because of first page and second page primary domain conflict");
             driver.quit();
             return "phishing because of first page and second page primary domain conflict";
         }
        //<editor-fold defaultstate="collapsed" desc="comment">
        /*try {
            pwd = driver.findElement(By.cssSelector("input[type='password']"));
        } catch (NoSuchElementException e) {
            List<WebElement> anchors = driver.findElements(By.tagName("a"));
            for (int j = 0; j < anchors.size(); j++) {
                if (anchors.get(j).isDisplayed()) {
                    if (anchors.get(j).getText().toLowerCase().equalsIgnoreCase("login")) {
                        loginText = anchors.get(j).getText();
                        break;
                    }
                    if (anchors.get(j).getText().toLowerCase().equalsIgnoreCase("signin")) {
                        loginText = anchors.get(j).getText();
                        break;
                    }
                    if (anchors.get(j).getText().toLowerCase().equalsIgnoreCase("log in")) {
                        loginText = anchors.get(j).getText();
                        break;
                    }
                    if (anchors.get(j).getText().toLowerCase().equalsIgnoreCase("sign in")) {
                        loginText = anchors.get(j).getText();
                        break;
                    }
                }
            }
            if (!(loginText.equalsIgnoreCase(""))) {
                Boolean isPresent = driver.findElements(By.partialLinkText(loginText)).size() > 0;
                if (isPresent) {
                    try {
                        driver.findElement(By.partialLinkText(loginText)).click();
                        pwd = driver.findElement(By.cssSelector("input[type='password']"));
                    } catch (NoSuchElementException n) {
                        System.out.println(n);
                    }
                }
            }
        }*/
//</editor-fold>
if(status){
    if(URLHeuristicScore==1)
    {
        System.out.println("the webpage is Phishing based on URLHeuristic score");
          driver.quit();
          return "the webpage is Phishing based on URLHeuristic score";
    }
    else
     {
         System.out.println("the webpage is Phishing based on successful logging in second page");
          driver.quit();
          return "the webpage is Phishing based on successful logging in second page";
    }   
}
else
{
    System.out.println("second page based genuine website & URL Heuristic score is != 1");
    driver.quit();
   return "second page based genuine website & URL Heuristic score is != 1";   
   
}
    }
     public boolean comparePrimarydomain(String firstPageURL,String secondPageURL, SecondPage obj) throws URISyntaxException{
         firstPageURL=obj.getTopPrivateDomain(firstPageURL);
         secondPageURL=obj.getTopPrivateDomain(secondPageURL);
         if(firstPageURL.equalsIgnoreCase(secondPageURL))
             return true;
            else
             return false;
     }
    public String getTopPrivateDomain(String url) throws URISyntaxException {
    String host = new URI(url).getHost();
    InternetDomainName domainName = InternetDomainName.from(host);
    return domainName.topPrivateDomain().toString();
  }
    
}
