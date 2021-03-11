package serenity.zipcode_app;


import io.restassured.RestAssured;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.is;


@SerenityTest
public class ZipToCityEndpointTest {

    //https://api.zippopotam.us/{country}/{zipcode}

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://api.zippopotam.us" ;
    }
    @AfterAll
    public static void cleanUp(){
        SerenityRest.clear();

    }


    @DisplayName("Testing 1 zip code and get the result")
    @Test
    public void test1ZipCode(){

        given()
                .pathParam("country","us")
                .pathParam("zipcode","22030").
       when()
                .get("/{country}/{zipcode}").
       then()
                .statusCode(200)
                .body("'post code'",is("22030") )
                .body("places[0].'place name'", is("Fairfax")
                ) ;
    }


    @DisplayName("Testing multiple zipcodes and get the result")
    @ParameterizedTest
    @ValueSource(strings = {"22030", "22031", "22032","22034" ,"22035"} )
    public void testZipCodes( String zip  ){
        //run this parameterized test with 5 zipcodes of your choice
        // start with no external file
        // then add external csv file in separate test
        System.out.println("zip = " + zip);

        given()
                .pathParam("country","us")
                .pathParam("zipcode",zip).
        when()
                .get("/{country}/{zipcode}");

        Ensure.that("we got successful result", v->v.statusCode(200));

    }

    /**
     * {index}  --->> to represent iteration number
     * {arguments} -->>
     * {methodParameterIndexNumber}  -->>
     *just for displaying iteration number
     * this is Junit 5
     */


    @ParameterizedTest(name = "Iteration number {index}:{arguments}")
    @ValueSource(strings = {"22030", "22031", "22032","22034" ,"22035"} )
    public void testDisplayNameManipulation( String zip  ){


    }


    @ParameterizedTest(name = "Iteration number {index} Country is{0}, Zipcode is{1}")
    @CsvFileSource(resources = "/country_zip.csv", numLinesToSkip = 1)
    public void testCountryZip(String country, int zip){
        given()
                .pathParam("country",country)
                .pathParam("zipcode",zip).
                when()
                .get("/{country}/{zipcode}") ;
        Ensure.that("we got successful result ", v -> v.statusCode(200) ) ;

    }






}



