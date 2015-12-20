package servlet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import utils.TSRTA_CONSTANTS;
import utils.Utils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by govardhanreddy on 9/7/15.
 */

// Checks the TSRTA portal and updates the customer's vehicle status
public class StatusRefresherScript {
    public static VehicleRegistrationStatus getVehicleStatus(String trNum) {

        WebDriver driver = new HtmlUnitDriver();

        driver.get(TSRTA_CONSTANTS.TSRTA_URL);

        WebElement select = driver.findElement(By.xpath("//select"));
        List<WebElement> allOptions = select.findElements(By.tagName("option"));
        for (WebElement option : allOptions) {
            if (!TSRTA_CONSTANTS.TR_NUMBER_INPUT_FORM_ID.equals(option.getAttribute("value"))) continue;
            System.out.println(String.format("Value is: %s", option.getAttribute("value")));
            option.click();
        }

        WebElement numEle = driver.findElement(By.id(TSRTA_CONSTANTS.TR_NUMBER_RESPONSE_FORM_ID_TSRTA_SITE));
        numEle.sendKeys(trNum);
        numEle.submit();
        WebElement btnEle = driver.findElement(By.id(TSRTA_CONSTANTS.GET_DATA_BUTTON_INPUT_FORM_TSRTA_SITE));
        btnEle.click();
        WebElement form = driver.findElement(By.id(TSRTA_CONSTANTS.FORM_ID_TSRTA_SITE));
        form.submit();
        //        Document document = Jsoup.parse(pageResponse);
//        Element element = document.select(TSRTA_CONSTANTS.STATUS_ID_TSRTA_SITE).first();
//        System.out.println(" element : " + element + " data " + element.data());
        String pageResponse = driver.getPageSource();
        if (!pageResponse.contains(TSRTA_CONSTANTS.STATUS_ID_TSRTA_SITE)) {
            System.out.println(" page does not contain status ????? ");
        } else {
            System.out.println(" page contains Status :)");
        }

        if (pageResponse.contains(TSRTA_CONSTANTS.NO_DATA_FOUND_TSRTA_SITE)) {
            System.out.println("No Data Found for TR Number : " + trNum);
            return null;
        }
        System.out.println(" contains ? " + pageResponse.contains(TSRTA_CONSTANTS.STATUS_ID_TSRTA_SITE) + " trnum " + trNum);

        String statusValueInForm = parseDataForId(pageResponse, TSRTA_CONSTANTS.STATUS_ID_TSRTA_SITE);
        String registrationNumber = parseDataForId(pageResponse, TSRTA_CONSTANTS.REGISTRATION_NUMBER_RESPONSE_TSRTA_SITE);
        String makerClass = parseDataForId(pageResponse, TSRTA_CONSTANTS.MAKER_CLASS_RESPONSE_TSRTA_SITE);
        String vehicleColor = parseDataForId(pageResponse, TSRTA_CONSTANTS.VEHICLE_COLOR_RESPONSE_TSRTA_SITE);

        VehicleRegistrationStatus registrationStatus = new VehicleRegistrationStatus();
        registrationStatus.setStatus(statusValueInForm);
        registrationStatus.setRegistrationNumber(registrationNumber);
        registrationStatus.setMakerClass(makerClass);
        registrationStatus.setColor(vehicleColor);

        Logger.getAnonymousLogger().log(Level.INFO, " Vehicle : " + registrationStatus.toString());

        driver.quit();

        return registrationStatus;
    }

    public static String parseDataForId(String page, String id) {
//        String substring = "<TD id=\"" + id + "\"";
        String substring = id;
        System.out.println(" substring : " + substring + " page contaiins substr ? " + page.contains(substring));
        String trimmedData = page.split(substring)[1];
        return getDataBetween(trimmedData, '>', '<');
    }

    public static String getDataBetween(String source, char start, char end) {
        String op = "";
        int iStart = 0;
        while (iStart < source.length()) {
            if (source.charAt(iStart) == start) break;
            iStart++;
        }
        int iEnd = iStart;
        while (iEnd < source.length()) {
            if (source.charAt(iEnd) == end) break;
            iEnd++;
        }
        for (int i = iStart + 1; i < iEnd; i++) {
            op += source.charAt(i);
        }
        return op.trim();
    }

    public static void main(String args[]) throws  Exception {
        List<Customer> allCustomers = Utils.getAllCustomers();
        for (Customer customer : allCustomers) {
            if (customer.getStatus() == Customer.Status.NEW) {

            }
        }
        getVehicleStatus(args[0]);
    }
}