package srl.ramaiana.expedix.utils;
import jakarta.servlet.http.Cookie;


public class ApiUtils {
    public static Cookie createCookie(String value) {
        Cookie authorizationCookie = new Cookie("Authorization", value);
        authorizationCookie.setHttpOnly(true);
        authorizationCookie.setSecure(true);
        authorizationCookie.setPath("/");
        authorizationCookie.setMaxAge(300);
        return authorizationCookie;
    }

}
