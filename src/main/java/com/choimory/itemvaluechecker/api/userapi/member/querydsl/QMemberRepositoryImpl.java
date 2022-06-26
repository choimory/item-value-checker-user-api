package com.choimory.itemvaluechecker.api.userapi.member.querydsl;

import com.choimory.itemvaluechecker.api.userapi.common.querydsl.CommonQuerydslUtil;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberListRequest;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.choimory.itemvaluechecker.api.userapi.common.querydsl.CommonQuerydslUtil.getOrderSpecifier;
import static com.choimory.itemvaluechecker.api.userapi.member.entity.QMember.member;
import static com.choimory.itemvaluechecker.api.userapi.member.querydsl.expressions.QMemberExpressions.*;

@Repository
@RequiredArgsConstructor
public class QMemberRepositoryImpl implements QMemberRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Member> getMembers(final MemberListRequest param, final Pageable pageable) {
        QueryResults<Member> result = query.select(member)
                .from(member)
                .where(eqId(param),
                        containsName(param),
                        containsEmail(param),
                        eqAuthLevel(param),
                        betweenCreatedAt(param),
                        betweenModifiedAt(param),
                        betweenDeletedAt(param))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                //.orderBy(getOrderSpecifier(pageable))
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
