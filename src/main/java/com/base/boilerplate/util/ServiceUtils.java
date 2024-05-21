package com.base.boilerplate.util;

import com.base.boilerplate.auth.exception.CustomExcelInvalidException;
import com.base.boilerplate.auth.exception.CustomInvalidOrNullException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ServiceUtils {
    public static final Integer ERROR_SIZE_LIMIT = 20;
    public static void checkErrorSizeByExcelData(CustomExcelInvalidException customExcelInvalidException) throws CustomExcelInvalidException, CustomInvalidOrNullException {
        if(customExcelInvalidException.getErrors().size()!=0){
            throw customExcelInvalidException;
        }else if(customExcelInvalidException.getErrors().size()>ERROR_SIZE_LIMIT){
            throw new CustomInvalidOrNullException(HttpStatus.BAD_REQUEST, "많은 데이터가 잘못되었습니다. 확인 부탁드립니다.");
        }
    }
    public static double roundToFirstDecimal(double value) {
        // 소수 둘째 자리에서 반올림
        double roundedValue = Math.round(value * 10.0) / 10.0;
        return roundedValue;
    }

    public String sendApiWithBody(HttpMethod http, String url, Map<String, Object> map) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Content-type", "application/json;charset=UTF-8");

        HttpEntity<?> requestMessage = new HttpEntity<>(map, headers);
        ResponseEntity<?> serverResponse = restTemplate.exchange(url, http, requestMessage, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(serverResponse.getBody().toString());

        return root.path("data").asText();
    }
}