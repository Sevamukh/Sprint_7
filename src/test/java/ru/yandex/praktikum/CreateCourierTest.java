package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.yandex.praktikum.client.*;
import ru.yandex.praktikum.generator.CourierGenerator;
import ru.yandex.praktikum.pojo.*;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static ru.yandex.praktikum.generator.CourierGenerator.EmptyField;
import static ru.yandex.praktikum.generator.CourierGenerator.EmptyField.*;

@RunWith(JUnitParamsRunner.class)
public class CreateCourierTest {

    private CourierData courierData;
    private final CourierLogin courierValidLogin = CourierGenerator.getDefaultCourierLogin();
    private String id = "-1";

    CourierClient courierClient = new CourierClient();

    @Test
    @DisplayName("Создание курьера с валидными данными")
    public void createCourierWithValidData() {
        courierData = CourierGenerator.getDefaultCourierData();
        ValidatableResponse responseCreate = courierClient.createCourier(courierData);
        ValidatableResponse responseLogin = courierClient.loginCourier(courierValidLogin);

        id = responseLogin.extract().path("id").toString();
        int statusCode = responseCreate.extract().statusCode();
        boolean isCourierCreated = responseCreate.extract().path("ok");

        Assert.assertEquals("Ошибка в коде или теле ответа", List.of(SC_CREATED, true),
                List.of(statusCode, isCourierCreated));
    }

    @Test
    @Parameters(method = "createCourierWithOneEmptyFieldParameters")
    @TestCaseName("Создание курьера без {1}")
    public void createCourierWithOneEmptyField(EmptyField emptyField, String field) {
        courierData = CourierGenerator.getCourierDataWithOneEmptyField(emptyField);
        ValidatableResponse responseCreate = courierClient.createCourier(courierData);

        int statusCode = responseCreate.extract().statusCode();
        String courierCreatedMessage = responseCreate.extract().path("message");

        Assert.assertEquals("Неправильная обработка ошибки",
                List.of(SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи"),
                List.of(statusCode, courierCreatedMessage));
    }
    private Object[][] createCourierWithOneEmptyFieldParameters() {
        return new Object[][]{
                {LOGIN, "логина"},
                {PASSWORD, "пароля"}
        };
    }

    @Test
    @DisplayName("Создание курьера с повторяющимся логином")
    public void createDuplicateCourier() {
        courierData = CourierGenerator.getDefaultCourierData();
        courierClient.createCourier(courierData);
        ValidatableResponse responseCreate = courierClient.createCourier(courierData);
        ValidatableResponse responseLogin = courierClient.loginCourier(courierValidLogin);

        id = responseLogin.extract().path("id").toString();
        int statusCode = responseCreate.extract().statusCode();
        String courierCreatedMessage = responseCreate.extract().path("message");

        Assert.assertEquals("Неправильная обработка ошибки",
                List.of(SC_CONFLICT, "Этот логин уже используется. Попробуйте другой."),
                List.of(statusCode, courierCreatedMessage));
    }

    @After
    public void cleanUp() {
        if (!id.equals("-1")) courierClient.deleteCourier(id);
        id = "-1";
    }
}
