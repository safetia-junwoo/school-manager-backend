package com.base.boilerplate.auth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
}
