package com.cydeo.day13;

import com.cydeo.utilities.SpartanNewTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P01_OldRestAssured extends SpartanNewTestBase {

    @Test
    public void getAllSpartan() {


        given().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .when().get("/spartans").
                then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("id[0]", is(1))
                .body("id[1]", is(102));
    }


    @Test
    public void getAllSpartanOldWay() {

        given().accept(ContentType.JSON)
                .auth().basic("admin", "admin").
                expect().statusCode(200)
                .contentType(ContentType.JSON)
                .body("id[0]", is(1))
                .body("id[1]", is(2)).
                when().get("/spartans");
    }
     /*
    OLD WAY --> EXPECT()
        - it works like soft assertion
    NEW WAY --> then()  (This is the that we are gonna use now also in the future if they will not release new version )
        - it works like hard assertion
     */

}
