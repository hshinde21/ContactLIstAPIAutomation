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
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class UpdateUserProfileAPI extends BaseTest {
    String server = PropertyReader.propertyReader("config.properties", "ContactListBaseURL");
    String Token = PropertyReader.propertyReader("config.properties", "token");

    String UpdateUser  = Route.GetProfile;
    String Url = server+UpdateUser;
    Response response;
    @Test(description = "updating  new user for the test")
    public void UpdateUser() throws IOException, ParseException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", Constants.CONTENT_TYPE_JSON);
        headers.put("Authorization", "Bearer " + Token);
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidatePostMethod", "Validate 201 Status Code for post request");
        System.out.println(Url);
        response = given().log().everything()
                .headers(headers)
                .body(JsonReader.getJsonData("UpdatedUser.json"))
                .when()
                .patch(Url);
        response.getBody().prettyPrint();

        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        assertThat(response.getBody().path("firstName"),is(equalTo("Updated")));



    }
}
