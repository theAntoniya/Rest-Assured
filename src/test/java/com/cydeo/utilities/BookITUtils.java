package com.cydeo.utilities;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
public class BookITUtils {


    public static String getToken(String email,String password){

        String accessToken = given().accept(ContentType.JSON)
                .queryParam("email", email)
                .queryParam("password", password).
                when().get("/sign")
                .then().statusCode(200)
                .extract().jsonPath()
                .getString("accessToken");

        // This is a verification to see we are getting not null Value
        assertThat(accessToken,not(emptyOrNullString()));

        return "Bearer "+accessToken;
    }


    // how  you build connection here ?
    /*
        Before all will init BASEURI since it is static field for RestAssured
        when we send any request it will use as BASEURI --> BOOKIT BASEURI
     */

}