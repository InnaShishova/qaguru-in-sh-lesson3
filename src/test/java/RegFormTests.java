import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selectors.byText;

public class RegFormTests {

    @BeforeAll
    static void prepareEnvironment() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 3000;

    }

    @Test
    void fillFormTest() {
        //Открытие страницы 'Practice Form'//
        open("/automation-practice-form");

        //Name//
        $("#firstName").setValue("Ivan");
        $("#lastName").setValue("Petrov");

        //Email//
        $("#userEmail").setValue("test1@test2.com");

        //Gender//
                $("#genterWrapper").$(byText("Female")).click();

        //Mobile//
                $("#userNumber").setValue("1234567890");

        //Date of Birth//
        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").selectOption("2025");
        $(".react-datepicker__month-select").selectOption("October");
        $(".react-datepicker__month").$(byText("15")).click();

        //Subjects//
        $("#subjectsInput").setValue("English").pressEnter();

        //Hobbies//
        $("#hobbiesWrapper").$(byText("Reading")).click();

        //Picture//
        $("#uploadPicture").uploadFromClasspath("filepicture.jpg");

        //Current Address//
        $("#currentAddress").scrollTo().shouldBe(visible);
        $("#currentAddress").setValue("город Москва, улица Ленина");


        //State and City//
        $("#state").click();
        $(byText("Haryana")).click();
        $("#city").click();
        $(byText("Panipat")).click();

        //Submit
        $("#submit").click();

        //Проверка таблицы
        $("[class=modal-header]").shouldHave(text("Thanks for submitting the form"));

        $(".table").shouldHave(text("Ivan"));
        $(".table").shouldHave(text("Petrov"));
        $(".table").shouldHave(text("test1@test2.com"));
        $(".table").shouldHave(text("Female"));
        $(".table").shouldHave(text("1234567890"));
        $(".table").shouldHave(text("15 October,2025"));
        $(".table").shouldHave(text("English"));
        $(".table").shouldHave(text("Reading"));
        $(".table").shouldHave(text("город Москва, улица Ленина"));
        $(".table").shouldHave(text("Haryana Panipat"));


        //Закрытие окна
        $("#closeLargeModal").click();


    }
}


