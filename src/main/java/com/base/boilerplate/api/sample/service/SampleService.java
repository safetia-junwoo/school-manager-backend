package com.base.boilerplate.api.sample.service;

import com.base.boilerplate.api.sample.domain.model.Sample;
import com.base.boilerplate.api.sample.domain.repository.SampleRepository;
import com.base.boilerplate.api.sample.dto.SamplePaginationDTO;
import com.base.boilerplate.api.sample.dto.SampleRequestDTO;
import com.base.boilerplate.api.sample.dto.SampleResponseDTO;
import com.base.boilerplate.api.sample.dto.SampleSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class SampleService {

    private final SampleRepository sampleRepository;

    public SamplePaginationDTO retrievePage(SampleSearchCondition searchCondition, Pageable pageable) throws Exception{
        Page<SampleResponseDTO> content = sampleRepository.retrievePage(searchCondition, pageable);
        SamplePaginationDTO dto = new  SamplePaginationDTO(content.getTotalPages(), content.getTotalElements(), content.getContent());
        return dto;
    }

    public SamplePaginationDTO retrieveAll(SampleSearchCondition searchCondition) throws Exception {
        List<SampleResponseDTO> content = sampleRepository.retrieveAll(searchCondition);
        SamplePaginationDTO dto = new SamplePaginationDTO(1, content.size(),  content);
        return dto;
    }
    public Integer upsertItem(SampleRequestDTO requestDto) {
        Sample newItem = sampleRepository.save(Sample.convertToEntity(requestDto));
        requestDto.setId(newItem.getId());
        return requestDto.getId();
    }
    public Integer removeItem(SampleRequestDTO requestDto) {
        requestDto.setDelYn("Y");
        Sample newItem = sampleRepository.save(Sample.convertToEntity(requestDto));
        requestDto.setId(newItem.getId());
        return requestDto.getId();
    }


}
