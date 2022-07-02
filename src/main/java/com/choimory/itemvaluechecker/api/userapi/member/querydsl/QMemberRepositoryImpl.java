package com.choimory.itemvaluechecker.api.userapi.member.querydsl;

import com.choimory.itemvaluechecker.api.userapi.common.querydsl.Querydsl4RepositorySupport;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberListRequest;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.choimory.itemvaluechecker.api.userapi.member.entity.QMember.member;
import static com.choimory.itemvaluechecker.api.userapi.member.querydsl.expressions.QMemberExpressions.*;

@Repository
public class QMemberRepositoryImpl extends Querydsl4RepositorySupport implements QMemberRepository {
    private final JPAQueryFactory query;

    public QMemberRepositoryImpl(JPAQueryFactory query) {
        super(Member.class);
        this.query = query;
    }

    @Override
    public Page<Member> getMembers(final MemberListRequest param, final Pageable pageable) {
        QueryResults<Member> result = getQuerydsl().applyPagination(pageable,
                query.select(member)
                        .from(member)
                        .where(eqIdName(param),
                            containsNickname(param),
                            containsEmail(param),
                            eqAuthLevel(param),
                            betweenCreatedAt(param),
                            betweenModifiedAt(param),
                            betweenDeletedAt(param)))
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
