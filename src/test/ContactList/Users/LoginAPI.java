package src.test.ContactList.Users;

import core.BaseTest;
import core.Route;
import core.StatusCode;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import utils.Constants;
import utils.ExtentReport;
import utils.JsonReader;
import utils.PropertyReader;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class LoginAPI extends BaseTest {
    String server = PropertyReader.propertyReader("config.properties", "ContactListBaseURL");
    String path = Route.ContactListUsers;
    String login = Route.UserLogin;
    String Url = server + path;
    Response response;

    @Test(description = "Adding new user for the test")
    public void LoginUser() throws IOException, ParseException {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidatePostMethod", "Validate 201 Status Code for post request");
        System.out.println(Url + login);
        response = given()
                .header("Content-Type", Constants.CONTENT_TYPE_JSON)
                .body(JsonReader.getJsonData("RequestBodyContactList.json"))
                .when()
                .post(Url + login);
        response.getBody().prettyPrint();

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);

        System.out.println("generateAuthToken executed successfully");


    }
    @Test(description = "Adding new user for the test")
    public void LoginUserwithInvalidPassword() throws IOException, ParseException {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidatePostMethod", "Validate 401 Status Code for post request");
        System.out.println(Url + login);
        response = given()
                .header("Content-Type", Constants.CONTENT_TYPE_JSON)
                .body(JsonReader.getJsonData("InvalidRequestBodyContactList.json"))
                .when()
                .post(Url + login);
        response.getBody().prettyPrint();

        assertEquals(response.getStatusCode(), StatusCode.UNAUTHORIZED.code);




    }
}