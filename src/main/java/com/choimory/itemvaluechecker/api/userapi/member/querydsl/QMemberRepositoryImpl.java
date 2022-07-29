package com.choimory.itemvaluechecker.api.userapi.member.querydsl;

import com.choimory.itemvaluechecker.api.userapi.common.querydsl.Querydsl4RepositorySupport;
import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberDto;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.querydsl.core.QueryResults;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.choimory.itemvaluechecker.api.userapi.member.entity.QMember.member;
import static com.choimory.itemvaluechecker.api.userapi.member.entity.QMemberAuthority.memberAuthority;
import static com.choimory.itemvaluechecker.api.userapi.member.entity.QMemberSocial.memberSocial;
import static com.choimory.itemvaluechecker.api.userapi.member.querydsl.expressions.QMemberExpressions.*;

@Repository
public class QMemberRepositoryImpl extends Querydsl4RepositorySupport implements QMemberRepository {
    private final JPAQueryFactory query;

    public QMemberRepositoryImpl(JPAQueryFactory query) {
        super(Member.class);
        this.query = query;
    }

    @Override
    public Page<Member> findAll(Pageable pageable, String identity, String nickname, String email, AuthLevel authLevel, LocalDateTime createdFrom, LocalDateTime createdTo, LocalDateTime modifiedFrom, LocalDateTime modifiedTo, LocalDateTime deletedFrom, LocalDateTime deletedTo) {
        QueryResults<Member> result = getQuerydsl().applyPagination(pageable,
                query.select(member)
                        .from(member)
                        .where(eqIdentity(identity),
                            containsNickname(nickname),
                            containsEmail(email),
                            eqAuthLevel(authLevel),
                            betweenCreatedAt(createdFrom, createdTo),
                            betweenModifiedAt(modifiedFrom, modifiedTo),
                            betweenDeletedAt(deletedFrom, deletedTo)))
                .fetchResults();


        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public List<MemberDto> findAllNoOffset(int lastId, int size, String identity, String nickname, String email, AuthLevel authLevel, LocalDateTime createdFrom, LocalDateTime createdTo, LocalDateTime modifiedFrom, LocalDateTime modifiedTo, LocalDateTime deletedFrom, LocalDateTime deletedTo) {
        return query.select(
                    Projections.fields(MemberDto.class,
                            member.id,
                            member.identity,
                            member.nickname,
                            Projections.fields(MemberDto.MemberAuthorityDto.class,
                                    memberAuthority.authLevel
                            ).as("memberAuthority")
                    ),
                    Projections.list(
                            memberSocial.socialType,
                            memberSocial.socialId
                    )
                )
                .from(member)
                .where(gtId(lastId),
                        eqIdentity(identity),
                        containsNickname(nickname),
                        containsEmail(email),
                        eqAuthLevel(authLevel),
                        betweenCreatedAt(createdFrom, createdTo),
                        betweenModifiedAt(modifiedFrom, modifiedTo),
                        betweenDeletedAt(deletedFrom, deletedTo))
                .innerJoin(member.memberAuthority, memberAuthority)
                .leftJoin(member.memberSocials, memberSocial)
                .transform(GroupBy.groupBy(member.identity)
                        .list(Projections.fields(MemberDto.class, member.id, member.identity, member.nickname,
                                Projections.fields(MemberDto.MemberAuthorityDto.class, memberAuthority.authLevel).as("memberAuthority"),
                                GroupBy.list(Projections.fields(MemberDto.MemberSocialDto.class, memberSocial.socialType, memberSocial.socialId)).as("memberSocials"))));
    }
}
