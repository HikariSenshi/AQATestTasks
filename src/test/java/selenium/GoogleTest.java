package selenium;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class GoogleTest {

    //this method will provide browser data for Selenide-framework
    @DataProvider(name = "browsers")
    public static Object[][] browserProvider() {
        return new Object[][]{
                {"chrome"},
                {"opera"},
                {"firefox"}
        };
    }

    private static void waitFullLoading() {
        Wait<WebDriver> wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(7));
        wait.until(driver -> {
            String exec = String
                    .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"));
            return ("complete".equals(exec));
        });
    }

    @BeforeMethod
    public void setUp(Object[] param) {
        Configuration.browser = param[0].toString();

    }

    @Test(dataProvider = "browsers", timeOut = 45000L)
    public void testSearchGoogle(@SuppressWarnings("unused") String browser) throws InterruptedException {
        //this method open google page
        Selenide.open("https://google.com");
        //send google request
        $(By.name("q")).setValue("gbsfo").pressEnter();
        Selenide.executeJavaScript(
                //mouse wheel click(that open link in new tab) don't support in Selenium, so we need to use JS
                "arguments[0].dispatchEvent(new MouseEvent( \"click\", { \"button\": 1, \"which\": 2 }))",
                //find the most exact result
                $x(".//a[contains(@href,\"gbsfo.com\")]")
        );
        Thread.sleep(5000);

    }

    @Test(dataProvider = "browsers", timeOut = 35000L)
    public void testLastHourRequest(@SuppressWarnings("unused") String browser) throws InterruptedException {
        //this method open google page
        Selenide.open("https://google.com");
        //send google request
        $(By.name("q")).setValue("gbsfo").pressEnter();
        waitFullLoading();
        //open settings tab
        $("#hdtb-tls").click();
        //open list of time rules for requests
        $x(".//*[@id=\"qdr_\"]/../preceding-sibling::div[1]").click();
        //select last hour requests
        $("#qdr_h").click();

        Thread.sleep(5000);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }
}

