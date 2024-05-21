package com.base.boilerplate.api.sample.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class SamplePaginationDTO {

    private Integer totalPage;
    private long totalCount;
    private List<SampleResponseDTO> content;
}
