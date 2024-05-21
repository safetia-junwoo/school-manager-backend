package com.base.boilerplate.auth.domain.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "com_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = {"comUser"})
@DynamicInsert
@Builder
public class ComRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_create_user_id", nullable = false)
    private ComUser comUser;
    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1) default 'Y'")
    private String useYn;
}
