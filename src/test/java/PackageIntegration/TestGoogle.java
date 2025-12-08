package PackageIntegration;

import java.time.Duration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.jupiter.api.Assertions.*;
import io.qameta.allure.*;   // Allure anotaciones
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

@Epic("Buscador")
@Feature("Prueba de búsqueda en Bing")
public class TestGoogle {
    private WebDriver driver;

    @BeforeEach
    public void inicio() {
        // Configura automáticamente el driver con WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.bing.com/?setlang=es");
    }

    @Test
    @Story("Buscar 'bootstrap'")//contexto de negocio / requisito.
    @Description("Verifica que el buscador devuelve resultados y el título contiene la palabra buscada")//detalle caso de prueba
    @Severity(SeverityLevel.CRITICAL)
    public void testBusquedaBootstrap() {
        WebElement searchbox = driver.findElement(By.name("q"));
        searchbox.sendKeys("bootstrap");
        searchbox.submit();

        String titulo = driver.getTitle();
        System.out.println("Título actual: " + titulo);

        // Validación flexible: el título debe contener la palabra buscada
        assertTrue(titulo.toLowerCase().contains("bootstrap"),
                   "El título no contiene la palabra esperada");

        // Adjuntar captura al reporte Allure
        takeScreenshot();
    }

    // Método para adjuntar capturas en Allure
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @AfterEach
    public void cerrar() {
        if (driver != null) {
            driver.quit();
        }
    }
}