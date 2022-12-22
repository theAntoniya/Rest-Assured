package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class ZippopotamUS {

    @BeforeAll
    public static void init() {

        RestAssured.baseURI = "https://api.zippopotam.us";
    }
}
