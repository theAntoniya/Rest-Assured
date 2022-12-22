package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class P01_SpartanGetRequests {
    String url = "http://54.91.203.138:8000";


    /*
     * Given accept  content type is application/json
     * When user sends GET request /api/spartans endpoint
     * Then status code should be 200
     * And Content type should be application/json
     */
    @DisplayName("GET All Spartans")
    @Test
    public void getAllSpartans() {

        Response response = RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .get(url + "/api/spartans");

        // print response
        // response.prettyPrint();


        // how to get status code
        int actualStatusCode = response.statusCode();

        Assertions.assertEquals(200, actualStatusCode);

        //how can we get ContentType
        String actualContentType = response.contentType();

        Assertions.assertEquals("application/json", actualContentType);

        //how to get header info
        String connection = response.header("Connection");
        System.out.println("connection = " + connection);


        // get content type with header
        System.out.println("response.header(\"Content-Type\") = " + response.header("Content-Type"));

        // can we get connection() same as contentType() insteading of using header?
        // A --> Rest Assured created couple of method for common usage.
        // statusCode() contentType() methods are specificly created by them.So there is connection method


        // get date header
        System.out.println("response.header(\"Date\") = " + response.header("Date"));


        //how can we verify date is exist ?
        boolean date = response.headers().hasHeaderWithName("Date");

        Assertions.assertTrue(date);

    }


    /*
     * Given accept  content type is application/json
     * When user sends GET request /api/spartans/3 endpoint
     * Then status code should be 200
     * And Content type should be application/json
     * And response body needs to contains Fidole
     */


    @DisplayName("GET Single Spartan")
    @Test
    public void getSpartan() {

        Response response = RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .get(url + "/api/spartans/3");

        //Verify status code
        Assertions.assertEquals(200, response.statusCode());


        response.prettyPrint();

        //Verify contentType is application json
        Assertions.assertEquals(ContentType.JSON.toString(), response.contentType());
        Assertions.assertEquals("application/json", response.header("Content-Type"));
        Assertions.assertEquals(ContentType.JSON.toString(), response.header("Content-Type"));
        //ContentType.JSON.toString() --> it makes enum to String to able to use in assertions

        //Verify body contains Fidole
        Assertions.assertTrue(response.body().asString().contains("Fidole"));
        /*
            it is not the good way to make assertion.In this we are just conversting response to String and with the help of String contains
            we are just looking into Response.In real we need verfiy name is Fidole.Thats we need access name key to get value of it which Fidole
         */

        // if we dont have related header or if we have typo it will return NULL
        System.out.println("response.header(\"KeepAlive\") = " + response.header("KeepAlive"));


    }

    /*
        Given no headers provided
        When Users send GET request to /api/hello
        Then response status code should be 200
        And Content type header should be "text/plain;charset=UTF-8"
        And header should contain Date
        And Content-Length should be 17
        And body should be "Hello from Sparta"
    */
    @DisplayName("GET Hello Sparta")
    @Test
    public void helloSpartan() {

        Response response = RestAssured.when().get(url + "/api/hello");

        response.prettyPrint();
        //Then response status code should be 200
        Assertions.assertEquals(200, response.statusCode());

        //And Content type header should be "text/plain;charset=UTF-8"
        Assertions.assertEquals("text/plain;charset=UTF-8", response.contentType());

        //And header should contain Date
        Assertions.assertTrue(response.headers().hasHeaderWithName("Date"));

        //And Content-Length should be 17
        Assertions.assertEquals("17", response.header("Content-Length"));

        //And body should be "Hello from Sparta"
        Assertions.assertTrue(response.body().asString().contains("Hello from Sparta"));
    }
}
