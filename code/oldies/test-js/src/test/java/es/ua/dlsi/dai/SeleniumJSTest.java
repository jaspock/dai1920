package es.ua.dlsi.dai;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumJSTest {

    private static WebDriver driver;
    private static JavascriptExecutor js;

    @BeforeClass
    public static void setUp(){
        /* Versiones recientes de Firefox no son compatibles con la rama 2.x de Selenium.
           Hay que usar la rama 3.x que requiere el ejecutable de Geckodriver además del navegador.
           Geckodriver se descarga de https://github.com/mozilla/geckodriver/releases/ 
           Es necesario indicar la ruta del ejecutable en la propiedad webdriver.gecko.driver */
        System.setProperty("webdriver.gecko.driver", "/bin/geckodriver");
        driver = new FirefoxDriver();
        if (driver instanceof JavascriptExecutor) {
          js = (JavascriptExecutor)driver;
        }
        else {
          throw new IllegalStateException("JavaScript not supported by this driver!");
        }
    }

    @Test
    public void testExample() throws InterruptedException {
        driver.get("http://tilde.club/~david/m");
        // Espera como máximo 20 segundos a que el evento load se haya disparado; nota: se usan funciones lambda de Java 8
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
          ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        
        /* Ejecución de código en JavaScript y uso del valor devuelto */
        double r= (double)js.executeScript("return smoothColor(100,2,4.6637803524832506e+47,1.727737463059871e+47);");
        assertEquals(1.2176376846128134,r,0.00000001);

        /* Ejecución de código JavaScript que modifica el CSS */
        WebElement element = driver.findElement(By.cssSelector("#description"));
        js.executeScript("arguments[0].style.color='#00FF00'", element);
        assertEquals("rgb(0, 255, 0)",element.getCssValue("color"));
        
        /* Cambio de valor en cuadro de texto y clic en botón */
        element = driver.findElement(By.cssSelector("#steps"));
        element.clear();
        element.sendKeys("10");        
        element = driver.findElement(By.cssSelector("#submitButton"));
        element.click();
        Thread.sleep(3000);  // TODO: usar mejor WebDriverWait detectando que se ha recalculado el conjunto
    }

    @AfterClass
    public static void cleanUp(){
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

}


