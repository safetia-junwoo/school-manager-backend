package com.base.boilerplate.file.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class FileResponseDTO {
    List<FileInfoDTO> files;
    private String fileGroupCode;
}
