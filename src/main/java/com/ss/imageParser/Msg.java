package com.ss.imageParser;

import vl.utils.MapList;
import vl.utils.MapMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Msg {

    public static final String US_en = "US_en";
    public static final String RU_ru = "RU_ru";

    public static final String CODE_LOGIN_ERR_SESSION_EXIPED = "CODE_LOGIN_ERR_SESSION_EXIPED";
    private static final String RU_MSG_LOGIN_ERR_SESSION_EXIPED = "Ваша сессия истекла, зайдите заново";

    public static final String CODE_UNAUTHORIZED_HTTP_REQUEST = "CODE_UNAUTHORIZED_HTTP_REQUEST";
    private static final String RU_UNAUTHORIZED_HTTP_REQUEST = "Пользователь не авторизован";

    public static final String CODE_FORBIDDEN_ERR = "CODE_FORBIDDEN_ERR";
    private static final String RU_FORBIDDEN_ERR = "Доступ к запрашиваемому ресурсу запрещен";

    public static final String CODE_RESOURCE_NOT_FOUND_ERR = "CODE_RESOURCE_NOT_FOUND_ERR";
    private static final String RU_RESOURCE_NOT_FOUND_ERR = "Невозможно найти запрашиваемый ресурс. ";

    public static final String CODE_INCORRECT_URL_ERR = "CODE_INCORRECT_URL_ERR";
    private static final String RU_INCORRECT_URL_ERR = "Некорректный url";

    private static final HashMap<String, Msg> map = new HashMap<>();
    private static final MapMap<String, String, String> msg = new MapMap<>();   // locale, code, translation
    private static final MapList<String, String> months = new MapList<>();   // locale, List of month

    static {
        map.put(RU_ru, new Msg(RU_ru));

        HashMap<String, String> mMess;
        mMess = msg.getOrCreate(RU_ru);
        mMess.put(CODE_LOGIN_ERR_SESSION_EXIPED, RU_MSG_LOGIN_ERR_SESSION_EXIPED);
        mMess.put(CODE_UNAUTHORIZED_HTTP_REQUEST, RU_UNAUTHORIZED_HTTP_REQUEST);
        mMess.put(CODE_FORBIDDEN_ERR, RU_FORBIDDEN_ERR);
        mMess.put(CODE_RESOURCE_NOT_FOUND_ERR, RU_RESOURCE_NOT_FOUND_ERR);
        mMess.put(CODE_INCORRECT_URL_ERR, RU_INCORRECT_URL_ERR);

        ArrayList<String> aMonth = months.getOrCreateList(RU_ru);
        aMonth.addAll(Arrays.asList("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"));
    }

    private static Msg instance = map.get(RU_ru);
    private String locale;

    public Msg(String locale) {
        this.locale = locale;
    }

    public static Msg i() {
        return instance;
    }

    public static void changeLocale(String locale) {
        instance = map.get(locale);
        instance.locale = locale;
    }

    public Message4User getMessage(String messageCode) {
        String sRes = msg.get(locale, messageCode);
        return (sRes != null) ? new Message4User(sRes) : new Message4User(messageCode);
    }
}
