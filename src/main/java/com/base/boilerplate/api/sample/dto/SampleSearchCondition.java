package com.base.boilerplate.api.sample.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class SampleSearchCondition {
    private Integer id;
    private String name;
    private String delYn;
    private String sort;

}
