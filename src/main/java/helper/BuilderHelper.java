package helper;
import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BuilderHelper {
//    String Server = PropertyReader.propertyReader("config.properties", "server");

//    private RequestSpecification requestSpecification


    public static void BaseURL(String Server){
        requestSpecification = given().baseUri(Server);

    }
    public static Response PostRequest(String body, String endpoint){
        return requestSpecification.contentType(ContentType.JSON).body(body).post(endpoint);
    }

    public static  Response PutRequest(String body, String endpoint){
        return requestSpecification.contentType(ContentType.JSON).body(body).put(endpoint);
    }
    public  static Response GetRequest( String endpoint){
        return requestSpecification.contentType(ContentType.JSON).get(endpoint);
    }


}
