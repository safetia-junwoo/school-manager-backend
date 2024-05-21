package com.base.boilerplate.util;

import lombok.Getter;

@Getter
public enum USER_INFO {
    USER_INFO("userInfo"),
    SPOUSE_INFO("spouseInfo"),
    HOSPITALRESERVATION_INFO("reservationInfo"),
    USER_IMAGE("userImage");

    private final String keyName;
    USER_INFO(String keyName) {
        this.keyName = keyName;
    }

}
