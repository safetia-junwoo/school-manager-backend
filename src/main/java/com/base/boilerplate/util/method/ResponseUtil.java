package com.base.boilerplate.util.method;

import com.base.boilerplate.auth.service.AuthService;
import com.base.boilerplate.util.OwnerIdentifiable;
import com.base.boilerplate.util.enums.ROLE_PRIMITIVE_TYPE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class ResponseUtil {
    @Autowired
    AuthService authService;

    public class FailDTO  implements OwnerIdentifiable {


        @Override
        public Integer getOwnerId() {
            return null;
        }

        @Override
        public String getFailCode() {
            return "NO_AUTHENTICATION";
        }
    }

    public boolean checkTargetUserId(Integer requestTargetUserId, Integer findTargetUserId){
        if(requestTargetUserId!=null&&findTargetUserId!=null){
            return requestTargetUserId.equals(findTargetUserId);
        }
        return false;
    }
    public <T extends OwnerIdentifiable> ResponseEntity<?> sendResponse(T parcel, HttpServletRequest request) {
        if(parcel.getOwnerId()==null){
            return ResponseEntity.ok().body(null);
        }
        Integer ownerId = parcel.getOwnerId();
        Integer tokenUserId =authService.getUserId(request);
        String role = authService.getRole(request);
        if(role.equals(ROLE_PRIMITIVE_TYPE.ADMIN.getKey())){
            return ResponseEntity.ok().body(parcel);
        }
        if(ownerId.equals(tokenUserId)) {
            return ResponseEntity.ok().body(parcel);
        }
        FailDTO failDto = new FailDTO();
        return  ResponseEntity.ok().body(failDto);
    }

    public <T extends OwnerIdentifiable> ResponseEntity<?> sendResponseList(List<T> parcel, HttpServletRequest request) {
        if(parcel!=null&&!parcel.isEmpty()){
            Integer ownerId = parcel.get(0).getOwnerId();
            Integer tokenUserId =authService.getUserId(request);
            String authKey = authService.getAuthKey(request);
            String role = authService.getRole(request);
            if(role.equals(ROLE_PRIMITIVE_TYPE.ADMIN.getKey())){
                return ResponseEntity.ok().body(parcel);
            }
            if(ownerId.equals(tokenUserId)) {
                return ResponseEntity.ok().body(parcel);
            }
        }
        FailDTO failDto = new FailDTO();
        return  ResponseEntity.ok().body(failDto);
    }

}
