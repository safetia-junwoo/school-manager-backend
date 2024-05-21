package com.base.boilerplate.api.sample.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SampleRequestDTO {
    private Integer id;
    private String name;
    private String delYn;
}
