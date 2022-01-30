package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

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

    //Positive tests

    //All data enters like text
    @Test
    void correctTest(){
        $(Selectors.byAttribute("type", "text")).setValue("Москва");
        $("[class='input__control'][type='tel']").setValue("02.02.2022");
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
    @Test
    void correctTestCalendarSelectsByclick(){
        $(Selectors.byAttribute("type", "text")).setValue("Москва");
//        $("[class='input__control'][type='tel']").setValue("02.02.2022");
        $("button[type=\"button\"]").click();
        $(Selectors.byAttribute("data-day", "1644526800000")).click();
        $("[name=\"name\"]").setValue("Иван Петров");
        $("[name=\"phone\"]").setValue("+71234567890");
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.appear, Duration.ofSeconds(15));
    }

    //Negative tests
}
