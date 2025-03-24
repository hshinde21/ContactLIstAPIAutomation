package src.test.ContactList.contact;

import core.BaseTest;
import core.Route;
import core.StatusCode;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import utils.Constants;
import utils.ExtentReport;
import utils.PropertyReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertEquals;

public class ValidateGetContactID extends BaseTest {
    String Token = PropertyReader.propertyReader("config.properties", "token");
    String ContactListBaseURL = PropertyReader.propertyReader("config.properties", "ContactListBaseURL");
    String path = Route.AddContact;
    String InvalidId = Route.InvalidContactId;
    String contactId = Route.ContactId;
    String contactIDURL = ContactListBaseURL + path + contactId;
    String InvalidContactIDURL = ContactListBaseURL + path + InvalidId;

    @Test
    public void ValidateGetContactListFromID() throws IOException, ParseException {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidatePostMethod", "Validate 201 Status Code for post request");
        System.out.println(contactIDURL);
        System.out.println(contactId);
        System.out.println("Bearer " + Token);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", Constants.CONTENT_TYPE_JSON);
        headers.put("Authorization", "Bearer " + Token);

        Response response =
                given()
                        .headers(headers)
                        .when().log().all()
                        .get(InvalidContactIDURL);
        response.getStatusCode();
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);

       String res = response.getBody().path("email");
       assertThat("the email of the current id is ",res, equalTo("hesy@fake.com"));
        assertThat("Email is incorrect", res, containsString("@"));



//        System.out.println(response.getBody().prettyPrint());

//        assertThat(response.jsonPath().get("id"), hasItems(id)) ;


    }
    @Test(description = "Testing negative scenario")
    public void ValidateMissingID(){
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidatePostMethod", "Validate 201 Status Code for post request");
        System.out.println(contactIDURL);
        System.out.println(contactId);
        System.out.println("Bearer " + Token);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", Constants.CONTENT_TYPE_JSON);
        headers.put("Authorization", "Bearer " + Token);

        Response response =
                given()
                        .headers(headers)
                        .when().log().all()
                        .get(InvalidContactIDURL);
        response.getStatusCode();
        assertEquals(response.getStatusCode(), StatusCode.BAD_REQUEST.code);
        String msg  = response.getBody().toString();
        assertThat(msg, equalTo("Invalid Contact ID"));



    }
}