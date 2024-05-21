package com.base.boilerplate.auth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LoginDTO {
    private String loginId;
    private String loginPassword;
    private String roleName;
}
