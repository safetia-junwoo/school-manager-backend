package com.base.boilerplate.file.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FileInfoDTO {
    private Integer id;
    private String originFileName;
    private LocalDateTime createDate;

}
