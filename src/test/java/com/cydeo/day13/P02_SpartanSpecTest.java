package com.cydeo.day13;

import com.cydeo.utilities.SpartanNewTestBase;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class P02_SpartanSpecTest extends SpartanNewTestBase {

    @Test
    public void getAllSpartans() {

        given().log().all().accept(ContentType.JSON)
                .auth().basic("admin","admin").
                when().get("/spartans").
                then().
                statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void getAllSpartansWithReqResSpec() {

        RequestSpecification reqSpec = given().log().all().accept(ContentType.JSON)
                .auth().basic("admin", "admin");


        ResponseSpecification resSpec =
                expect().statusCode(200)
                        .contentType(ContentType.JSON);


        given().spec(reqSpec).
                when().get("/spartans").
                then().spec(resSpec);
    }
}