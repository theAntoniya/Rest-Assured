package com.cydeo.day07;


import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HomeworkDay07 extends SpartanTestBase {


    static int spartanId;
    @DisplayName("POST spartan with Map body")
    @Test
    public void test1() {

        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("name", "API Flow POST");
        requestBody.put("gender", "Male");
        requestBody.put("phone", "1231231233");


        String expectedMessage = "A Spartan is Born!";

        JsonPath jsonPath = given().log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody).
                when().post("/api/spartans").prettyPeek().
                then().statusCode(201)
                .contentType("application/json").extract().jsonPath();


        assertEquals(expectedMessage, jsonPath.getString("success"));
        assertEquals(requestBody.get("name"), jsonPath.getString("data.name"));
        assertEquals(requestBody.get("gender"), jsonPath.getString("data.gender"));
        assertEquals(requestBody.get("phone"), jsonPath.getString("data.phone"));

        spartanId = jsonPath.getInt("data.id");
        System.out.println(spartanId);
        System.out.println("--------------GET----------------------");

        JsonPath js = given().relaxedHTTPSValidation().log().all()
                .accept(ContentType.JSON).
                pathParam("spartanID", spartanId).
                when().get("/api/spartans/{spartanID}").
                then().statusCode(200).extract().jsonPath();


        assertEquals("API Flow POST", js.getString("name"));

        System.out.println("-------------------PUT-------------------------------");

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("name", "API PUT Flow");
        request.put("gender", "Female");
        request.put("phone", "32132132113");




        given().log().body()
                .contentType(ContentType.JSON)
                .pathParam("id", spartanId)
                .body(request).
                when().put("/api/spartans/{id}").
                then().statusCode(204);

        System.out.println("----------------------GET---------------------------");

        JsonPath js2 = given().log().all()
                .accept(ContentType.JSON).
                pathParam("id", spartanId).
                when().get("/api/spartans/{id}").
                then().statusCode(200).extract().jsonPath();


        assertEquals("API PUT Flow", js2.getString("name"));

        System.out.println("------------------------DELETE----------------------");


        given().pathParam("id",spartanId)
                .when().delete("/api/spartans/{id}").
                then().statusCode(204);


        System.out.println("----------------GET---------------------------");


        given().accept(ContentType.JSON).log().all()
                .pathParam("id",spartanId).
                when().get("/api/spartans/{id}").
                then().statusCode(404);


    }


}
