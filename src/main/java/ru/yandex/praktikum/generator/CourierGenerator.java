package ru.yandex.praktikum.generator;

import ru.yandex.praktikum.pojo.CourierData;
import ru.yandex.praktikum.pojo.CourierLogin;

public class CourierGenerator {
    final static String DEFAULT_LOGIN_NAME = "stdr6";
    final static String DEFAULT_PASSWORD = "123";
    final static String DEFAULT_FIRST_NAME = "Seva";

    final static String INCORRECT_LOGIN_NAME = "hfbnfdlbnlxfgb343";

    final static String INCORRECT_PASSWORD = "70iu6irht";

    public enum EmptyField {
        LOGIN,
        PASSWORD
    }

    public static CourierData getDefaultCourierData() {
        return new CourierData(DEFAULT_LOGIN_NAME, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME);
    }

    public static CourierData getCourierDataWithOneEmptyField(EmptyField emptyField) {
        CourierData courierData = null;
        switch (emptyField) {
            case LOGIN:
                courierData = new CourierData("", DEFAULT_PASSWORD, DEFAULT_FIRST_NAME);
                break;
            case PASSWORD:
                courierData = new CourierData(DEFAULT_LOGIN_NAME, "", DEFAULT_FIRST_NAME);
                break;
        }
        return courierData;
    }

    public static CourierLogin getDefaultCourierLogin() {
        return new CourierLogin(DEFAULT_LOGIN_NAME, DEFAULT_PASSWORD);
    }

    public static CourierLogin getCourierLoginWithOneEmptyField(EmptyField emptyField) {
        CourierLogin courierLogin = null;
        switch (emptyField) {
            case LOGIN:
                courierLogin = new CourierLogin("", DEFAULT_PASSWORD);
                break;
            case PASSWORD:
                courierLogin = new CourierLogin(DEFAULT_LOGIN_NAME, "");
                break;
        }
        return courierLogin;
    }

    public static CourierLogin getIncorrectCourierLogin() {
        return new CourierLogin(INCORRECT_LOGIN_NAME, INCORRECT_PASSWORD);
    }
}
