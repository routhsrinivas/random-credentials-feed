/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VerifyBrokenLinks;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author srinivas
 */
public class VerifyBrokenLinks {
 
	public static void main(String[] args) 
	{
		WebDriver driver=new FirefoxDriver();
		
		driver.manage().window().maximize();
                JavascriptExecutor js = (JavascriptExecutor) driver;
		String id="null";
                 WebElement textElement=null;
		//driver.get("https://routhsrinivas.github.io/coursera-test/test/test.html");
                driver.get("https://www.jabong.com/");
                // driver.get("https://www.paypal.com/signin?country.x=IN&locale.x=en_IN");
               // driver.get("http://localhost/paypal/Log%20in%20to%20your%20PayPal%20account.html");
                //driver.findElement(By.xpath("//*[@id=\"Email\"]")).sendKeys("srinivas");
                
              //  driver.findElement(By.xpath("//*[@id=\"next\"]")).click();

                Boolean isPresent = driver.findElements(By.partialLinkText("Log In/log in/LogIn/login/Log In")).size() > 0;
                 if(!isPresent)
                 {
                     try{
                      driver.findElement(By.partialLinkText("Sign in || SIGN IN")).click();
                     }
                     catch(NoSuchElementException e){
                       System.out.println(e);
                     }
                 }
                else
                 {
                 driver.findElement(By.partialLinkText("Log")).click();
                 }
                 
                 driver.switchTo().activeElement();
             
		List<WebElement> inputFields = driver.findElements(By.tagName("input"));
                System.out.println("Total inputfields are "+inputFields.size());
                for(int i=0;i<inputFields.size();i++)
		{
                    try{
                        if(!(inputFields.get(i).getAttribute("name").isEmpty())){
                            id=inputFields.get(i).getAttribute("name");
                             textElement=driver.findElement(By.name(id));
                        }
                        else{
			 if(!inputFields.get(i).getAttribute("id").isEmpty())
                         {
                         id=inputFields.get(i).getAttribute("id");
                         textElement=driver.findElement(By.id(id));
                         }
                         else{
                         if(!inputFields.get(i).getAttribute("class").isEmpty()){
                            id=inputFields.get(i).getAttribute("class");
                            textElement=driver.findElement(By.className(id));
                        }
                         }
                        }
                       
                        System.out.println(textElement.getTagName().toString());
                        if(textElement.isDisplayed())
                        {
                             WebElement pwd=null;
                             pwd=driver.findElement(By.cssSelector("input[type='password']"));
                             if(pwd.equals(textElement)){
			textElement.sendKeys("password");
                        textElement.submit();
                       
                             }
                             else
                                 textElement.sendKeys("nitk@123.com");
                             
			//verifyLinkActive(url);
                        }
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    
		}
             //   driver.findElement(By.tagName("form")).submit();
                
                
		List<WebElement> links=driver.findElements(By.tagName("a"));
		
		System.out.println("Total links are "+links.size());
		
		for(int i=0;i<links.size();i++)
		{
			
			WebElement ele1= links.get(i);
			
			String url=ele1.getAttribute("href");
			
			//verifyLinkActive(url);
			
		}
		
	}
	
	public static void verifyLinkActive(String linkUrl)
	{
        try 
        {
           URL url = new URL(linkUrl);
           
           HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
           
           httpURLConnect.setConnectTimeout(3000);
           
           httpURLConnect.connect();
           
           if(httpURLConnect.getResponseCode()==200)
           {
               System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage());
            }
          if(httpURLConnect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND)  
           {
               System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
            }
        } catch (Exception e) {
           
        }
    } 
 
}

/*private boolean existsElement(String id) {
    try {
        driver.findElement(By.id(id));
    } catch (NoSuchElementException e) {
        return false;
    }
    return true;
}*/