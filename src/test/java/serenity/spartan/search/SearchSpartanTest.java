package serenity.spartan.search;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import serenity.utility.SpartanTestBase;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;


@SerenityTest
public class SearchSpartanTest extends SpartanTestBase {

    @DisplayName("Authenticated user should be able to search")
    @Test
    public void testSearch(){
        given()
                .auth().basic("admin","admin")
                .queryParam("nameContains","a")
                .queryParam("gender","Male").

        when()
                .get("/spartans/search") ;
        Ensure.that("Request was successful",
                vResponse-> vResponse.statusCode(200) )
        .andThat("We got Json Result",
                vResponse-> vResponse.contentType(ContentType.JSON));


        // chain above ensure you got json result
        // open another ensure
        // make sure you got all names contains a
        Ensure.that("Make sure every item actually contains a ",
                vResponse-> vResponse
                      .body("content.name",
             everyItem( anyOf(containsString("a") , containsString("A")  ) ) )
        ).andThat("every item gender value is Male" , vResponse -> vResponse.body("content.gender",
                       everyItem(  is("Male") ) ) ) ;

    }

}
