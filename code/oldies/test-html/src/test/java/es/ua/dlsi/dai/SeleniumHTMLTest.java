package es.ua.dlsi.dai;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumHTMLTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setUp(){
        driver = new HtmlUnitDriver();
    }

    @Test
    public void testUniversitatAlacant() {
        driver.get("http://www.ua.es");
        // Espera como máximo 10 segundos a que el elemento esté disponible:
        WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#footer1Columna2 > p:first-child > a")));
        assertEquals("informacio@ua.es",element.getText());
    }

    @AfterClass
    public static void cleanUp(){
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

}


