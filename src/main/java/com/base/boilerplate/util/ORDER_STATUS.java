package com.base.boilerplate.util;

import com.querydsl.core.types.Order;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum ORDER_STATUS {
    DESC("내림차순", "desc",  Order.DESC),
    ASC("오름차순", "asc", Order.ASC);
    private String kor;
    private String eng;
    private Order order;

    public static Order of(String value){
        if(value.equalsIgnoreCase(ORDER_STATUS.DESC.kor)||value.equalsIgnoreCase(ORDER_STATUS.DESC.eng)){
            return ORDER_STATUS.DESC.order;
        }else if(value.equalsIgnoreCase(ORDER_STATUS.ASC.kor)||value.equalsIgnoreCase(ORDER_STATUS.ASC.eng)) {
            return ORDER_STATUS.ASC.order;
        }else{
            return ORDER_STATUS.DESC.order;
        }
    }
}
