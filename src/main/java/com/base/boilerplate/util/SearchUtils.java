package com.base.boilerplate.util;

import com.base.boilerplate.auth.exception.CustomInvalidOrNullException;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchUtils<T extends EntityPathBase>{
    private final JPAQueryFactory jpaQueryFactory;
//    public BooleanBuilder getBuilder(Object condition, PathBuilder<?> entity, List<String> searchArray) throws IllegalAccessException {
//        BooleanBuilder builder = new BooleanBuilder();
//        List<BooleanExpression> booleanExpressions = new ArrayList<>();
//
//
//        Class<?> clazz = condition.getClass();
//        for(Field field : clazz.getDeclaredFields()){
//            field.setAccessible(true);
//            if(!(field.getName().equals("sort")||field.getName().equals("searchWord"))){
//                if(field.get(condition)!=null){
//                    builder.and(entity.get(field.getName()).eq(field.get(condition)));
//                }
//            }else if(field.getName().equals("searchWord")&&field.get(condition)!=null){
//                for(String word : searchArray){
//                    booleanExpressions.add(entity.get(word).eq(field.get(condition)));
//                }
//
//                BooleanExpression bes = null;
//                for (BooleanExpression be : booleanExpressions) {
//                    bes = bes == null ? be : bes.or(be);
//                }
//                builder.and(bes);
//            }
//        }
//        return builder;
//    }
    public String getCode(Class object, String entityName) throws Exception {
        PathBuilder<T> entity = new PathBuilder<T>(object, entityName);
        String code = (String) jpaQueryFactory.select(entity.get("code")).from(entity).orderBy(getOrder("id,desc", entity)).limit(1).fetchOne();
        String taskName = "";
        Integer codeNumber = 0;
        if(code == null){
            code="test1";
        }
        checkNumber:for(int i=0; i<code.length(); i++){
            char ch = code.charAt(i);
            if(48<=ch&&ch<=57){
                taskName = code.substring(0, i);
                codeNumber = Integer.parseInt(code.substring(i))+1;
                break checkNumber;
            }
        }
        return taskName+codeNumber;
    }

    public String getEntityCode(Class object, String entityName, String categoryLabel) throws Exception {
        PathBuilder<T> entity = new PathBuilder<T>(object, entityName);
        String code = (String) jpaQueryFactory.select(entity.get("code")).from(entity).where(entity.getString("code").contains(categoryLabel)).orderBy(getOrder("id,desc", entity)).limit(1).fetchOne();
        if(code == null){
            code="default1";
        }
        return code;
    }

    public String getIncreaseCode(String baseCode){
        String newCode = "";
        checkNumber:for(int i=baseCode.length()-1; i>=0; i--){
            char ch = baseCode.charAt(i);
            if(48<=ch&&ch<=57){
                continue checkNumber;
            }else{
                newCode = baseCode.substring(0, i) + Integer.parseInt(baseCode.substring(i+1))+1;
                break checkNumber;
            }
        }
        return newCode;
    }

    public String getIncreaseNumberFormat(String baseCode){
        String increaseString = "";
        checkNumber:for(int i=baseCode.length()-1; i>=0; i--){
            char ch = baseCode.charAt(i);
            if(48<=ch&&ch<=57){
                continue checkNumber;
            }else{
                Integer increaseNumber = Integer.parseInt(baseCode.substring(i+1))+1;
                increaseString = String.format("%03d", increaseNumber);
                break checkNumber;
            }
        }
        return increaseString;
    }

    public OrderSpecifier[] getOrder(String order, PathBuilder<T> entity) throws Exception {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(order==null){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, entity.get("id")));
        }
        if(StringUtils.hasText(order)){
            try {
                String[] orderArray = order.split(",");
                for (int i = 0; i < orderArray.length; i += 2) {
                    String orderName = orderArray[i];
                    Order orderType = orderArray.length > i + 1 ? ORDER_STATUS.of(orderArray[i + 1]) : Order.DESC;
                    orderSpecifiers.add(new OrderSpecifier(orderType, entity.get(orderName)));
                }
            }catch (Exception e){
                throw new CustomInvalidOrNullException(HttpStatus.BAD_REQUEST, "정렬 형식이 올바르지 않습니다.");
            }
        }
        
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
