package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.yandex.praktikum.client.*;
import ru.yandex.praktikum.generator.OrderGenerator;
import ru.yandex.praktikum.pojo.*;

import java.util.List;

import static org.apache.http.HttpStatus.*;

@RunWith(JUnitParamsRunner.class)
public class CreateOrderTest {

    private String track;
    OrderClient orderClient = new OrderClient();

    @Test
    @Parameters(method = "createOrderWithColorsParameters")
    @TestCaseName("Создание заказа с цветами самоката: {0}")
    public void createOrderWithColors(List<String> colors) {
        NewOrder newOrder = OrderGenerator.getDefaultNewOrder();
        newOrder.setColor(colors);
        ValidatableResponse responseCreate = orderClient.createOrder(newOrder);

        int statusCode = responseCreate.extract().statusCode();
        String track = responseCreate.extract().path("track").toString();

        Assert.assertEquals("Ошибка в коде или теле ответа",
                List.of(SC_CREATED, true),
                List.of(statusCode, !track.isEmpty()));

    }
    private Object[][] createOrderWithColorsParameters() {
        return new Object[][]{
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
        };
    }

    @After
    public void cleanUp() {
        OrderTrack orderTrack = OrderGenerator.getOrderTrackByNumber(track);
        orderClient.cancelOrder(orderTrack);
    }
}
