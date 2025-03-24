package src.test.ContactList.contact;

import core.BaseTest;
import core.Route;
import core.StatusCode;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertEquals;

public class ValidateGetContactList extends BaseTest {
    String ContactListBaseURL = PropertyReader.propertyReader("config.properties", "ContactListBaseURL");
    String Token = PropertyReader.propertyReader("config.properties", "token");
    String path = Route.AddContact;
    String contactId = Route.ContactId;


    Response response;

    @Test
    public void ValidateGetContactList() throws IOException, ParseException {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidatePostMethod", "Validate 201 Status Code for post request");

        System.out.println(ContactListBaseURL + path);
        System.out.println("Bearer " + Token);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", Constants.CONTENT_TYPE_JSON);
        headers.put("Authorization", "Bearer " + Token);

        Response response =
                given()
                        .headers(headers)
                        .when()
                        .get(ContactListBaseURL + path);
        response.getBody().prettyPrint();
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        assertThat(response.jsonPath().getList(""), hasSize(2));
        List<String> expectedEmails = response.jsonPath().getList("email");

        assertThat(expectedEmails, hasItem("hesy@fake.com"));

    }

}   