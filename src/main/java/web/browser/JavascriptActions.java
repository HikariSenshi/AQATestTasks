package web.browser;

import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class JavascriptActions {

    public static void waitFullPageLoading() {

        Selenide.Wait().withTimeout(Duration.ofMillis(7000)).until(driver -> {
            String exec = String
                    .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"));
            return ("complete".equals(exec));
        });

    }
}
