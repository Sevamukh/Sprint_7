package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.pojo.NewOrder;
import ru.yandex.praktikum.pojo.OrderTrack;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {

    private final static String CREATE_ORDER_PATH = "api/v1/orders";

    private final static String GET_ORDER_PATH = "api/v1/orders";
    private final static String CANCEL_ORDER_PATH = "api/v1/orders/cancel";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(NewOrder newOrder) {
        return given()
                .spec(getSpec())
                .body(newOrder)
                .when()
                .post(CREATE_ORDER_PATH)
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(OrderTrack orderTrack) {
        return given()
                .spec(getSpec())
                .body(orderTrack)
                .when()
                .post(CANCEL_ORDER_PATH)
                .then();
    }

    @Step("Получение всех заказов")
    public ValidatableResponse getAllOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(GET_ORDER_PATH)
                .then();
    }
}