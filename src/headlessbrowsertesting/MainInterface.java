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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;

/**
 *
 * @author srinivas
 */
public class MainInterface {
    
    int rowcount = 0, index=0;
    String v0 = "null", v1 = "null";

    public WebDriver Execute(String browser) {
        // Set Browsers
        WebDriver driver = null;
        if (browser.equalsIgnoreCase("firefox")) {
        //    FirefoxProfile p = new FirefoxProfile();
        //    p.setPreference("javascript.enabled", false);
//driver = new FirefoxDriver(p);
           DesiredCapabilities dc = new DesiredCapabilities();
dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
System.setProperty("webdriver.gecko.driver", "C:\\Users\\srinivas\\Documents\\NetBeansProjects\\jars important\\geckodriver.exe");
driver = new FirefoxDriver(dc);
           //driver = new FirefoxDriver();
        }

//<editor-fold defaultstate="collapsed" desc="comment">
         /*  else if (browser.equalsIgnoreCase("chrome")) {
         
         {System.setProperty("webdriver.chrome.driver","C:/Users/srinivas/Selenium/chromedriver.exe");}
         driver = new ChromeDriver();
         }
         
         else if (browser.equalsIgnoreCase("ie")) {
         
         {System.setProperty("webdriver.ie.driver","C:/Users/Srinivas/Selenium/IEDriverServer.exe");}
         driver = new InternetExplorerDriver();
         {DesiredCapabilities iecapabilities = DesiredCapabilities.internetExplorer();
         iecapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);}
         }*/
//</editor-fold>
        // Implicit Wait and Maximize browser
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

          // Navigate to URL
        //driver.get(Constant.URL);
        return driver;
    }

    public void feedTOExcel(int rcount) throws FileNotFoundException, IOException, InvalidFormatException {
        InputStream inp = new FileInputStream("randomfeed.xlsx");
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet = wb.getSheetAt(0);
        int rowCount = rcount;
        int columnCount = -1;
        Object[][] bookData = {
            {v0, v1},};
        for (Object[] aBook : bookData) {
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
        FileOutputStream fileOut = new FileOutputStream("randomfeed.xlsx");
        wb.write(fileOut);
        fileOut.close();
    }
    
    public static void main(String[] args) {
        MainInterface obj = new MainInterface();
        String URLs = "C:\\Users\\srinivas\\Documents\\NetBeansProjects\\HeadLessBrowserTesting\\randomfeed.txt";
        String status="null";
        try {
            BufferedReader br = new BufferedReader(new FileReader(URLs));
            String list = br.readLine();
            while (list != null && !(list.equalsIgnoreCase(""))) {
                WebDriver driver = null;
                driver = obj.Execute("firefox");
                LogIn obj2 = new LogIn();
                list="http://"+list;
                status=obj2.logInAction(driver,list,obj);
                obj.feedGlobalVariables(status,list,obj);
                list = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    public void feedGlobalVariables(String status, String URL, MainInterface obj2){
        v0=URL;
        v1=status;
        try {
            obj2.feedTOExcel(rowcount);
        } catch (IOException ex) {
            Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        rowcount++;
    }
    public void Screenshot(WebDriver driver){
        v0="null";
        v1 = "null";
        try {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File imgsrc=ts.getScreenshotAs(OutputType.FILE);
        
            FileUtils.copyFile(imgsrc, new File("C:\\Users\\srinivas\\Documents\\NetBeansProjects\\HeadLessBrowserTesting\\Screenshots\\"+(++index)+".png"));
        } catch (IOException ex) {
            Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
        } 
        // some websites screenshots are not been taken by websriver --media.tumblr.com
        catch(WebDriverException y)
        {
            System.out.println(y);
        }
    }
}
