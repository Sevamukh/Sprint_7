package ru.yandex.praktikum.generator;

import ru.yandex.praktikum.pojo.NewOrder;
import ru.yandex.praktikum.pojo.OrderTrack;

import java.util.List;

public class OrderGenerator {
    final static String DEFAULT_FIRST_NAME = "Naruto";
    final static String DEFAULT_LAST_NAME = "Uchiha";
    final static String DEFAULT_ADDRESS = "Konoha, 142 apt.";
    final static String DEFAULT_METRO_STATION = "Арбатская";
    final static String DEFAULT_PHONE = "+7 800 355 35 35";
    final static int DEFAULT_RENT_TIME = 5;
    final static String DEFAULT_DELIVERY_DATE= "2020-06-06";
    final static String DEFAULT_COMMENT= "Saske, come back to Konoha";
    final static List<String> DEFAULT_COLOR= List.of();

    public static NewOrder getDefaultNewOrder() {
        return new NewOrder(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_ADDRESS,
                DEFAULT_METRO_STATION, DEFAULT_PHONE, DEFAULT_RENT_TIME, DEFAULT_DELIVERY_DATE,
                DEFAULT_COMMENT, DEFAULT_COLOR);
    }

    public static OrderTrack getOrderTrackByNumber(String track) {
        return new OrderTrack(track);
    }
}
