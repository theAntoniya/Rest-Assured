package com.cydeo.day02;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class QueryParameters {

    String url = "http://54.91.203.138:8000";

    /*
    Given accept type is Json
    And Query Parameter values are:
    gender|Female
    nameContains|J
    When user sends Get request to api/spartans/search
    Then response status code should be 200
    And response content -type: application/json;charset=UTF-8
    And "Female" shouls be in response payload (pauload same as "body")
    And "Janette" should be in response payload
     */

    @Test
    public void QueryParam1(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender","Female")
                .and().queryParam("nameContains", "J")
                .when().get( "api/spartans/search");

        //verify status code
        Assertions.assertEquals(200,response.statusCode());

        //verify content type
        Assertions.assertEquals("application/json;charset=UTF-8",response.contentType());

        //verify Female
        Assertions.assertTrue(response.body().asString().contains("Female"));

        //verify Male not exist
        Assertions.assertFalse(response.body().asString().contains("Male"));

        //verify Janette
        Assertions.assertTrue(response.body().asString().contains("Janette"));

        response.prettyPrint();
    }

    @Test
    public void queryParams2(){
        //creating map for query Params

        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("gender", "Female");
        paramsMap.put("nameContains", "J");

        //send request
        Response response = given().accept(ContentType.JSON)
                .and().queryParams(paramsMap)
                .when().get("api/spartans/search");


        //verify status code
        Assertions.assertEquals(200,response.statusCode());

        //verify content type
        Assertions.assertEquals("application/json;charset=UTF-8",response.contentType());

        //verify Female
        Assertions.assertTrue(response.body().asString().contains("Female"));

        //verify Male not exist
        Assertions.assertFalse(response.body().asString().contains("Male"));

        //verify Janette
        Assertions.assertTrue(response.body().asString().contains("Janette"));

        response.prettyPrint();
    }
}
