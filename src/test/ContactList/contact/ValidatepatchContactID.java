package src.test.ContactList.contact;

import core.Route;
import core.StatusCode;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import pojo.putRequestBodyContactList;
import pojo.putResponseBodyContactList;
import utils.Constants;
import utils.PropertyReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertEquals;

public class ValidatepatchContactID {
    String ContactListBaseURL = PropertyReader.propertyReader("config.properties", "ContactListBaseURL");
    String Token = PropertyReader.propertyReader("config.properties", "token");
    String path = Route.AddContact;
    String contactId = Route.ContactId;

    String contactIDURL = ContactListBaseURL + path + contactId;
    @Test(description = "Testing the patch request")
    public void ValidatePatchContactList() throws IOException, ParseException {

        System.out.println(contactIDURL);
        putRequestBodyContactList putBody = getPutRequestBodyContactList();
        System.out.println("Bearer " + Token);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", Constants.CONTENT_TYPE_JSON);
        headers.put("Authorization", "Bearer " + Token);

        Response response =
                given().headers(headers)
                        .when().body(putBody)
                        .patch(contactIDURL);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        response.getBody().prettyPrint();



    }
    private static putRequestBodyContactList getPutRequestBodyContactList() {
        putRequestBodyContactList putBody= new putRequestBodyContactList();
        putBody.set_id("/67d658c3cbea0b00134e5004");

        putBody.setFirstName("Anna");
        putBody.setLastName("berkssss");
        putBody.setBirthdate("1970-01-01");

        return putBody;
    }


}
