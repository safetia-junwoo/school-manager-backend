package com.base.boilerplate.api.sample.domain.model;

import com.base.boilerplate.api.sample.dto.SampleRequestDTO;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "com_sample")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@DynamicInsert
@Builder
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "del_yn")
    private String delYn;


    public static Sample convertToEntity(SampleRequestDTO requestDto) {
        return Sample.builder()
                .id(requestDto.getId())
                .name(requestDto.getName())
                .build();
    }
}
