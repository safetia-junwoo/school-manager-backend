package com.base.boilerplate.auth.dto;

import com.base.boilerplate.auth.domain.model.ComUser;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UserDTO {
    private Integer id;
    private String email;
    private String password;
    private String name;
    private String language;
    private String login_type;
    private String login_token;
    private String address;
    private String gender;
    private String phone;
    private String roleId;
    private String roleCode;
    private String roleName;

    public static UserDTO convertToDTO(ComUser comUser){
        return UserDTO.builder()
                .id(comUser.getId())
                .name(comUser.getName())
                .build();
    }
}
