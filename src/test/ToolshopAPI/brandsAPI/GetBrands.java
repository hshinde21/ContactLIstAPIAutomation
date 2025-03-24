package src.test.ToolshopAPI.brandsAPI;

import core.BaseTest;
import core.Route;
import core.StatusCode;
import helper.BuilderHelper;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import utils.ExtentReport;
import utils.JsonArrayReader;
import utils.JsonReader;
import utils.PropertyReader;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.testng.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GetBrands extends BaseTest {

    Response response;
    String server = PropertyReader.propertyReader("config.properties", "server");
    String brandsPath = PropertyReader.propertyReader("config.properties", "brandsPath");
    String URL = server + brandsPath;

    @Test(description = "Validate the  Brands schema")
    public void ValidateSchema() {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidateSchema", "Validate 200 Status Code for GET request for schema");

        File schema = new File("resources/ExpectedSchemaToolShop.json");

        given().
                when().
                get("https://api.practicesoftwaretesting.com/brands")
                .then()
                .assertThat()
                .statusCode(StatusCode.SUCCESS.code)
                .body(JsonSchemaValidator.matchesJsonSchema(schema));


    }

    @Test(description = "Validate the  Brands schema")
    public void JsonValidateSchema() {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidateSchema", "Validate 200 Status Code for GET request for schema");

        File schema = new File("resources/ExpectedSchemaToolShop.json");


        given().
                when().
                get("/brands")
                .then()
                .assertThat()
                .statusCode(StatusCode.SUCCESS.code)
                .body(JsonSchemaValidator.matchesJsonSchema(schema));


    }

    @Test(description = "Validate get brand method")
    public void getBrands() {

        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidateGetMethod", "Validate 200 Status Code for GET request for schema");

        response =
                given()
                        .header("Content-Type", "application/json")
                        .when().log().all()
                        .get(URL);

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        // Use Hamcrest to check that the response body contains specific items in a specific order
        List<String> expectedBrands = Arrays.asList("01JNWA3G1EMRQF5J021DAD3D4S", "01JNWA3G1EMRQF5J021DAD3D4T");


        assertThat(response.jsonPath().getList("id"), contains(expectedBrands.toArray(new String[0])));

        // Use Hamcrest to check that the response body contains specific items
        assertThat(response.jsonPath().getList("name"), hasItems("ForgeFlex Tools", "MightyCraft Hardware"));
        // Use Hamcrest to validate the content type of the response body
        assertTrue(response.contentType().contains(" application/json"));


    }

    @Test(description = "Validate post method for the tools shop")
    public void postBrands() throws IOException, ParseException {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidatePostMethod", "Validate 201 Status Code for post request");
        response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(JsonReader.getJsonData("postRequestBodyShoptools.json"))
                        .when()
                        .post(URL);

//            printing the body as json
        response.body().prettyPrint();

        // Use Hamcrest to check that the response code
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);

        // Use Hamcrest to check that the response body contains specific items
        assertThat(response.getBody().path("slug"), equalTo("AutomationTestings"));


    }

    @Test(description = "validate the brandID")
    public void getBrandID() throws IOException, ParseException {
        ExtentReport.extentreport.
                startTest("ValidateGetMethod", "Validate 200 Status Code for GetBrandID request");
        System.out.println(URL + Route.brandID);
        response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .get(URL + Route.brandID);
        response.body().prettyPrint();
        // Use Hamcrest to check that the response code
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        // Use Hamcrest to check that the response body contains specific items
        assertThat(response.getBody().path("slug"), equalTo("forgeflex-tools"));


    }


    @Test(description = "validate the patched brandID")
    public void PatchBrandID() throws IOException, ParseException {
        ExtentReport.extentreport.
                startTest("ValidatePutMethod", "Validate 200 Status Code for GetBrandID request");
        System.out.println(URL + Route.brandID);
        response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(JsonReader.getJsonData("postRequestBodyShoptools.json"))
                        .put(URL + Route.brandID);
        response.body().prettyPrint();
        // Use Hamcrest to check that the response code
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        // Use Hamcrest to check that the response body contains specific items
        assertThat(response.getBody().path("success"), equalTo(true));
        assertThat(response.getBody().path("slug"), equalTo("AutomationTestingssss"));


    }


}