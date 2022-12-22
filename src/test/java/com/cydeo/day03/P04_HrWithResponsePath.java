package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P04_HrWithResponsePath extends HrTestBase {

    @DisplayName("GET request to countries wiht using Response Path")
    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":2}").
                when().get("/countries");

        //response.prettyPrint();

        //print limit
        System.out.println("response.path(\"limit\") = " + response.path("limit"));
        //print hasMore
        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));
        //print second country_id
        System.out.println("response.path(\"items[1].country_id\") = " + response.path("items[1].country_id"));

        //print 4th element country name
        System.out.println("response.path(\"items[3].country_name\") = " + response.path("items[3].country_name"));

        //print 4th element href
        System.out.println("response.path(\"items[3].links[0].href\") = " + response.path("items[3].links[0].href"));



        //get all countries name
        List<String> allCountryNames = response.path("items.country_name");
        System.out.println("allCountryNames = " + allCountryNames);

        // verify all region_id is 2
        List<Integer> allRegionIDs = response.path("items.region_id");
        for (Integer eachRegionID : allRegionIDs) {
            System.out.println("eachRegionID = " + eachRegionID);
            assertEquals(2,eachRegionID);
        }

        //stream
        assertTrue(allRegionIDs.stream().allMatch(each -> each == 2));


    }

    /*
        Send a GET request to /employees endpoint to see only job_id = IT_PROG
        Query Param
            q = {"job_id":"IT_PROG"}
        Then assert all job_ids are IT_PROG
     */

}
