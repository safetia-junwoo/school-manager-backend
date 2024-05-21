package com.base.boilerplate.file.dto;

import com.base.boilerplate.file.domain.model.ComFile;
import lombok.*;
import org.springframework.core.io.Resource;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class FileDownloadDTO {
    private String originName;
    private String ext;
    private Resource fileResource;

    public static FileDownloadDTO convertToDTO(ComFile comFile, Resource resource){
        return FileDownloadDTO.builder().originName(comFile.getOriginName()).ext(comFile.getExt()).fileResource(resource).build();
    }
}
