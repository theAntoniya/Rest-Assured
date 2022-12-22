package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class SpartanTestBase {

    @BeforeAll
    public static void init(){

        RestAssured.baseURI="http://3.95.207.159:8000";
//        RestAssured.baseURI="http://3.80.111.193:8000";
//
    }
}
