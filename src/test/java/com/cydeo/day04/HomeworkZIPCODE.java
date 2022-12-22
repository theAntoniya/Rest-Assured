package com.cydeo.day04;

import com.cydeo.utilities.ZippopotamUS;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeworkZIPCODE extends ZippopotamUS {


    @Test
    public void task1() {

        //Given Accept application/json
        //and path zipcode is 22031
        //when I send a GET request to /us endpoint
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("country", "US").and().pathParam("postal-code", "22031")

                .when().get("/{country}/{postal-code}");

        response.prettyPrint();


       //Then status code is 200

        assertEquals(200, response.statusCode());

        //and content type must be application/json

        assertEquals("application/json", response.contentType());

       //and Server header is cloudflare
        assertEquals("cloudflare", response.header("server"));

        //and report-to header exists
        assertTrue(response.headers().hasHeaderWithName("Report-To"));

        // Create JSON PATH OBJECT
        JsonPath jsonPath = response.jsonPath();

        //And body should contain the following information

        assertEquals(22031, jsonPath.getInt ("'post code'"));

        assertEquals("United States", jsonPath.getString("country"));

        assertEquals("US", jsonPath.getString("'country abbreviation'"));

        assertEquals("Fairfax", jsonPath.getString("places[0].'place name'"));

        assertEquals("Virginia", jsonPath.getString("places[0].state"));

        assertEquals(38.8604, jsonPath.getDouble("places[0].latitude"));

    }


}
