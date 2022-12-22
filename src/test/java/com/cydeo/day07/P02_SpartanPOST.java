package com.cydeo.day07;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class P02_SpartanPOST extends SpartanTestBase {

    /**
     Given accept type is JSON
     And Content type is JSON
     And request json body is:
     {
     "gender":"Male",
     "name":"John Doe",
     "phone":8877445596
     }
     When user sends POST request to '/api/spartans'
     Then status code 201
     And content type should be application/json
     And json payload/response/body should contain:
     verify the success value is A Spartan is Born!
     "name": "John Doe",
     "gender": "Male",
     "phone": 1231231231
     */
    @DisplayName("POST spartan with String body")
    @Test
    public void test1() {

        String requestBody="{\n" +
                "     \"gender\":\"Male\",\n" +
                "     \"name\":\"John Doe\",\n" +
                "     \"phone\":8877445596\n" +
                "     }";

        String expectedMessage="A Spartan is Born!";

        JsonPath jsonPath = given().accept(ContentType.JSON).log().body()// API send me response in JSON format
                .contentType(ContentType.JSON) // API I am sending body in JSON format
                .body(requestBody).  // This is the that we wnna POST in API
                        when().post("/api/spartans").prettyPeek().
                then().statusCode(201)
                .contentType("application/json").extract().jsonPath();


        assertEquals(expectedMessage,jsonPath.getString("success"));
        assertEquals("John Doe",jsonPath.getString("data.name"));
        assertEquals("Male",jsonPath.getString("data.gender"));
        assertEquals(8877445596l,jsonPath.getLong("data.phone"));

        // What if I want to get id
        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);

        // if you wanna do assertion with GET Request we can use it as Path param to specify same Spartans


        /*
        {
            "success": "A Spartan is Born!",
            "data": {
                 "id": 174,
                 "name": "John Doe",
                 "gender": "Male",
                 "phone": 8877445596l
            }
        }
         */
        //what happens if we run again? --> it will create new spartan with different ID



    }

    @DisplayName("POST spartan with Map body")
    @Test
    public void test2() {

        Map<String,Object> requestBody=new LinkedHashMap<>();
        requestBody.put("name","James Bond");
        requestBody.put("gender","Male");
        requestBody.put("phone",1234567890l);

        // Map<String,Object> spartanMap= SpartanUtil.getSpartanAsMap();
        // Can we add more info ---> Do we have right to add based on doc --> No . we cant

        String expectedMessage="A Spartan is Born!";


        // body(requestBody) --> is doind serilization behind the scene to send data in JSON format
        // to do serilization we need to one the ObjectMapper ( Jackson / Gson )

        JsonPath jsonPath = given().accept(ContentType.JSON).log().body()// API send me response in JSON format
                .contentType(ContentType.JSON) // API I am sending body in JSON format
                .body(requestBody).
                when().post("/api/spartans").prettyPeek().
                then().statusCode(201)
                .contentType("application/json").extract().jsonPath();


        assertEquals(expectedMessage,jsonPath.getString("success"));
        assertEquals(requestBody.get("name"),jsonPath.getString("data.name"));
        assertEquals(requestBody.get("gender"),jsonPath.getString("data.gender"));
        assertEquals(requestBody.get("phone"),jsonPath.getLong("data.phone"));

        // What if I want to get id
        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);


        // Can we create SpartanUtil to create dynamic Spartan as Map to use in request


    }


    @DisplayName("POST spartan with Spartan POJO body")
    @Test
    public void test3() {

        Spartan requestBody=new Spartan();
        requestBody.setName("John Wick");
        requestBody.setGender("Male");
        requestBody.setPhone(1234567890l);

        System.out.println("requestBody = " + requestBody);


        String expectedMessage="A Spartan is Born!";


        // body(requestBody) --> is doing serilization behind the scene to send data in JSON format
        // to do serilization we need to one the ObjectMapper ( Jackson / Gson )

        JsonPath jsonPath = given().accept(ContentType.JSON).log().body()// API send me response in JSON format
                .contentType(ContentType.JSON) // API I am sending body in JSON format
                .body(requestBody).
                when().post("/api/spartans").prettyPeek().
                then().statusCode(201)
                .contentType("application/json").extract().jsonPath();


        // What if I want to get id
        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);



        assertEquals(expectedMessage,jsonPath.getString("success"));
        assertEquals(requestBody.getName(),jsonPath.getString("data.name"));
        assertEquals(requestBody.getGender(),jsonPath.getString("data.gender"));
        assertEquals(requestBody.getPhone(),jsonPath.getLong("data.phone"));


        // Can we create SpartanUtil to create dynamic Spartan as Map to use in request


    }


    @DisplayName("POST spartan with Spartan POJO body and GET same spartan")
    @Test
    public void test4() {

        Spartan requestBody=new Spartan();
        requestBody.setName("John Wick");
        requestBody.setGender("Male");
        requestBody.setPhone(1234567890l);

        System.out.println("requestBody = " + requestBody);

        // POST SPARTAN
        JsonPath jsonPath = given().accept(ContentType.JSON).log().body()
                .contentType(ContentType.JSON)
                .body(requestBody).
                when().post("/api/spartans").prettyPeek().
                then().statusCode(201)
                .contentType("application/json").extract().jsonPath();


        // What if I want to get id
        int idFromPOST = jsonPath.getInt("data.id");
        System.out.println("id = " + idFromPOST);

        // GET SAME SPARTAN WITH SAME ID THAT WE GET FROM POST RESPONSE
        Spartan spartanGET = given().accept(ContentType.JSON)
                .pathParam("id", idFromPOST).
                when().get("/api/spartans/{id}").
                then().statusCode(200).extract().jsonPath().getObject("", Spartan.class);

        System.out.println("spartanGET = " + spartanGET);

        // verify names are matcching
        assertEquals(requestBody.getName(),spartanGET.getName());




    }
}
