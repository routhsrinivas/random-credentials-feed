/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VerifyBrokenLinks;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author srinivas
 */
public class SecondPage {
     public String secondPageLoginCheck(WebDriver driver, String pwdAttribute) {
        String id = "null";
        String loginText = "";
        WebElement textElement = null;
        WebElement pwd = null;
        boolean status=false;
       try {
            pwd = driver.findElement(By.cssSelector("input[type='password']"));
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
                if (textElement.isDisplayed()) {
                    if (pwd.equals(textElement)) {
                        if(id.equalsIgnoreCase(pwdAttribute))
                        {
                            System.out.println("The webpage is genuine and is safe to visit");
                            driver.quit();
                            status=false;
                        break;
                        }
                    }
                    status=true;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        } catch (NoSuchElementException e) {
            System.out.println("no password field");
            System.out.println("the webpage is genuine and is safe to visit");
            driver.quit();
            return "genuine";
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
    System.out.println("the webpage is Phishing and is not safe to visit");
    driver.quit();
    return "phishing";
}
else
{
    driver.quit();
   return "genuine website";   
   
}
    }
    
}
