//lesson3//
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegFormTests.java {

    @BeforeAll
    static void prepareEnvironment() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 6000;
    }

    @Test
     void FillFormTest() {
        //Открытие страницы 'Practice Form'//
        open("/automation-practice-form");

        //Name//
       $("#firstName").setValue("Ivan");
       $("#lastName").setValue("Petrov");

        //Email//
       $("#userEmail").setValue("test1@test2.test3");

        //Gender//
       $("#genterWrapper").$("Female").click();

        //Mobile//
       $("#userNumber").setValue("1234567890");

        //Date of Birth//
       $("#dateOfBirthInput").click();
       $(".react-datepicker__year-select").selectOption("1990");
       $(".react-datepicker__month-select").selectOption("June");
       $(".react-datepicker__month").$(("26")).click();

        //Subjects//
       $("#subjectsInput").setValue("history");

        //Hobbies//
       $("#hobbiesWrapper").$("Reading").click();

       //Picture//
       $("#uploadPicture").uploadFromClasspath("filepicture.ipg");

       //Current Address//
       $("#сurrentAddress").setValue("город Москва, улица Ленина");

       //State and City//
       $("#state").click();
       $("#react-select-3-input").setValue("Haryana").pressEnter();
       $("#city");
       $("#react-select-4-input").setValue("Karnal").pressEnter();
    }
}
