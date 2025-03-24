package src.test.ContactList.contact;

import core.Route;
import core.StatusCode;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import pojo.putRequestBodyContactList;
import pojo.putResponseBodyContactList;
import utils.Constants;
import utils.ExtentReport;
import utils.PropertyReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertEquals;

public class validatePutContactID {
    String ContactListBaseURL = PropertyReader.propertyReader("config.properties", "ContactListBaseURL");
    String Token = PropertyReader.propertyReader("config.properties", "token");
    String path = Route.AddContact;
    String contactId = Route.ContactId;

    String contactIDURL = ContactListBaseURL + path + contactId;
    @Test(description = "Validate put request for the contactList")
    public void ValidatePutContactList() throws IOException, ParseException {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidatePutContactList", "Validate 201 Status Code for post request");


        System.out.println(contactIDURL);
        putRequestBodyContactList putBody = getPutRequestBodyContactList();
        System.out.println("Bearer " + Token);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", Constants.CONTENT_TYPE_JSON);
        headers.put("Authorization", "Bearer " + Token);

        Response response =
                given().headers(headers)
                        .when().body(putBody)
                        .put(contactIDURL);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        response.getBody().prettyPrint();
        putResponseBodyContactList responseBody = response.as(putResponseBodyContactList.class);

        assertThat(responseBody.get_id(),equalTo("67d658c3cbea0b00134e5004"));
        assertThat(responseBody.getEmail(), equalTo("hesy@fake.com"));
        assertEquals(0, responseBody.getV1());


    }
    private static putRequestBodyContactList getPutRequestBodyContactList() {
        putRequestBodyContactList putBody= new putRequestBodyContactList();
        putBody.set_id("/67d658c3cbea0b00134e5004");

        putBody.setFirstName("john");
        putBody.setLastName("berkssss");
        putBody.setBirthdate("1970-01-01");
        putBody.setEmail("hesy@fake.com");
        putBody.setPhone("8005555555");
        putBody.setStreet1("123");
        putBody.setStreet2("321");
        putBody.setCity("New York");
        putBody.setPostalCode("784551");
        putBody.setStateProvince("NA");
        putBody.setCountry("US");
        return putBody;
    }

}
