package com.base.boilerplate.api.sample.domain.repository.custom;


import com.base.boilerplate.api.sample.domain.model.Sample;
import com.base.boilerplate.api.sample.dto.SampleResponseDTO;
import com.base.boilerplate.api.sample.dto.SampleSearchCondition;
import com.base.boilerplate.util.SearchUtils;
import com.base.boilerplate.util.method.QueryDslUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;


import java.util.List;

import static com.base.boilerplate.api.sample.domain.model.QSample.sample;


@Repository
@RequiredArgsConstructor
public class CustomSampleRepositoryImpl implements CustomSampleRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final SearchUtils searchUtils;

    private final QueryDslUtil dslUtil = new QueryDslUtil();

    @Override
    public Page<SampleResponseDTO> retrievePage(SampleSearchCondition searchCondition, Pageable pageable) throws Exception {
        OrderSpecifier[] order = searchUtils.getOrder(searchCondition.getSort(), new PathBuilder<>(Sample.class, "sample"));
        List<SampleResponseDTO> content = getContent(searchCondition, pageable, order);
        JPAQuery<Long> countQuery = getCount(searchCondition);
        return PageableExecutionUtils.getPage(content,pageable, countQuery::fetchOne);
    }

    @Override
    public List<SampleResponseDTO> retrieveAll(SampleSearchCondition searchCondition) throws Exception {
        OrderSpecifier[] order = searchUtils.getOrder(searchCondition.getSort(), new PathBuilder<>(Sample.class, "sample"));
        List<SampleResponseDTO> content = getContent(searchCondition, null, order);
        return content;
    }

    public Predicate generateWhere(SampleSearchCondition searchCondition) {
        return Expressions.asBoolean(true).isTrue() // 초기값 설정
                .and(dslUtil.integerEq(searchCondition.getId(), sample.id))
                .and(dslUtil.stringContains(searchCondition.getName(), sample.name));
    }

    public JPAQuery<SampleResponseDTO> getQuery() {
        return jpaQueryFactory
                .select(Projections.bean(SampleResponseDTO.class,
                        sample.id,
                        sample.name
                ))
                .from(sample);
    }

    public List<SampleResponseDTO> getContent(SampleSearchCondition searchCondition,  Pageable pageable, OrderSpecifier[] order) throws Exception {
        JPAQuery<SampleResponseDTO> query =getQuery();
        query.where(generateWhere(searchCondition))
                .orderBy(order);
        if(pageable != null) {
            query.offset(pageable.getOffset()).limit(pageable.getPageSize());
        }
        query.fetch();
        List<SampleResponseDTO> content = query.fetch();
        return content;
    }
    public JPAQuery<Long> getCount(SampleSearchCondition searchCondition){
        return jpaQueryFactory
                .select(sample.count())
                .from(sample)
                .where(generateWhere(searchCondition));
    }



}
