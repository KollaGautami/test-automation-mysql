package com.example.tests.ui;

import com.example.framework.DBManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginUITest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testCompleteFlightBooking() {
        String testName = "UI_CompleteBooking";
        try {
            // 1. Open BlazeDemo
            driver.get("https://blazedemo.com");
            Thread.sleep(2000);

            // 2. Select departure and destination
            driver.findElement(By.name("fromPort")).sendKeys("Boston");
            driver.findElement(By.name("toPort")).sendKeys("London");
            driver.findElement(By.cssSelector("input[type='submit']")).click();
            Thread.sleep(2000);

            // 3. Choose first flight
            driver.findElement(By.cssSelector("table.table tbody tr:nth-child(1) input")).click();
            Thread.sleep(2000);

            // 4. Fill passenger & payment details
            driver.findElement(By.id("inputName")).sendKeys("John Doe");
            driver.findElement(By.id("address")).sendKeys("123 Main St");
            driver.findElement(By.id("city")).sendKeys("New York");
            driver.findElement(By.id("state")).sendKeys("NY");
            driver.findElement(By.id("zipCode")).sendKeys("10001");
            driver.findElement(By.id("creditCardNumber")).sendKeys("4111111111111111");
            driver.findElement(By.id("creditCardMonth")).clear();
            driver.findElement(By.id("creditCardMonth")).sendKeys("12");
            driver.findElement(By.id("creditCardYear")).clear();
            driver.findElement(By.id("creditCardYear")).sendKeys("2025");
            driver.findElement(By.id("nameOnCard")).sendKeys("John Doe");
            Thread.sleep(2000);

            // 5. Purchase flight
            driver.findElement(By.cssSelector("input[type='submit']")).click();
            Thread.sleep(3000);

            // 6. Verify confirmation page
            String confirmationMessage = driver.getPageSource();
            boolean isConfirmed = confirmationMessage.contains("Id")
                    || confirmationMessage.contains("Thank you");

            Assert.assertTrue(isConfirmed, "Booking confirmation not found!");

            // ✅ Save success in DB
            DBManager.saveTestResult(testName, "PASS", "Flight booked and payment successful");

        } catch (Exception e) {
            // ❌ Save failure in DB
            DBManager.saveTestResult(testName, "FAIL", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000); // keep browser visible for 2 seconds
        if (driver != null) {
            driver.quit();
        }
    }
}
