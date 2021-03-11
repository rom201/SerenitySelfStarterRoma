package serenity.spartan;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.*;
import serenity.utility.SpartanUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;


@SerenityTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class AdminCrudTest {

   // private static RequestSpecification adminSpec;

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



    @DisplayName("1.Admin user should be able to Add Spartan")
    @Test
    public void testAdd1Data(){

        Map<String, Object> payload = SpartanUtil.getRandomSpartanRequestPayload();

        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(payload).
          when()
                .post("/spartans") ;


        Ensure.that("Request was successful"  ,
                thenResponse -> thenResponse.statusCode(201) )
                .andThat("We get json formta",
                        thenReponse -> thenReponse.contentType(ContentType.JSON))
                .andThat("Succses message Spartan is born",
                        thenResponse -> thenResponse.body ("success", is("A Spartan is Born!")))
        ;


        Ensure.that("The data <"+payload+"> we provided added correctly",
                vRes -> vRes.body("data.name", is( payload.get("name")  ) )
                        .body("data.gender", is( payload.get("gender")  ) )
                        .body("data.phone", is( payload.get("phone")  ) ) )
                .andThat("New ID has been generated and not null" ,
                        vRes -> vRes.body("data.id" , is(not(nullValue() )))    ) ;


// how do we extract information after sending requests ? :
        // for example I want to print out ID
        // lastResponse() method is coming SerenityRest class
        // and return the Response Object obtained from last ran request.
//        lastResponse().prettyPeek();
        System.out.println("lastResponse().jsonPath().getInt(\"data.id\") = "
                + lastResponse().jsonPath().getInt("data.id"));


    }

    @DisplayName("2.Admin Should be able to read single data")
    @Test
    public void getOneData(){

        int newID = lastResponse().jsonPath().getInt("data.id");
        //System.out.println("newID = " + newID);

        given()
                .auth().basic("admin","admin").
         when()
                .get("/spartans/{id}", newID) ;
        Ensure.that("We can access newly generated data",
                thenResponse-> thenResponse.statusCode(200) ) ;

    }


    @DisplayName("3.Admin Should be able to delete single data")
    @Test
    public void testDeleteOneData(){
        // capture the id from last get request
        int myId = lastResponse().jsonPath().getInt("id") ;
        given()
                .auth().basic("admin","admin")
                .pathParam("id", myId).
        when()
                .delete("/spartans/{id}" ) ;


        Ensure.that("Request is successful",
                thenResponse -> thenResponse.statusCode(204) ) ;
        // send another get request to make sure upi you get 404
        given()
                .auth().basic("admin","admin")
                .pathParam("id", myId).
        when()
                .get("/spartans/{id}" ) ;
        Ensure.that("Delete was successful, Can not find data anymore",
                thenResponse -> thenResponse.statusCode(404) ) ;
    }










}
