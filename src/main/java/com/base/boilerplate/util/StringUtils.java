package com.base.boilerplate.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class StringUtils {
//    private final SupportManagementService supportManagementService;

    public String getMailText(String text, String type) {
        // 먼저 type이 "string"인지 확인
        if ("string".equals(type)) {
            // text가 null이 아니면서 비어있지 않은 경우 text를 반환
            if (text != null && !text.isEmpty()) {
                return text;
            }
        } else if ("date".equals(type)){
            try {
                LocalDateTime dateTime = LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                // 원하는 날짜 형식으로 변환 (예: "yyyy-MM-dd")
                DateTimeFormatter targetFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 필요한 날짜 형식으로 수정 가능
                return targetFormat.format(dateTime);
            } catch (Exception e) {
                // 파싱 중 에러가 발생한 경우, 에러 메시지를 반환하거나 빈 문자열을 반환
                return "";
            }
        }
        // 위의 조건에 해당하지 않으면 빈 문자열 반환
        return "";
    }

    public String getFormatSocialSecurityNumber(String securityNumber){
        StringBuilder stringBuilder = new StringBuilder(securityNumber);
        stringBuilder.insert(stringBuilder.length()-1, "-");
        return stringBuilder.toString();
    }


    public String getFormatDateToKr(LocalDateTime localDateTime){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
        return localDateTime.format(format);
    }
    public String makeFilePathName(String codeValue) {
        if (codeValue.contains(" ")) {
            return codeValue.replace(" ", "_").toLowerCase();
        } else {
            return codeValue.toLowerCase();
        }
    }

    public String encodeFileName(String fileName) throws UnsupportedEncodingException {
        return URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
    }

    public static String getRoleName() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
    }

    public <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static String formatNumberWithComma(long number) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        return formatter.format(number);
    }

//    public List<Integer> getHighRoles(){
//        return supportManagementService.retrieveSupportManagementHighRoleList();
//    }
}
