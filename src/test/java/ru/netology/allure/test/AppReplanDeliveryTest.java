package ru.netology.allure.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.allure.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppReplanDeliveryTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSendAndReplanDateTest() {

        var user = DataGenerator.Registration.generateUser("ru");
        var firstMeetingDate = DataGenerator.generateDate(4);
        var secondMeetingDate = DataGenerator.generateDate(7);

        $("[data-test-id='city'] input.input__control").setValue(user.getCity());
        $("[data-test-id='date'] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input.input__control").setValue(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").setValue(user.getName());
        $("[data-test-id='phone'] input.input__control").setValue(user.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button.button").click();

        $("[data-test-id='success-notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text(firstMeetingDate));

        $("[data-test-id='date'] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input.input__control").setValue(secondMeetingDate);
        $("button.button").click();
        $("[data-test-id='replan-notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(3));

        $("[data-test-id='replan-notification'] button.button").click();
        $("[data-test-id='success-notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text(secondMeetingDate));
    }

    @Test
    public void shouldShowErrorMessageForInvalidPhoneFormatTest() {

        var user = DataGenerator.Registration.generateUser("ru");
        var firstMeetingDate = DataGenerator.generateDate(4);
        var secondMeetingDate = DataGenerator.generateDate(7);

        $("[data-test-id='city'] input.input__control").setValue(user.getCity());
        $("[data-test-id='date'] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input.input__control").setValue(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").setValue(user.getName());
        $("[data-test-id='phone'] input.input__control").setValue(DataGenerator.generateInvalidPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("button.button").click();

        $("[data-test-id='phone']").shouldHave(Condition.cssClass("input_invalid"));
    }
}
