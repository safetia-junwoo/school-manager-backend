package com.base.boilerplate.util;

import com.base.boilerplate.config.GlobalSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class TokenUtils {

    private final GlobalSettings globalSettings;

    public Cookie buildTokenCookie(String tokenName, String token){
        Integer tokenMaxAge = tokenName.equals("accessToken")?globalSettings.getAccessTokenCookieExpirationTime(): globalSettings.getRefreshTokenCookieExpirationTime();
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setMaxAge(tokenMaxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        return cookie;
    }

    public Cookie deleteCookie(String tokenName){
        Cookie cookie = new Cookie(tokenName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        return cookie;
    }

    public String getToken(String tokenName, HttpServletRequest request) {
        System.out.println(request.getRequestURL());
        if(request.getCookies()!=null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals(tokenName)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
