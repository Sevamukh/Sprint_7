package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.generator.CourierGenerator;
import ru.yandex.praktikum.pojo.CourierData;
import ru.yandex.praktikum.pojo.CourierLogin;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static ru.yandex.praktikum.generator.CourierGenerator.EmptyField;
import static ru.yandex.praktikum.generator.CourierGenerator.EmptyField.*;


@RunWith(JUnitParamsRunner.class)
public class LoginCourierTest {

    private final CourierData courierValidData = CourierGenerator.getDefaultCourierData();
    private final CourierLogin courierValidLogin = CourierGenerator.getDefaultCourierLogin();
    private CourierLogin courierLogin;
    private String id = "-1";

    CourierClient courierClient = new CourierClient();

    @Before
    public void setUp(){
        courierClient.createCourier(courierValidData);
    }

    @Test
    @Parameters(method = "loginCourierWithOneEmptyFieldParameters")
    @TestCaseName("Логин курьера без {1}")
    public void loginCourierWithOneEmptyField(EmptyField emptyField, String field) {
        courierLogin = CourierGenerator.getCourierLoginWithOneEmptyField(emptyField);
        ValidatableResponse responseLogin = courierClient.loginCourier(courierLogin);

        int statusCode = responseLogin.extract().statusCode();
        String courierLoginMessage = responseLogin.extract().path("message");

        Assert.assertEquals("Неправильная обработка ошибки",
                List.of(SC_BAD_REQUEST, "Недостаточно данных для входа"),
                List.of(statusCode, courierLoginMessage));
    }
    private Object[][] loginCourierWithOneEmptyFieldParameters() {
        return new Object[][]{
                {LOGIN, "логина"},
                {PASSWORD, "пароля"}
        };
    }

    @Test
    @DisplayName("Логин курьера с несуществующей парой логин-пароль")
    public void loginWithIncorrectCourierLogin() {
        courierLogin = CourierGenerator.getIncorrectCourierLogin();
        ValidatableResponse responseLogin = courierClient.loginCourier(courierLogin);

        int statusCode = responseLogin.extract().statusCode();
        String courierLoginMessage = responseLogin.extract().path("message");

        Assert.assertEquals("Неправильная обработка ошибки",
                List.of(SC_NOT_FOUND, "Учетная запись не найдена"),
                List.of(statusCode, courierLoginMessage));
    }

    @After
    public void cleanUp() {
        ValidatableResponse responseLogin = courierClient.loginCourier(courierValidLogin);
        id = responseLogin.extract().path("id").toString();
        courierClient.deleteCourier(id);
    }
}
