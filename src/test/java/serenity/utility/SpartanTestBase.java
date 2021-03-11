package serenity.utility;

import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


public class SpartanTestBase {


    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://52.87.221.62:8000";
        RestAssured.basePath = "/api";
        // sett static for rest assured
        //adminSpec = given()
        //    .auth().basic("admin","admin");
    }

    @AfterAll
    public static void cleanUp(){
        SerenityRest.clear();
        RestAssured.reset();
    }



}
