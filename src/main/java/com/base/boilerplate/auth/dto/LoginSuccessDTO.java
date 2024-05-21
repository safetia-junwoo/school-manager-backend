package com.base.boilerplate.auth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LoginSuccessDTO {
    private UserDTO userInfo;
    private TokenDTO tokenInfo;
}
