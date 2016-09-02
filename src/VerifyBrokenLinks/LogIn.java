/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VerifyBrokenLinks;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author srinivas
 */
public class LogIn {
    public String logInAction(WebDriver driver, String URL, MainInterface screenobj) {
        String id = "null", phishStatus="null";
        String loginText = "";
        WebElement textElement = null;
        String passwordAttribute = null;
        boolean pwdStatus=false;
        driver.get(URL);
        screenobj.Screenshot(driver);
        SecondPage obj=new SecondPage();
        //********* check for the password field if present no need for modal window checking
        WebElement pwd = null;
        try {
            pwd = driver.findElement(By.cssSelector("input[type='password']"));
             pwdStatus=true;
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
                         pwdStatus=true;
                    } catch (NoSuchElementException n) {
                        System.out.println(n);
                    }
                }
            }
            
        }
if(pwdStatus){
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
                    if(pwd!=null)
                    if (pwd.equals(textElement)) {
                        textElement.sendKeys("test");
                        passwordAttribute = id;
                        break;
                    } else {
                        textElement.sendKeys("test");
                    }

                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (textElement != null) {
            if (textElement.equals(pwd)) {
                textElement.submit();
                System.out.println("second url: " + driver.getCurrentUrl());
                phishStatus=obj.secondPageLoginCheck(driver,passwordAttribute);
            }
        }
    }
        if(phishStatus.equalsIgnoreCase("null"))
        {
            driver.quit();
        return "no password field";
        }
        else
            return phishStatus;
    }
    
}
