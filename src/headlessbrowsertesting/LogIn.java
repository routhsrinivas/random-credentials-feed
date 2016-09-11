/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package headlessbrowsertesting;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author srinivas
 */
public class LogIn {

    public String logInAction(WebDriver driver, String URL, MainInterface screenobj) {
        String id = "null", phishStatus = "null";
        String loginText = "";
        WebElement textElement = null;
        String passwordAttribute = null;
        boolean pwdStatus = false;
        driver.get(URL);
        screenobj.Screenshot(driver);
        SecondPage obj = new SecondPage();
        // to deal with redirect url ( first page url and second page url mismatch issue(fb.com and facebook.com))
        URL = driver.getCurrentUrl();
        //********* check for the password field if present no need for modal window checking
        WebElement pwd = null;
        try {
            pwd = driver.findElement(By.cssSelector("input[type='password']"));
            pwdStatus = true;
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
                        pwdStatus = true;
                    } catch (NoSuchElementException n) {
                        System.out.println(n);
                    }
                }
            }

        }
        if (pwdStatus) {
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
                        if (pwd != null) {
                            if (pwd.equals(textElement)) {
                                textElement.sendKeys("test@1234");
                                passwordAttribute = id;
                                break;
                            } else {
                                textElement.sendKeys("test@gmail.com");
                            }
                        }

                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (textElement != null) {
                if (textElement.equals(pwd)) {
                    textElement.submit();

                    //************* for handling pop up windows *****************
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        String alertmsg = driver.switchTo().alert().getText();
                        System.out.println(alertmsg);
                        driver.switchTo().alert().accept();
                    } catch (NoAlertPresentException e) {
                        System.out.println(e);
                    }

                    phishStatus = obj.secondPageLoginCheck(driver, passwordAttribute, URL);
                }
            }
        }
        if (phishStatus.equalsIgnoreCase("null")) {
            driver.quit();
            return "no password field in start page";
        } else {
            return phishStatus;
        }
    }

}
