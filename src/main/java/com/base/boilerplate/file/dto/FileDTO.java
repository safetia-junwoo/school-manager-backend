package com.base.boilerplate.file.dto;

import com.base.boilerplate.file.domain.model.ComFile;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class FileDTO {
    private Integer id;
    private String originName;
    private String uuidName;
    private String ext;
    private String path;
    private String fullPath;
    private String fileGroupCode;

    public static ComFile convertToEntity(FileDTO fileDTO) {
        return ComFile.builder()
                .originName(fileDTO.getOriginName())
                .uuidName(fileDTO.getUuidName())
                .ext(fileDTO.getExt())
                .path(fileDTO.getPath())
                .fullPath(fileDTO.getFullPath())
                .fileGroupCode(fileDTO.getFileGroupCode())
                .build();
    }
//    private String fileFullPath;
//    private String thumbnailFileFullPath;

}
