package com.base.boilerplate.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

@ConstructorBinding
@ConfigurationProperties(prefix = "global")
@RequiredArgsConstructor
@Getter
public class GlobalSettings {
    private final String domain;
    private final String fileDelimiter;
    private final String fileExtList;
    private final String fileUploadPath;
    private final String defaultFileImagePath;
    private final String allowedOrigin;
    private final String allowedOriginBatchServer;
    private final String allowedOriginSSOServer;
    private final String secretKey;
    private final long accessTokenExpirationTime;
    private final long refreshTokenExpirationTime;
    private final Integer accessTokenCookieExpirationTime;
    private final Integer refreshTokenCookieExpirationTime;
    private final String cookieOption;
    private final String admin;
    private final String smsUrl;
    private final String smsTableKey;
    private final String smsFrom;
    private final String mailFrom;

    public long getAccessTokenExpirationTime() {
        return Duration.ofMinutes(this.accessTokenExpirationTime).toMillis();
    }
    public long getRefreshTokenExpirationTime() {
        return Duration.ofMinutes(this.refreshTokenExpirationTime).toMillis();
    }
    public Integer getAccessTokenCookieExpirationTime(){
        return this.accessTokenCookieExpirationTime * 60;
    }
    public Integer getRefreshTokenCookieExpirationTime(){
        return this.refreshTokenCookieExpirationTime * 60;
    }
}
