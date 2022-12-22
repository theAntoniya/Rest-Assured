package com.cydeo.day01;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class P01_SimpleGetRequest {

    String url="http://174.129.150.19:8000/api/spartans";

    /**
     * When users end request to /api/spartans endpoint
     * Then user should be able see status code is 200
     * And Print out response body into screen
     */


    @Test
    public  void simpleGetRequest() {
        // send request to url and get response as Response interface
        Response response = RestAssured.get(url);

        // Both same no difference.We are gonna use to assert
        System.out.println("response.getStatusCode() = " + response.getStatusCode());
        System.out.println("response.statusCode() = " + response.statusCode());


        // it gives all sstatus line
        System.out.println("response.statusLine() = " + response.statusLine());


        int actualStatusCode = response.getStatusCode();

        Assertions.assertEquals(200,actualStatusCode);

        //how to print into screen ?
        response.prettyPrint();



    }

}
