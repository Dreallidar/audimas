import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class audimasTest {

    public static CompletableFuture<Boolean> asyncClick(WebDriver driver, By locator) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                        .until(ExpectedConditions.elementToBeClickable(locator));
                element.click();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }
    public String generateUsername() {
        String[] characters = {"abcdefghijklmnopqrstuvwxyz", "0123456789"};

        Random random = new Random();

        StringBuilder username = new StringBuilder();

        // Generate username part
        int usernameLength = random.nextInt(10) + 5; // Random length between 5 to 14 characters
        for (int i = 0; i < usernameLength; i++) {
            String characterSet = characters[random.nextInt(2)]; // Selecting either alphabets or numbers
            char randomChar = characterSet.charAt(random.nextInt(characterSet.length()));
            username.append(randomChar);

        }
        return username.toString();
    }

    WebDriver _globalDriver;

    public WebElement snoozeUntilElement(By by) {
        WebDriverWait wait = new WebDriverWait(_globalDriver, Duration.ofSeconds(20));

        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return element;
    }

    public WebElement snoozeUntilClickable(By by) {
        WebElement element = snoozeUntilElement(by);
        WebDriverWait wait = new WebDriverWait(_globalDriver, Duration.ofSeconds(30));

        element = wait.until(ExpectedConditions.elementToBeClickable(by));
        return element;
    }


    public boolean isElementPresent(WebDriver driver, By by) {
        try {
            WebElement element = driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    @BeforeTest
    public void SetupWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        _globalDriver = new ChromeDriver(options);
        _globalDriver.get("https://www.audimas.lt");
        _globalDriver.manage().window().maximize();
        snoozeUntilClickable(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();
//        snoozeUntilClickable(By.xpath("/html/body/div[8]/div/div/div/button")).click();
        asyncClick(_globalDriver,By.xpath("/html/body/div[8]/div/div/div/button")).thenAccept(x->{
            System.out.println("Reklama uzdaryta");
        });




    }

    @Test
    public void paieskaKrepselisTest() {
        _globalDriver.findElement(By.xpath("/html/body/div[4]/header/div/div[3]/div[4]/div[1]/form/div/input")).sendKeys("Kelnes"); // Paieska
        snoozeUntilElement(By.xpath("/html/body/div[4]/header/div/div[3]/div[4]/div[3]/div/table/tbody/tr/td[1]/div[1]/a")); // Paieskos elemento laukimas
        _globalDriver.findElement(By.xpath("/html/body/div[4]/header/div/div[3]/div[4]/div[3]/div/table/tbody/tr/td[1]/div[1]/a")).click(); // Paieskos elemento paspaudimas
        snoozeUntilElement(By.xpath("/html/body/div[3]/div[3]/div/div/div/div[2]/div[2]/a")); // prekes elemento laukimas
        _globalDriver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div/div/div[2]/div[2]/a")).click(); // prekes elemento paspaudimas
        String kainaKelnes = snoozeUntilElement(By.xpath("/html/body/div[3]/div[3]/div/div/div/div[1]/div[3]/div/div[3]/div[1]/div[2]/form/div[1]/div/span")).getText(); // Pasiemam prekes kaina is katalogo
        String medvilninesKelnesName = snoozeUntilElement(By.xpath("/html/body/div[3]/div[3]/div/div/div/div[1]/div[3]/div/div[3]/div[1]/div[1]/div/p")).getText(); // pasiemam pavadinima prekes
        _globalDriver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div/div/div[1]/div[3]/div/div[3]/div[1]/div[2]/form/div[3]/div[2]/ul/li[5]/span")).click(); // spaudziam ant dydzio
        snoozeUntilElement(By.id("add2cart_button"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        _globalDriver.findElement(By.id("add2cart_button")).click(); // krepselis
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        snoozeUntilElement(By.xpath("/html/body/div[3]/header/div/div[3]/div[1]/div[1]/i"));
        _globalDriver.findElement(By.xpath("/html/body/div[3]/header/div/div[3]/div[1]/div[1]/i")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        _globalDriver.findElement(By.xpath("/html/body/div[3]/header/div/div[3]/div[1]/div[2]/div/div/div[2]/div[2]/div[2]/a")).click();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        snoozeUntilClickable(By.xpath("/html/body/div[3]/div[3]/div[2]/div/div/div/div/div/div[1]/div/a")).click();
        String krepselioKaina = snoozeUntilElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div[2]/div/div/div[2]/div[1]/div/div[3]/span[1]")).getText();
        String krepselioPrekPavadinimas = _globalDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div[2]/div/div/div[2]/div[1]/div/div[2]/span[1]")).getText();
        System.out.println(krepselioPrekPavadinimas + " " + krepselioKaina);








    }



}
