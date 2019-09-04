package es.ua.dlsi.dai;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumCSSTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setUp(){
        driver = new FirefoxDriver(); 
    }

    @Test
    public void testExample() {
        driver.get("http://example.com");
        // Espera como máximo 10 segundos a que el elemento esté disponible:
        WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div ")));
        assertEquals("rgba(255, 255, 255, 1)",element.getCssValue("background-color"));
    }

    @AfterClass
    public static void cleanUp(){
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

}


