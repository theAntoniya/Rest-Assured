package com.cydeo.day08;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class P01_SpartanAuthTest extends SpartanAuthTestBase {

    @DisplayName("GET /api/spartans as GUEST user --> EXPECT --> 401 ")
    @Test
    public void test1() {
        // it is Negative Test Case
        given().accept(ContentType.JSON).
                when().get("/api/spartans").
                then().log().ifValidationFails().statusCode(401)
                .body("error",is("Unauthorized"));

    }


    @DisplayName("GET /api/spartans as USER  --> EXPECT --> 200 ")
    @Test
    public void test2() {

        given().accept(ContentType.JSON).log().all()
                .auth().basic("user","user"). // This is the to send basic auth credentials through REST ASSURED
                when().get("/api/spartans").prettyPeek().
                then().log().ifValidationFails().statusCode(200);

    }

    @DisplayName("DELETE /api/spartans/{id} as EDITOR  --> EXPECT --> 403 --> FORBIDDEN ")
    @Test
    public void test3() {

        given().pathParam("id",56)
                .accept(ContentType.JSON)
                // .header("Accept","application/json") --> They created ready methods for us as Accept
                .auth().basic("editor","editor").
                when().delete("/api/spartans/{id}").then().statusCode(403)
                .body("error",is("Forbidden"));

    }
    // Try to delete with Admin

    @DisplayName("DELETE /api/spartans/{id} as ADMIN  --> EXPECT --> 204 --> SUCCESS ")
    @Test
    public void test4() {

        given().pathParam("id",6)
                .auth().basic("admin","admin").
                when().delete("/api/spartans/{id}").then().statusCode(204);

    }

    /**
     *  HOMEWORKS
     *
     *  	Role Based Control Test --> RBAC
     *
     * 			ADMIN  -->  GET  POST PUT PATCH  DELETE   --> Spartan Flow
     * 			EDITOR -->  GET  POST PUT PATCH   403
     * 			USER   -->  GET  403  403  403    403
     * 			GUEST  -->  401  401  401  401    401
     *
     *
     *   -- Create RBAC Test for all different roles from Spartan Application including with Negative Test cases
     *   -- Create SpartanUtil Class
     *             public static Map<String,Object>  SpartanUtil.getRandomSpartan(){
     *                  Map<String,Object> spartanMap=new HashMap();
     *                  spartanMap.put("name",faker.funnyName());
     *
     *                  use Faker class to create each time differetn spartan information
     *
     *                  return spartanMap;
     *              }
     *
     *               public static void  GETSpartans(String role,String password,int statusCode,int id){
     *
     *                 given().pathParam("id",id)
     *                 .auth().basic(role,password).
     *                 when().delete("/api/spartans/{id}").then().statusCode(statusCode);
     *
     *               }
     *
     *     Q --> can we create class in utilities class with loop for each user, admin, guest with passport make more dynamic
     *            - YES we can but what if first user test is failing.
     *
     *            - We should do it --> Data Driven Test
     *
     *            - JUnit5 DDT to implement
     *
     */

}