package com.example.tests.api;

import com.example.framework.DBManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReqResApiTest {

    @Test
    public void testGetUsers() {
        String testName = "API_GetUsers";
        try {
            Response response = RestAssured
                    .given()
                    .when()
                    .get("https://reqres.in/api/users?page=2")
                    .then()
                    .extract().response();

            int statusCode = response.getStatusCode();
            Assert.assertEquals(statusCode, 200, "API Test Failed");

            int page = response.jsonPath().getInt("page");
            Assert.assertEquals(page, 2, "Page number mismatch!");

            // ✅ Save success in DB
            DBManager.saveTestResult(testName, "PASS", "API returned correct response");

        } catch (AssertionError | Exception e) {
            // ✅ Save failure in DB
            DBManager.saveTestResult(testName, "FAIL", e.getMessage());
            throw e;
        }
    }
}
