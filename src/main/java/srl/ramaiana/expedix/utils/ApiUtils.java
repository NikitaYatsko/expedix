package srl.ramaiana.expedix.utils;
import jakarta.servlet.http.Cookie;

import java.util.UUID;


public class ApiUtils {
    public static Cookie createCookie(String value) {
        Cookie authorizationCookie = new Cookie("Authorization", value);
        authorizationCookie.setHttpOnly(true);
        authorizationCookie.setSecure(true);
        authorizationCookie.setPath("/");
        authorizationCookie.setMaxAge(300);
        return authorizationCookie;
    }


    public static String generateUuidWithoutDash(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


}
