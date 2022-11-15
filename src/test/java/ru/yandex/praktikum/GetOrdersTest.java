package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.praktikum.client.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersTest {

    OrderClient orderClient = new OrderClient();

    @Test
    public void getOrdersBasic() {
        ValidatableResponse getOrdersResponse = orderClient.getAllOrders();
        getOrdersResponse.assertThat().body("orders", notNullValue())
                .and().statusCode(SC_OK);
    }

}
