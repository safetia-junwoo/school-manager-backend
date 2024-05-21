package com.base.boilerplate.util.method;

import com.querydsl.core.types.dsl.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

@Component
public class QueryDslUtil {
    public BooleanExpression integerEq(Integer id, NumberPath<Integer> target) {
        return id != null ? target.eq(id) : null;
    }

    public BooleanExpression stringContains(String txt, StringPath target) {
        return StringUtils.hasText(txt) ? target.contains(txt) : null;
    }
    public BooleanExpression stringEq(String txt, StringPath target) {
        return StringUtils.hasText(txt) ? target.eq(txt) : null;
    }

    public BooleanExpression dateBefore(LocalDateTime beforeDate, DateTimePath<LocalDateTime> targetDate){
        if(beforeDate!=null){
            return targetDate.before(beforeDate);
        }
        return null;
    }

    public  <E extends Enum<E>> BooleanExpression enumEq(String enumAsString, EnumPath<E> enumPath, Class<E> enumType) {
        if (enumAsString == null || enumAsString.isEmpty()) {
            return null;
        }

        E enumValue;
        try {
            enumValue = Enum.valueOf(enumType, enumAsString.toUpperCase()); // 문자열을 대문자로 변환하여 enum으로 변환
        } catch (IllegalArgumentException ex) {
            return null; // or you could return a false expression if desired
        }

        return enumPath.eq(enumValue);
    }
    public BooleanExpression dateEq(LocalDate date, DateTimePath<LocalDateTime> targetDate) {
        if (date != null) {
            return  targetDate.eq(date.atStartOfDay());
        }
        return null;
    }

    public BooleanExpression dateYn(String yn, DateTimePath<LocalDateTime> targetDate) {
        if(targetDate == null || yn == null) return null;
        return yn.equals("Y") ?  targetDate.isNotNull() : targetDate.isNull();
    }

    public BooleanExpression dateEq(LocalDateTime date, DateTimePath<LocalDateTime> targetDate) {
        if (date != null) {
            return  targetDate.eq(date);
        }
        return null;
    }
    public BooleanExpression dateContains(LocalDate date, DatePath<LocalDate> targetDate) {
        if (date != null) {
            BooleanExpression isLoeEndDate = targetDate.loe(date);
            return Expressions.allOf(isLoeEndDate);
        }
        return null;
    }

    public BooleanExpression dateBetweenContains(LocalDate startDate, LocalDate endDate, DatePath<LocalDate> targetStartDate, DatePath<LocalDate> targetEndDate) {
        if (startDate != null && endDate != null) {
            BooleanExpression isGoeStartDate = targetStartDate.goe(LocalDate.from(startDate.atStartOfDay()));
            BooleanExpression isLoeEndDate = targetEndDate.loe(LocalDate.from(endDate.atTime(LocalTime.MAX)));
            return Expressions.allOf(isGoeStartDate, isLoeEndDate);
        }
        return null;
    }
    public BooleanExpression dateTimeBetweenContains(LocalDate startDate, LocalDate endDate, DateTimePath<LocalDateTime> targetStartDate, DateTimePath<LocalDateTime> targetEndDate) {
        if (startDate != null && endDate != null) {
            BooleanExpression isGoeStartDate = targetStartDate.goe(startDate.atTime(LocalTime.MIN));
            BooleanExpression isLoeEndDate = targetEndDate.loe(endDate.atTime(LocalTime.MAX));
            return Expressions.allOf(isGoeStartDate, isLoeEndDate);
        }
        return null;
    }

    public BooleanExpression baseYearBetweenContains(Integer baseYear, DateTimePath<LocalDateTime> targetDate){
        if(baseYear==null){
            return null;
        }
        LocalDateTime startDate = LocalDateTime.of(baseYear, Month.JANUARY, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(baseYear, Month.DECEMBER, 31, 23, 59);
        return targetDate.between(startDate,endDate);
    }

    public BooleanExpression integerIn(List<Integer> ids, NumberPath<Integer> target) {
        return ids != null && !ids.isEmpty() ? target.in(ids) : null;
    }

    public BooleanExpression integerNotIn(List<Integer> ids, NumberPath<Integer> target) {
        return ids != null && !ids.isEmpty() ? target.notIn(ids) : null;
    }

    public BooleanExpression ynIsNotNull(String yn, NumberPath<Integer> targetId){
        if(!StringUtils.hasText(yn)){
            return null;
        }
        return yn.equalsIgnoreCase("Y")?targetId.isNotNull():targetId.isNull();
    }
    public BooleanExpression priceBetweenContains(Integer minPrice, Integer maxPrice, NumberPath<Integer> targetPrice){
        if(minPrice==null&&maxPrice==null){
            return null;
        }else if(minPrice!=null&&maxPrice==null){
            return targetPrice.goe(minPrice);
        }else if(minPrice==null){
            return targetPrice.loe(maxPrice);
        }else{
            return Expressions.allOf(targetPrice.goe(minPrice), targetPrice.loe(maxPrice));
        }
    }
}
