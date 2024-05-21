package com.base.boilerplate.api.sample.domain.repository;

import com.base.boilerplate.api.sample.domain.model.Sample;
import com.base.boilerplate.api.sample.domain.repository.custom.CustomSampleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Integer>, CustomSampleRepository {
}
