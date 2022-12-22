package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PathParameters {

    String url = "http://54.91.203.138:8000";

@Test
    public void practice(){

//    Given accept type is Json
    //    And Id parameter value is 500
    //    When user sends GET request to api/spartans/{id}
    Response response = RestAssured.given().accept(ContentType.JSON)
            .and().pathParam("id",500)
            .when().get("api/spartans/{id}");

//    Then response status code should be 404
    Assertions.assertEquals(404,response.statusCode());

//    And response content-type: application/json;charset=UTF-8
    Assertions.assertEquals("application/json;charset=UTF-8", response.contentType());

//    And "Spartan Not Found" message should be in response payload
    Assertions.assertTrue(response.body().asString().contains("Not Found"));

    response.prettyPrint();




}

}
