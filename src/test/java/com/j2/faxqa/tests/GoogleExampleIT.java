package com.j2.faxqa.tests;

import com.j2.faxqa.DriverBase;
import com.j2.faxqa.listeners.testrail.TestRail;
import com.j2.faxqa.page_objects.GoogleHomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


public class GoogleExampleIT extends DriverBase {

    public GoogleExampleIT() throws Exception {
    }


    private ExpectedCondition<Boolean> pageTitleStartsWith(final String searchString) {
        return driver -> driver.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
    }
    @Test
    @TestRail(testCaseId = {65})
    public void googleCheeseExample() throws Exception {
        WebDriver driver = getDriver();
        driver.get("http://www.google.com");
        GoogleHomePage googleHomePage = new GoogleHomePage();

        System.out.println("Page title is: " + driver.getTitle());

        googleHomePage.enterSearchTerm("Cheese")
                .submitSearch();

        WebDriverWait wait = new WebDriverWait(driver, 10, 100);
        wait.until(pageTitleStartsWith("Cheese"));

        System.out.println("Page title is: " + driver.getTitle());
    }

    @Test
    @TestRail(testCaseId = {66})
    public void googleMilkExample() throws Exception {
        WebDriver driver = getDriver();
        driver.get("http://www.google.com");

        GoogleHomePage googleHomePage = new GoogleHomePage();

        System.out.println("Page title is: " + driver.getTitle());

        googleHomePage.enterSearchTerm("Milk")
                .submitSearch();

        WebDriverWait wait = new WebDriverWait(driver, 10, 100);
        wait.until(pageTitleStartsWith("Milk"));

        System.out.println("Page title is: " + driver.getTitle());
    }
}