package com.base.boilerplate.file.domain.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "com_relationship_file")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@DynamicInsert
@Builder
public class ComRelationshipFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "FK_task_id")
    private Integer taskId;
    @Column(name = "task_name", nullable = false)
    private String taskName;
    @Column(name = "FK_file_group_code",  columnDefinition = "char(36)")
    private String fileGroupCode;
}
