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
        //임시 - 일단 전체 변환하는 방법
        /*select(Projections.fields(MemberDto.class, member))
                .from(member)
                .innerJoin(member.memberAuthority, memberAuthority)
                .innerJoin(member.memberSocials, memberSocial).fetchJoin()
                .where(gtId(lastId),
                        eqIdentity(identity),
                        containsNickname(nickname),
                        containsEmail(email),
                        eqAuthLevel(authLevel),
                        betweenCreatedAt(createdFrom, createdTo),
                        betweenModifiedAt(modifiedFrom, modifiedTo),
                        betweenDeletedAt(deletedFrom, deletedTo))
                .limit(size)
                .fetch();*/

        //삽질 1. memberDto의 1:1 객체인 memberAuthority까지만 dto 안에 넣고 List<MemberSocialDto>은 DTO 바깥으로 따로 빼서 일단 튜플(dto + list)로 반환하는데까지 확인
        /*query.select(Projections.fields(MemberDto.class,
                member.identity,
                Projections.fields(MemberDto.MemberAuthorityDto.class,
                        memberAuthority.authLevel).as("memberAuthority")
                ),
                Projections.list(
                        memberSocial.socialType,
                        memberSocial.socialId)
        )
                .from(member)
                .innerJoin(member.memberAuthority, memberAuthority)
                .innerJoin(member.memberSocials, memberSocial)
                .where(gtId(lastId),
                        eqIdentity(identity),
                        containsNickname(nickname),
                        containsEmail(email),
                        eqAuthLevel(authLevel),
                        betweenCreatedAt(createdFrom, createdTo),
                        betweenModifiedAt(modifiedFrom, modifiedTo),
                        betweenDeletedAt(deletedFrom, deletedTo))
                .limit(size)
                .fetch();*/

        /*정답 찾음.*/
        // 1. memberDto의 필드들과 1:1 객체들은 projections.fields()로 MeberDto로 바로 변환, List<MemberSocialDto>는 Projections.list()로 dto 바깥으로 빼서 별도의 튜플 객체를 만든 뒤 (삽질1)
        // 2. querydsl의 .transform(GroupBy.groupBy().list(GroupBy.list()).as())로 result aggregation하여 1:N 객체도 Member dto에 최종 변환하여 반환
        // 3. 페이징 처리는 힘듦


        return query.select(Projections.fields(MemberDto.class,
                member.id, member.identity, member.nickname,
                Projections.fields(MemberDto.MemberAuthorityDto.class,
                        memberAuthority.authLevel).as("memberAuthority")
                ),
                Projections.list(
                        memberSocial.socialType, memberSocial.socialId)
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
                .leftJoin(member.memberSocials, memberSocial).fetchJoin()
                .transform(GroupBy.groupBy(member.identity)
                        .list(Projections.fields(MemberDto.class, member.id, member.identity, member.nickname,
                                Projections.fields(MemberDto.MemberAuthorityDto.class, memberAuthority.authLevel).as("memberAuthority"),
                                GroupBy.list(Projections.fields(MemberDto.MemberSocialDto.class, memberSocial.socialType, memberSocial.socialId)).as("memberSocials"))));



    }
}
