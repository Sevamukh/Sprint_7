package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.pojo.*;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    private final static String CREATE_COURIER_PATH = "api/v1/courier";

    private final static String LOGIN_COURIER_PATH = "api/v1/courier/login";

    private final static String DELETE_COURIER_PATH = "api/v1/courier/";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(CourierData data) {
        return given()
                .spec(getSpec())
                .body(data)
                .when()
                .post(CREATE_COURIER_PATH)
                .then();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse loginCourier(CourierLogin login) {
        return given()
                .spec(getSpec())
                .body(login)
                .when()
                .post(LOGIN_COURIER_PATH)
                .then();
    }

    @Step("Удаление курьера\n")
    public ValidatableResponse deleteCourier(String id) {
        return given()
                .spec(getSpec())
                .when()
                .delete(DELETE_COURIER_PATH+id)
                .then();
    }

}
