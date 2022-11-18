package selenium;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import web.browser.JavascriptActions;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BrowserAbilitiesTest {


    //this method will check if element is stale
    //if element is stale, we need to find it again
    //if element is not stale, we can use it
    @Test
    public void staleElementTest() {

        open("https://google.com");
        SelenideElement searchInputStale = $("[name='q']");
        //Test action
        searchInputStale.setValue("123");
        //Updating the page which causes the element will be out of DOM
        Selenide.refresh();
        //catching StaleElementReferenceException and updating the element
        try{
            searchInputStale.click();
        } catch (StaleElementReferenceException stale) {
            searchInputStale = $("[name='q']");
        }
        searchInputStale.setValue("abcde").pressEnter();

    }

    //this method will switch tab
    @Test
    public void tabSwitchTest() {

        open("https://stackoverflow.com");
        //Key sequence for opening new tab
        Selenide.switchTo().newWindow(WindowType.TAB);
        //Should open in new tab
        open("https://google.com");

    }

    @Test
    public void domUpdateTest() {
        //this method will check if DOM was updated
        //This tests is dummy (have not found examples of DOM update)
        open("https://example.com/");
        //dummy click will happen
        Selenide.refresh();
        JavascriptActions.waitFullPageLoading();
        String url = WebDriverRunner.url();
        //it possible that DOM was updated, but URL was not changed in this case, because we are on the main page
        Assert.assertFalse(url.startsWith("https://example.com/"));
        $(By.linkText("More information...")).should(not(exist));

    }

    @AfterTest
    public void tearDown() {
        Selenide.closeWebDriver();
    }

}
