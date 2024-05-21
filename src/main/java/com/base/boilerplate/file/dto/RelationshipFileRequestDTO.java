package com.base.boilerplate.file.dto;

import com.base.boilerplate.file.domain.model.ComRelationshipFile;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RelationshipFileRequestDTO {
    private Integer taskId;
    private String taskName;
    private String fileGroupCode;

    public static ComRelationshipFile convertToEntity(RelationshipFileRequestDTO relationshipFileRequestDTO){
        return ComRelationshipFile.builder().taskName(relationshipFileRequestDTO.getTaskName()).fileGroupCode(relationshipFileRequestDTO.getFileGroupCode()).build();
    }
}
