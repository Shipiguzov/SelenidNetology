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
import java.time.Month;

import static com.codeborne.selenide.Selenide.*;

public class SelenidTests {

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        open("http://localhost:9999/");
    }

    @AfterEach
    void shutdown(){
    }

    //TODO: Complete this method with month and day checks
    void checkDateByCalendar(){
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        String monthName = "";
        switch (month) {
            case 1: monthName = "Январь";
            case 2: monthName = "Февраль";
            case 3: monthName = "Март";
            case 4: monthName = "Апрель";
        }
        $("button[type=\"button\"]").click();
        $(Selectors.byAttribute("data-step", "12")).click();
        year++;
        $(Selectors.withText(String.valueOf(year))).should(Condition.appear, Duration.ofSeconds(15));
        $(Selectors.byAttribute("data-step", "12")).click();
        year++;
        $(Selectors.withText(String.valueOf(year))).should(Condition.appear, Duration.ofSeconds(15));

    }

    //Positive tests

    //All data enters like text
    @Test
    void correctTest(){
        $(Selectors.byAttribute("type", "text")).setValue("Москва");
        LocalDate date = LocalDate.now().plusDays(5);
        StringBuilder dateToForm = new StringBuilder()
                .append(date.getDayOfMonth())
                .append(".")
                .append(date.getMonthValue())
                .append(".")
                .append(date.getYear());
        System.out.println(dateToForm);
        $("[class='input__control'][type='tel']").clear();
        $("[class='input__control'][type='tel']").setValue(dateToForm.toString());
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+71234567890");
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.appear, Duration.ofSeconds(15));
    }

    //City selects by click after enter first letter
    @Test
    void correctTestCitySelectsByClick(){
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

    //Data selects by click in form
    //TODO: Complete this test
    @Test
    void correctTestCalendarSelectsByclick(){
        $(Selectors.byAttribute("type", "text")).setValue("Москва");
//        $("[class='input__control'][type='tel']").setValue("02.02.2022");
        checkDateByCalendar();
        /*$(Selectors.byAttribute("data-day", "1644526800000")).click();
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+71234567890");
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.appear, Duration.ofSeconds(15));*/
    }

    //Negative tests

    //City name by English
    @Test
    void incorrectCity(){
        $(Selectors.byAttribute("type", "text")).setValue("Mos");
        $("[class='input__control'][type='tel']").setValue("02.02.2022");
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+71234567890");
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.withText("Доставка в выбранный город недоступна")).should(Condition.appear, Duration.ofSeconds(5));
    }

    //TODO: complete test with date in past
    //Entered data in the past
    @Test
    void dataInPast(){
        $(Selectors.byAttribute("type", "text")).setValue("Москва");
        $("[class='input__control'][type='tel']").setValue("02.02.2021");
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+71234567890");
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.byText("Заказ на выбранную дату невозможен")).should(Condition.appear, Duration.ofSeconds(5));
    }

    //TODO: complete test with date today + 1 day

    //TODO: complete test with incorrect phone number

    //TODO: complete test with unchecked checkbox

}
