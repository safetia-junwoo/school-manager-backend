package com.base.boilerplate.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum ROLE_PRIMITIVE_TYPE {
    NONE(null, null, null),
    ADMIN("ADMIN", "관리자", "CR10001");
    private String value;
    private String label;
    private String code;
    @JsonCreator
    public static ROLE_PRIMITIVE_TYPE forValue(String value) {
        return ROLE_PRIMITIVE_TYPE.of(value);
    }
    public static ROLE_PRIMITIVE_TYPE of(String valueOrName) {
        for(ROLE_PRIMITIVE_TYPE type : ROLE_PRIMITIVE_TYPE.values()){
            if(valueOrName.equalsIgnoreCase(type.getKey()) || valueOrName.equalsIgnoreCase(type.label) || valueOrName.equalsIgnoreCase(type.value) || valueOrName.equalsIgnoreCase(type.code)) {
                return type;
            }
        }
        return ROLE_PRIMITIVE_TYPE.NONE;
    }
    @JsonValue
    public String getValue(){
        return value;
    }
    public String getKey() {
        return name();
    }
}
