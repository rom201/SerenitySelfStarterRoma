package serenity.spartan;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.*;
import serenity.utility.SpartanUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;




@SerenityTest
public class SimpleSpartanTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://52.87.221.62:8000";
        RestAssured.basePath = "/api";
    }

    @AfterAll
    public static void cleanUp(){
        reset();
    }

    @DisplayName("Testing GET /api/hello Endpoint")
    @Test
    public void testingHelloEndPoint(){

        when()
                .get("/hello");
        //then()
        //        .statusCode(200)
         //       .contentType(ContentType.TEXT)
         //       .body( is("Hello from Sparta") )
        //;

        Ensure.that("Make sure endpoint work",
                response ->response
                        .statusCode(200)
                        .contentType(ContentType.TEXT)
                        .body( is("Hello from Sparta")));


                Ensure.that("Success response was received",
                        thenResponse -> thenResponse.statusCode(200) )
                .andThat("I got text response" ,
                         blaResponse -> blaResponse.contentType(ContentType.TEXT) )
                .andThat("I got Hello from Sparta" ,
                         vResponse -> vResponse.body( is("Hello from Sparta") ) )
                 .andThat("I got my response within 2 seconds",
                                vResponse -> vResponse.time( lessThan(2L), TimeUnit.SECONDS  ) )

                ;

    }


}
