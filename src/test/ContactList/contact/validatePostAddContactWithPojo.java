package src.test.ContactList.contact;

import core.BaseTest;
import core.Route;
import core.StatusCode;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import pojo.postRequestBodyContactList;

import utils.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertEquals;

public class validatePostAddContactWithPojo extends BaseTest {
    String ContactListBaseURL = PropertyReader.propertyReader("config.properties", "ContactListBaseURL");
    String Token = PropertyReader.propertyReader("config.properties", "token");

    String path = Route.AddContact;
    String contactId = Route.ContactId;

    String contactIDURL = ContactListBaseURL + path + contactId;


    tokenGenerationforContactlist authtoken = new tokenGenerationforContactlist();


    @Test
    public void ValidateAddContactWithPojo() throws IOException, ParseException {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("ValidateAddContactWithPojo", "Validate 201 Status Code for post request");


        postRequestBodyContactList postBody = getPostRequestBodyContactList();
        String token = authtoken.generateAuthToken();


        Response response =

                given().log().all()
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", Constants.CONTENT_TYPE_JSON)
                        .body(postBody)
                        .when()
                        .post(ContactListBaseURL + path);
        System.out.println(response.getStatusCode());
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);


    }

    @Test
    public void ValidatePutContactList() throws IOException, ParseException {
        System.out.println(ContactListBaseURL + path);
        String token = authtoken.generateAuthToken();
        System.out.println(token);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", Constants.CONTENT_TYPE_JSON);
        headers.put("Authorization", "bearer" + token);
        Response response =
                given().headers(headers)
                        .when()
                        .get(ContactListBaseURL + path);
        response.getBody().prettyPrint();
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        response.then().body("email", equalTo("jdoe@fake.com"));

    }






    private static postRequestBodyContactList getPostRequestBodyContactList() {
        postRequestBodyContactList postBody= new postRequestBodyContactList();
        postBody.setFirstName("john");
        postBody.setLastName("berkssss");
        postBody.setBirthdate("1970-01-01");
        postBody.setEmail("hesy@fake.com");
        postBody.setPhone("8005555555");
        postBody.setStreet1("123");
        postBody.setStreet2("321");
        postBody.setCity("New York");
        postBody.setPostalCode("784551");
        postBody.setStateProvince("NA");
        postBody.setCountry("US");
        return postBody;
    }


}
