package com.cydeo.day11;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P01_SpartanXMLTest extends SpartanAuthTestBase {


    /**
     * Given accept type is application/xml
     * When send the request /api/spartans
     * Then status code is 200
     * And content type is application/xml
     *   print firstname
     *   .....
     *   ...
     */


    @Test
    public void test1() {

        given().accept(ContentType.XML)
                .auth().basic("admin","admin").
                when().get("/api/spartans").prettyPeek().
                then().statusCode(200)
                .contentType(ContentType.XML)
                .body("List.item[0].name",is("John Dan"))
                .body("List.item[0].gender",is("Male"));


    }




    @DisplayName("GET /api/spartans with using XMLPath")
    @Test
    public void test2() {

        Response response = given().accept(ContentType.XML)
                .auth().basic("admin", "admin").
                when().get("/api/spartans");


        // GET response as XML format and save into XMLPath
        XmlPath xmlPath = response.xmlPath();


        // get first spartan name
        System.out.println("xmlPath.getString(\"List.item[0].name\") = " + xmlPath.getString("List.item[0].name"));

        // get me 2nd spartan name

        System.out.println("xmlPath.getString(\"List.item[1].name\") = " + xmlPath.getString("List.item[1].name"));

        // get me last spartan name
        System.out.println("xmlPath.getString(\"List.item[-1].name\") = " + xmlPath.getString("List.item[-1].name"));

        // Get all the spartan names
        List<String> nameList = xmlPath.getList("List.item.name");
        System.out.println("nameList = " + nameList);

        // how many spartans we have
        List<Spartan> allSpartans = xmlPath.getList("List.item");
        // Deserilization still possbile to do it.We should use another dependencies or use some Java logic to store into POJO
        // We are not gonna touch this

        System.out.println("allSpartans.size() = " + allSpartans.size());


        /**
         * Do we know how many spartan we have ?
         *   - Can we create Loop ?
         *       - yes
         *      String spartanName=xmlPath.getString("List.item[i].name")
         *
         */



    }
    }
