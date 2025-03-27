package src.test.ContactList.Users;

import core.Route;
import core.StatusCode;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import utils.Constants;
import utils.PropertyReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

public class GetUserProfileAPI {
    String Token = PropertyReader.propertyReader("config.properties", "token");

    String server = PropertyReader.propertyReader("config.properties", "ContactListBaseURL");

    String getProfile = Route.GetProfile;
    String Url = server + getProfile;
    Response response;


    @Test(description = "Send a GET request and validate the response body using 'then'")
    public void ValidateGetProfile() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", Constants.CONTENT_TYPE_JSON);
        headers.put("Authorization", "Bearer " + Token);
        given()
                .headers(headers).
                when()
                .get(Url)
                .then()
                .assertThat()
                .statusCode(StatusCode.SUCCESS.code)

                .body(is(notNullValue()));
    }



    @Test(description = "Send a GET request and store the response in a variable" )
    public void ValidateGetRequestWithoutToken() {



        // Send a GET request and store the response in a variable
         response =
                given()
                        .header("Content-Type", Constants.CONTENT_TYPE_JSON)                        .when()
                        .get(Url)
                        .then()
                        .extract()
                        .response();
        response.getBody().prettyPrint();
        assertThat(response.getStatusCode(), equalTo(StatusCode.UNAUTHORIZED.code));
        assertThat(response.getBody().path("error"), equalTo("Please authenticate."));


        // Use Hamcrest to check that the response body contains specific items
//        assertThat(response.jsonPath().getList("title"), hasItems("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "qui est esse"));
    }

}

