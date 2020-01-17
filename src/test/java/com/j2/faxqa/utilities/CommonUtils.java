package com.j2.faxqa.utilities;

import com.j2.faxqa.DriverBase;
import com.lazerycode.selenium.util.Query;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

import static com.j2.faxqa.DriverBase.getDriver;


public class CommonUtils  {

    public CommonUtils setCookie(String cookieName, String cookieValue, String cookieDomain) throws Exception {
        WebDriver driver = getDriver();

        Cookie ck = new Cookie(cookieName, cookieValue, cookieDomain);
        driver.manage().addCookie(ck);
        return this;
    }

    public CommonUtils noticeHandler() throws Exception {
        WebDriver driver = getDriver();

        WebElement notice = driver.findElement(By.cssSelector("a[class^='cn-set-cookie']"));
        if(notice.isDisplayed()) {
            System.out.println("Cookie footer is displayed... handling.");
            notice.click();
            System.out.println("Cookie footer Accept button clicked!");
        } else {
            System.out.println("Cookie footer is not displayed...");
            return this;
        }
        return this;
    }


    /**
     *
     * Accepts a By locator param to clear the field for the field specified by the locator.
     * The method first tries to use the Selenium clear() method. If it fails,
     * detects host OS and clears the field appropriately.
     *
     * @param locator
     * @return
     */

    public CommonUtils clearField(Query locator) {
        String hostOS = System.getProperty("os.name").toLowerCase();
        locator.findWebElement().clear();
        String e = locator.findWebElement().getText();
        if (hostOS.contains("win") && !e.isEmpty()) {
            System.out.println("Detected OS: " + hostOS);
            locator.findWebElement().sendKeys(Keys.chord(Keys.CONTROL, "a"), "55");
        } else if (hostOS.contains("os") && !e.isEmpty()) {
            System.out.println("Detected OS: " + hostOS);
            locator.findWebElement().sendKeys(Keys.chord(Keys.COMMAND, "a"), "55");
        }
        return this;
    }

    /**
     * Accepts a param for a WebElement object. Method to scroll the view to
     * an element to the center of the view using Selenium's 'Action' class.
     *
     * @param element
     * @return
     *
     */

    public CommonUtils scrollToElement(WebElement element) throws Exception {
        WebDriver driver = getDriver();
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        System.out.println("Scrolled WebElement into center of view : " + element);
        return this;
    }

    /**
     * Accepts a param for a WebElement object. Method utilizing JS
     * to scroll an element to the center of the view.
     *
     * @param element
     * @return
     *
     */

    public CommonUtils scrollToElementJS(WebElement element) throws Exception {
        WebDriver driver = getDriver();
        Actions actions = new Actions(driver);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        actions.moveToElement(element).perform();
        return this;
    }

    /**
     * Accepts a param for a Query locator. Method utilizing JS
     * to scroll an element to the center of the view.
     *
     * @param query
     * @return
     * @throws Exception
     */

    public CommonUtils scrollToElementJS(Query query) throws Exception {
        WebDriver driver = getDriver();
        Actions actions = new Actions(driver);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", query);
        return this;
    }


    /**
     * Method to generate a Bacon Ipsum string of text. Accepts params to define type (ie: meat, meat&filler),
     * paragraphs, where to start in the paragraph, and the format returned (ie: json, text, html)
     *
     * @param type
     * @param paragraphs
     * @param startWithLorem
     * @param format
     * @return
     * @throws Exception
     */

    public String baconIpsumGenerator(String type, Integer paragraphs, Integer startWithLorem, String format) {
        RestAssured.baseURI = "https://baconipsum.com/api/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("?type=" + type + "&paras=" + paragraphs + "&start-with-lorem=" + startWithLorem + "&format=" + format);
        ResponseBody body = response.getBody();
        return body.asString();
    }

    /**
     * Handy method to pull options from a dropdown element into
     * a list. Accepts a By locator as a param.
     *
     * @param by
     * @return
     * @throws Exception
     */

    public List<WebElement> getAllOptions(By by) throws Exception {
        WebDriver driver = getDriver();
        List<WebElement> options = driver.findElements(by);
        List<String> text = new ArrayList<>();
        for(int i=1; i<options.size(); i++) {
            text.add(options.get(i).getText());
            System.out.println(text);
        }
        return options;
    }

   public CommonUtils getIdFromCsv(String game) {
        String file;
        switch(game) {
            case "clash":
                file  = "src/resources/clash_users.csv";
                return this;
            case "minecraft":
                file = "src/resources/minecraft_users.csv";
                return this;
            case "lol":
                file = "src/resources/lol_users.csv";
                return this;
            case "fortnite":
                file = "src/resources/fortnite_users.csv";

        }

    return this;
    }
}
