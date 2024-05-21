package com.base.boilerplate.auth.dto;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDetailsDTO {
    private UserDetails userDetails;
    private Integer id;
    private String roleName;
}
