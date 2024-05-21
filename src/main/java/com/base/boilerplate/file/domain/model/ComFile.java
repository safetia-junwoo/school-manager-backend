package com.base.boilerplate.file.domain.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "com_file")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@DynamicInsert
@Builder
public class ComFile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "origin_name", nullable = false)
    private String originName;
    @Column(name = "uuid_name", nullable = false, unique = true)
    private String uuidName;
    @Column(name = "ext", nullable = false)
    private String ext;
    @Column(name = "path", nullable = false)
    private String path;
    @Column(name = "full_path", nullable = false)
    private String fullPath;
    @Column(name = "file_group_code", columnDefinition = "char(36)")
    private String fileGroupCode;
    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;
//
//    @Column(name = "thumbnailFileFullPath")
//    private String thumbnailFileFullPath;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "codeId")
//    private ComCode comCode;
//
//    @Column(name = "dataId")
//    private Integer dataId;
}
