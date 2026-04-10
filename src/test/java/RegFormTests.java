import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
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
        String email = "test1@test2.com";
        String gender = "Female";
        String number = "1234567890";
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

        open("/automation-practice-form");

        executeJavaScript("""
            document.getElementById('fixedban')?.remove();
            document.querySelector('footer')?.remove();
        """);

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(number);

        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").selectOption(birthYear);
        $(".react-datepicker__month-select").selectOption(birthMonth);
        $(".react-datepicker__month").$(byText(birthDay)).click();

        $("#subjectsInput").setValue(subject).pressEnter();
        $("#hobbiesWrapper").$(byText(hobby)).click();
        $("#uploadPicture").uploadFromClasspath(picture);

        $("#currentAddress").scrollTo().shouldBe(visible);
        $("#currentAddress").setValue(address);

        $("#state").click();
        $(byText(state)).click();

        $("#city").click();
        $(byText(city)).click();

        $("#submit").click();

        //Таблица проверки
        $("[class=modal-header]").shouldHave(text("Thanks for submitting the form"));
        $(".table").shouldHave(text(firstName));
        $(".table").shouldHave(text(lastName));
        $(".table").shouldHave(text(email));
        $(".table").shouldHave(text(gender));
        $(".table").shouldHave(text(number));
        $(".table").shouldHave(text(expectedBirthDate));
        $(".table").shouldHave(text(subject));
        $(".table").shouldHave(text(hobby));
        $(".table").shouldHave(text(picture));
        $(".table").shouldHave(text(address));
        $(".table").shouldHave(text(state + " " + city));

        $("#closeLargeModal").click();
    }
}

//автотест на Успешное заполнение формы только с обязательными полями
@Test
void successfulRegistrationWithRequiredFieldsOnlyTest() {
    String firstName = "Inna";
    String lastName = "Shishova";
    String gender = "Female";
    String number = "9991234567";

    open("/automation-practice-form");
    executeJavaScript("""
        document.getElementById('fixedban')?.remove();
        document.querySelector('footer')?.remove();
    """);

    $("#firstName").setValue(firstName);
    $("#lastName").setValue(lastName);
    $("#genterWrapper").$(byText(gender)).click();
    $("#userNumber").setValue(number);

    $("#submit").click();

    $(".modal-content").shouldHave(text("Thanks for submitting the form"));
    $(".table").shouldHave(text(firstName));
    $(".table").shouldHave(text(lastName));
    $(".table").shouldHave(text(gender));
    $(".table").shouldHave(text(number));
}

//Негативные сценарии
// Отправка формы без имени
@Test
void registrationWithoutFirstNameTest() {
    $("#firstName").clear();
    open("/automation-practice-form");
    executeJavaScript("""
        document.getElementById('fixedban')?.remove();
        document.querySelector('footer')?.remove();
    """);

    $("#lastName").setValue("Petrova");
    $("#genterWrapper").$(byText("Female")).click();
    $("#userNumber").setValue("9991234567");

    $("#submit").click();

    $(".modal-content").shouldNotHave(text("Thanks for submitting the form"));

}

//Отправка формы без фамилии
@Test
void registrationWithoutLastNameTest() {
    open("/automation-practice-form");
    executeJavaScript("""
        document.getElementById('fixedban')?.remove();
        document.querySelector('footer')?.remove();
    """);

    $("#firstName").setValue("Inna");
    $("#genterWrapper").$(byText("Female")).click();
    $("#userNumber").setValue("9991234567");

    $("#submit").click();

    $(".modal-content").shouldNotHave(text("Thanks for submitting the form"));
}

//Телефон меньше 10 цифр
@Test
void registrationWithShortPhoneNumberTest() {
    open("/automation-practice-form");
    executeJavaScript("""
        document.getElementById('fixedban')?.remove();
        document.querySelector('footer')?.remove();
    """);

    $("#firstName").setValue("Inna");
    $("#lastName").setValue("Shishova");
    $("#genterWrapper").$(byText("Female")).click();
    $("#userNumber").setValue("12345");

    $("#submit").click();

    $(".modal-content").shouldNotHave(text("Thanks for submitting the form"));

//Автотесты на простую форму
//Позитивный-заполнение минимальных полей
    @Test
    void textBoxWithMinimumFieldsTest() {
        String userName = "Inna";
        String userEmail = "inna@test.com";

        open("/text-box");

        executeJavaScript("""
        document.getElementById('fixedban')?.remove();
        document.querySelector('footer')?.remove();
    """);

        $("#userName").setValue(userName);
        $("#userEmail").setValue(userEmail);
        $("#submit").click();

        $("#output").shouldHave(text(userName));
        $("#output").shouldHave(text(userEmail));
    }
//Негативный-невалидный email
    @Test
    void textBoxWithInvalidEmailTest() {
        open("/text-box");

        executeJavaScript("""
        document.getElementById('fixedban')?.remove();
        document.querySelector('footer')?.remove();
    """);

        $("#userName").setValue("Inna");
        $("#userEmail").setValue("inna-test.com");
        $("#submit").click();

        $("#userEmail").shouldHave(attribute("class", "mr-sm-2 field-error form-control"));
    }