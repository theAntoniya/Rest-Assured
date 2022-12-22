package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class homework extends HrTestBase {

    /*
    Given accept type is Json +
    Path param value -US +
    When users sends request to /countries +
    Then status code is 200 +
    And Content -Type is Json +

    And country_id is US
    And Country_name is United States of America
    And Region_id is 2
     */

    @Test
    public void task01(){

//- Given accept type is Json
//- Path param value- US
//- When users sends request to /countries
        Response response = given().accept(ContentType.JSON)
                .pathParam("country_id", "US")
                .get("/countries/{country_id}");
//- Then status code is 200
        assertEquals(200,response.statusCode());
//- And Content - Type is Json
        assertEquals(ContentType.JSON.toString(),response.contentType());
//- And country_id is US
        JsonPath jsonPath = response.jsonPath();
        assertEquals("US", jsonPath.getString("country_id"));
//- And Country_name is United States of America
        assertEquals("United States of America",jsonPath.getString("country_name"));
//- And Region_id is 2
        assertEquals(2, jsonPath.getInt("region_id"));
    }

    @DisplayName("GET request from countries  using  Path param")
    @Test
    public void task1() {
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("country_id", "US")
                .and()
                .get("/countries/{country_id}");
        //response.prettyPrint();

        assertEquals(200, response.statusCode(), "Response code verification - Fail");
        assertEquals("application/json", response.contentType(), "Content Type verification - Fail");
        JsonPath jsPath = response.jsonPath();
        assertEquals("US", jsPath.getString("country_id"));
        assertEquals("United States of America", jsPath.getString("country_name"));
        assertEquals(2, jsPath.getInt("region_id"));

    }


    /*
    TASK 2 :
- Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all department_ids are 80
- And all job_ids start with 'SA'
- Count is 25
     */
    @Test
    public void task2() {

//- Given accept type is Json
//- Query param value - q={"department_id":80}
//- When users sends request to /employees
        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"department_id\":80}")
                .when().get("/employees");
//- Then status code is 200
        assertEquals(200, response.statusCode());
//- And Content - Type is Json
        assertEquals(ContentType.JSON.toString(), response.contentType());
//- And all job_ids start with 'SA'
        JsonPath jsonPath = response.jsonPath();
        List<String> listJobId = jsonPath.getList("items.job_id");
        assertTrue(listJobId.stream().allMatch(p -> p.startsWith("SA")));
//- And all department_ids are 80
        List<Integer> listDepId = jsonPath.getList("items.department_id");
        assertTrue(listDepId.stream().allMatch(p -> p == 80));
//- Count is 25
        assertEquals(25, jsonPath.getInt("count"));
    }



    @Test
    public void task3() {

//- Given accept type is Json
//- Query param value q= region_id 3
//- When users sends request to /countries
        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":3}")
                .when().get("/countries");
//- Then status code is 200
        assertEquals(200,response.statusCode());
//- And all regions_id is 3
        JsonPath jsonPath = response.jsonPath();
        List<Integer> listRegionId = jsonPath.getList("items.region_id");
        assertTrue(listRegionId.stream().allMatch(p -> p == 3));
//- And count is 6
        assertEquals(6,jsonPath.getInt("count"));
//- And hasMore is false
        assertFalse(jsonPath.getBoolean("hasMore"));
//- And Country_name are: Australia,China,India,Japan,Malaysia,Singapore
        assertEquals(Arrays.asList("Australia","China","India","Japan","Malaysia","Singapore"), jsonPath.getList("items.country_name"));
    }

}
