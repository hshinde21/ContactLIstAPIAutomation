package src.test.ContactList.Users;

import core.Route;
import io.restassured.response.Response;
import utils.PropertyReader;

public class GetUserProfile {
    String server = PropertyReader.propertyReader("config.properties", "ContactListBaseURL");
    String path = Route.ContactListUsers;
    String login = Route.UserLogin;
    String Url = server + path;
    Response response;
}
