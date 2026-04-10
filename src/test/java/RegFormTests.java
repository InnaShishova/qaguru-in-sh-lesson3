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
        Configuration.timeout = 10000;

    }

    @Test
    void fillFormTest() {

        String firstName = "Ivan";
        String lastName = "Petrov";
        String Email = "test1@test2.com";
        String gender = "Female";
        String Number = "1234567890";
        String birthDay = "15";
        String birthMonth = "October";
        String birthYear = "2025";
        String expectedBirthDate = "15 October,2025";
        String subject = "English";
        String hobby = "Reading";
        String picture = "filepicture.jpg";
        String address = "город Москва, улица Ленина";
        String state = "Haryana";
        String city = "Panipat";


        //Открытие страницы 'Practice Form'//
        open("/automation-practice-form");

        executeJavaScript("""
    document.getElementById('fixedban')?.remove();
    document.querySelector('footer')?.remove();
""");

        //Name//
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);

        //Email//
        $("#userEmail").setValue(Email);

        //Gender//
        $("#genterWrapper").$(byText(gender)).click();

        //Mobile//
        $("#userNumber").setValue(Number);

        //Date of Birth//
        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").selectOption(birthYear);
        $(".react-datepicker__month-select").selectOption(birthMonth);
        $(".react-datepicker__month").$(byText(birthDay)).click();

        //Subjects//
        $("#subjectsInput").setValue(subject).pressEnter();

        //Hobbies//
        $("#hobbiesWrapper").$(byText(hobby)).click();

        //Picture//
        $("#uploadPicture").uploadFromClasspath(picture);

        //Current Address//
        $("#currentAddress").scrollTo().shouldBe(visible);
        $("#currentAddress").setValue(address);


        //State and City//
        $("#state").click();
        $("#stateCity-wrapper").$(byText(state)).click();
        $("#city").click();
        $("#stateCity-wrapper").$(byText(city)).click();

        //Submit
        $("#submit").click();

        //Проверка таблицы
        $("[class=modal-header]").shouldHave(text("Thanks for submitting the form"));

        $(".table").shouldHave(text(firstName));
        $(".table").shouldHave(text(lastName));
        $(".table").shouldHave(text(Email));
        $(".table").shouldHave(text(gender));
        $(".table").shouldHave(text(Number));
        $(".table").shouldHave(text(expectedBirthDate));
        $(".table").shouldHave(text(subject));
        $(".table").shouldHave(text(hobby));
        $(".table").shouldHave(text(picture));
        $(".table").shouldHave(text(address));
        $(".table").shouldHave(text(state + " " + city));

        //Закрытие окна
        $("#closeLargeModal").click();


    }
}



