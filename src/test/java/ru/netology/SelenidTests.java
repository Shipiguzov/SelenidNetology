package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

import static com.codeborne.selenide.Selenide.*;

public class SelenidTests {

    @BeforeAll
    static void setupAll() {
    }

    @BeforeEach
    void setup() {
        /*ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");*/
        open("http://localhost:9999/");
    }

    @AfterEach
    void shutdown() {
    }

    //Positive tests

    //All data enters like text
    @Test
    void correctTest() {
        $(Selectors.byAttribute("type", "text")).setValue("Москва");
        LocalDate date = LocalDate.now().plusDays(5);
        $("[class='input__control'][type='tel']").setValue(date.toString());
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+91234567890");
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.appear, Duration.ofSeconds(15));
    }

    //City selects by click after enter first letter
    @Test
    void correctTestCitySelectsByClick() {
//        $(Selectors.byAttribute("type", "text")).setValue("Москва");
        $(Selectors.byAttribute("type", "text")).setValue("Мо");
        $(Selectors.byText("Москва")).click();
        $("[class='input__control'][type='tel']").setValue("02.02.2022");
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+71234567890");
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.appear, Duration.ofSeconds(15));
    }

    //Data selects from calendar
    @Test
    void dateFromCalendar() {
        $(Selectors.byAttribute("type", "text")).setValue("Москва");
        $(Selectors.byClassName("input_type_tel")).click();
        String data = $(Selectors.byClassName("calendar__day_state_current"))
                .getAttribute("data-day");
        long newDate = Long.valueOf(data) + 86_400_000;
        $(Selectors.byAttribute("data-day", String.valueOf(newDate))).click();
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+91234567890");
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.appear, Duration.ofSeconds(15));
    }

    //Negative tests

    //City name in English
    @Test
    void incorrectCity() {
        $(Selectors.byAttribute("type", "text")).setValue("Mos");
        $("[class='input__control'][type='tel']").setValue("02.02.2022");
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+71234567890");
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.withText("Доставка в выбранный город недоступна")).should(Condition.appear, Duration.ofSeconds(5));
    }

    //Entered wrong data
    @Test
    void wrongDate() {
        $(Selectors.byAttribute("type", "text")).setValue("Москва");
        LocalDate date = LocalDate.now();
        $("[class='input__control'][type='tel']").setValue(date.toString());
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+71234567890");
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.withText("Заказ на выбранную дату невозможен")).should(Condition.appear, Duration.ofSeconds(15));
    }

    //wrong phone number
    @Test
    void wrongPhoneNumberTest() {
        $(Selectors.byAttribute("type", "text")).setValue("Москва");
        $("[class='input__control'][type='tel']").setValue(LocalDate.now().plusDays(10).toString());
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+712");
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."))
                .should(Condition.appear, Duration.ofSeconds(15));
    }

    //checkbox unchecked
    @Test
    void checkboxError() {
        $(Selectors.byAttribute("type", "text")).setValue("Москва");
        $("[class='input__control'][type='tel']").setValue(LocalDate.now().plusDays(10).toString());
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+712");
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.byClassName("input_invalid"))
                .should(Condition.appear, Duration.ofSeconds(15));
    }
}
