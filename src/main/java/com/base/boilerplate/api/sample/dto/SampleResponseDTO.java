package com.base.boilerplate.api.sample.dto;

import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class SampleResponseDTO {

    private Integer id;
    private String name;
    private String delYn;


}
