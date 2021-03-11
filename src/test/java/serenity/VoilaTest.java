package serenity;

import net.serenitybdd.junit5.SerenityTest;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import serenity.steps.B20Actions;


@SerenityTest
@Tag("voila")
public class VoilaTest {


    @Steps
    B20Actions tucky;

    @Test
    public void testVoila(){

        //GIVEN
        tucky.preparedSomething();
        //WHEN
        tucky.takeAnAction();
        //THEN
        tucky.expectSomeResult();





    }

}
