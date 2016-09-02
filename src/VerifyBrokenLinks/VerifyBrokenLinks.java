/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VerifyBrokenLinks;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


/**
 *
 * @author srinivas
 */
public class VerifyBrokenLinks {
    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        String id = "null";
        String loginText = "";
        WebElement textElement = null;
        String passwordAttribute=null;
        //driver.get("https://routhsrinivas.github.io/coursera-test/test/test.html");
        //driver.get("http://testphp.vulnweb.com/login.php");
                 driver.get("https://www.paypal.com/signin?country.x=IN&locale.x=en_IN");
                 //<editor-fold defaultstate="collapsed" desc="comment">
// driver.get("http://localhost/paypal/Log%20in%20to%20your%20PayPal%20account.html");
                 //driver.findElement(By.xpath("//*[@id=\"Email\"]")).sendKeys("srinivas");
                 //  driver.findElement(By.xpath("//*[@id=\"next\"]")).click();
                 /*System.out.println("first url: "+driver.getCurrentUrl());
                 System.out.println(driver.manage().getCookies().toString()+"\n");
                 String session = ((RemoteWebDriver) driver).getSessionId().toString();
                 System.out.println("SessionId:--\t"+session);
                 
                 try {
                 Cookie cookie = driver.manage().getCookieNamed("JSESSIONID");
                 System.out.println("JAVASESSION\n" + cookie.getValue());
                 } catch (Exception e) {
                 // System.out.println(e);
                 }
                 try {
                 String ASPNET_SessionId = driver.manage().getCookieNamed("ASP.NET_SessionId").toString();
                 System.out.println("ASPNET\n" + ASPNET_SessionId);
                 } catch (Exception e) {
                 // System.out.println(e);
                 }
                 try {
                 String PHP_SessionId = driver.manage().getCookieNamed("getSessionId").toString();
                 System.out.println("PHP\n" + PHP_SessionId);
                 } catch (Exception e) {
                 // System.out.println(e);
                 }*/
//</editor-fold>
        //********* check for the password field if present no need for modal window checking
        WebElement pwd = null;
        try {
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
            if(!(loginText.equalsIgnoreCase(""))){
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
        }
     //   driver.switchTo().activeElement();

        List<WebElement> inputFields = driver.findElements(By.tagName("input"));
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

             //   System.out.println(textElement.getTagName().toString());
                if (textElement.isDisplayed()) {

                    if (pwd.equals(textElement)) {
                        textElement.sendKeys("test");
                        passwordAttribute=id;
                        break;
                       // textElement.submit();
                    } else {
                        textElement.sendKeys("test");
                    }
                    //verifyLinkActive(url);
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        }
       if(textElement!=null)
       {
        if(textElement.equals(pwd))
        {
         textElement.submit();
         System.out.println("second url: "+driver.getCurrentUrl());
        }
       }
       
//<editor-fold defaultstate="collapsed" desc="comment">
//********************** for total number of links ***********************
      /* List<WebElement> links = driver.findElements(By.tagName("a"));
       
       System.out.println("Total links are " + links.size());
       
       for (int i = 0; i < links.size(); i++) {
           
           WebElement ele1 = links.get(i);
           
           String url = ele1.getAttribute("href");
           
           //verifyLinkActive(url);
       }*/
//</editor-fold>

    }
     

    //<editor-fold defaultstate="collapsed" desc="comment">
    /*public static void verifyLinkActive(String linkUrl) {
    try {
    URL url = new URL(linkUrl);
    
    HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
    
    httpURLConnect.setConnectTimeout(3000);
    
    httpURLConnect.connect();
    
    if (httpURLConnect.getResponseCode() == 200) {
    System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
    }
    if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
    System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - " + HttpURLConnection.HTTP_NOT_FOUND);
    }
    } catch (Exception e) {
    
    }
    }
    */
//</editor-fold>

}

//<editor-fold defaultstate="collapsed" desc="comment">
/*private boolean existsElement(String id) {
try {
driver.findElement(By.id(id));
} catch (NoSuchElementException e) {
return false;
}
return true;
}*/
//</editor-fold>
