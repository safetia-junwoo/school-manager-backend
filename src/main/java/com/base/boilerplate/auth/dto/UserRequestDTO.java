package com.base.boilerplate.auth.dto;

import com.base.boilerplate.auth.domain.model.ComUser;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UserRequestDTO {
    private Integer id;
    private String email;
    private String password;
    private String name;
    private String language;
    private String loginType;
    private String loginToken;
    private String address;
    private String gender;
    private String phone;
    private String roleId;
    private String roleCode;
    private String roleName;

    public static UserRequestDTO convertToDTO(ComUser comUser){
        return UserRequestDTO.builder()
                .id(comUser.getId())
                .name(comUser.getName())
                .build();
    }
}
