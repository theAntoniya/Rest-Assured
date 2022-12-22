package com.cydeo.day06;

import com.cydeo.pojo.Search;
import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class P02_SpartanDeserilizationPOJO extends SpartanTestBase {


    @DisplayName("GET Single Spartan for deserialization to POJO ")
    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 15).
                when().get("/api/spartans/{id}").prettyPeek().
                then()
                .statusCode(200).extract().response();

        /*
            {
                "id": 15,
                "name": "Meta",
                "gender": "Female",
                "phone": 1938695106
            }
         */
        // RESPONSE
        System.out.println(" ----- RESPONSE -----");

        Spartan spartan = response.as(Spartan.class);
        System.out.println("spartan = " + spartan);
        System.out.println(spartan.getId());
        System.out.println(spartan.getName());
        System.out.println(spartan.getGender());
        System.out.println(spartan.getPhone());

        // JSONPATH
        System.out.println(" ----- JSON -----");
        JsonPath jsonPath = response.jsonPath();
        Spartan sp = jsonPath.getObject("", Spartan.class);
        System.out.println("sp = " + sp);
        System.out.println(sp.getId());
        System.out.println(sp.getName());
        System.out.println(sp.getGender());
        System.out.println(sp.getPhone());


    }




    @DisplayName("GET Spartans from search endpoint for deserialization to POJO ")
    @Test
    public void test2() {

        Response response = given().accept(ContentType.JSON).
                when().get("/api/spartans/search").prettyPeek().
                then()
                .statusCode(200).extract().response();


        // RESPONSE
        System.out.println(" ----- RESPONSE - GET FIRST SPARTAN  -----");
        // response.as() --> Since we can not put path in here to get specific part of Response
        // we are no gonna do it

        // JSONPATH
        System.out.println(" ----- JSON - GET FIRST SPARTAN-----");
        JsonPath jsonPath = response.jsonPath();

        Spartan spartan = jsonPath.getObject("content[0]", Spartan.class);
        System.out.println("spartan = " + spartan);


    }

    @DisplayName("GET Spartans from search endpoint for deserialization to Search Class ")
    @Test
    public void test3() {

        Response response = given().accept(ContentType.JSON).
                when().get("/api/spartans/search").
                then()
                .statusCode(200).extract().response();

        System.out.println(" ----- RESPONSE -----");
        Search search1 = response.as(Search.class);

        // since we are not providing path for response still we can use response.as() to make deserialization

        System.out.println(" ----- JSON -----");
        JsonPath jp = response.jsonPath();

        Search search = jp.getObject("", Search.class);

        System.out.println(search.getTotalElement());
        System.out.println("search.getContent().get(0) = " + search.getContent().get(0));
        System.out.println("search.getContent().get(0).getName() = " + search.getContent().get(0).getName());
    }

    @DisplayName("GET Spartans from search endpoint for deserialization to List Of Spartan Class ")
    @Test
    public void test4() {

        Response response = given().accept(ContentType.JSON).
                when().get("/api/spartans/search").
                then()
                .statusCode(200).extract().response();

        JsonPath jsonPath = response.jsonPath();

        List<Spartan> allSpartans= jsonPath.getList("content",Spartan.class);
        for (Spartan eachSpartan : allSpartans) {
            System.out.println("eachSpartan = " + eachSpartan);
        }

        System.out.println("allSpartans.get(0) = " + allSpartans.get(0));
        System.out.println("allSpartans.get(0).getId() = " + allSpartans.get(0).getId());
    }
}
