package com.cydeo.day04;

import com.cydeo.utilities.ZippopotamUS;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P03_ZipcodeTest extends ZippopotamUS {

    /**
     * Create TestBase for zipcode
     * website ----> https://www.zippopotam.us/#
     *
     */

    /**
     * Given accept type is json
     * and country path param value is "us"
     * and postal code path param value is 22102
     * When I send get request to http://api.zippopotam.us/{country}/{postal-code}
     * Then status code is 200
     * Then "post code" is "22102"
     * And  "country" is "United States"
     * And "place name" is "Mc Lean"
     * And  "state" is "Virginia"
     */

    @Test
    public void test1() {

        //    Given accept type is json
//    and country path param value is "us"
//    and postal code path param value is 22102
//    When I send get request to http://api.zippopotam.us/{country}/{postal-code}

        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("country", "US").and().pathParam("postal-code", "22102")

                .when().get("/{country}/{postal-code}");

        response.prettyPrint();


//    Then status code is 200

        assertEquals(200, response.statusCode());

////        Then "post code" is "22102"
        JsonPath jsonPath = response.jsonPath();
        String postCode = jsonPath.getString("'post code'");
        assertEquals("22102", postCode);

//        And  "country" is "United States"
        assertEquals("United States", jsonPath.getString("country"));

//        And "place name" is "Mc Lean"
        assertEquals("Mc Lean", jsonPath.getString("places[0].'place name'"));

//        And  "state" is "Virginia"
        assertEquals("Virginia", jsonPath.getString("places[0].state"));

    }

}
