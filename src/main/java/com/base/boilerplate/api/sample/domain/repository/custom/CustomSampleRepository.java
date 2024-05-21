package com.base.boilerplate.api.sample.domain.repository.custom;


import com.base.boilerplate.api.sample.dto.SampleResponseDTO;
import com.base.boilerplate.api.sample.dto.SampleSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomSampleRepository {
    Page<SampleResponseDTO> retrievePage(SampleSearchCondition searchCondition, Pageable pageable) throws Exception;
    List<SampleResponseDTO> retrieveAll(SampleSearchCondition searchCondition) throws Exception;
}
