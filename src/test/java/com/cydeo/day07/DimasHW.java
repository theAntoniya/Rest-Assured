package com.cydeo.day07;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class DimasHW extends SpartanTestBase {

    static int id;

    @Order(value = 1)
    @DisplayName("POST spartan")
    @Test
    public void test1() {
        Map<String, Object> spartan = new LinkedHashMap<>();
        spartan.put("name", "API Flow POST D");
        spartan.put("gender", "Male");
        spartan.put("phone", 123456789111l);
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .log().body().contentType(ContentType.JSON)
                .body(spartan)
                .when().post("/api/spartans").prettyPeek()
                .then().statusCode(201)
                .body("success", is("A Spartan is Born!")).extract().jsonPath();


        id = jsonPath.getInt("data.id");
        System.out.println("+++++++++++++++" + id);
    }
    @Order(value = 2)
    @Test
    @DisplayName("Get spartan")
    public void test2() {
        System.out.println(id);
        given().contentType(ContentType.JSON)
                .and().pathParam("id", id)
                .when().get("/api/spartans/{id}").prettyPeek()
                .then().statusCode(200)
                .and().assertThat().body("name", is("API Flow POST D"));
    }
    @Order(value = 3)
    @DisplayName("PUT")
    @Test
    public void test3() {
        Map<String, Object> putSp = new LinkedHashMap<>();
        putSp.put("name", "PutD");
        putSp.put("gender", "Female");
        putSp.put("phone", 1234567896L);


        given().log().body().contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(putSp)
                .when().put("/api/spartans/{id}").prettyPeek()
                .then().statusCode(204);
    }
    @Order(value = 4)
    @DisplayName("Delete")
    @Test
    public void test4() {
        given().pathParam("id", id)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(204);


    }
}
