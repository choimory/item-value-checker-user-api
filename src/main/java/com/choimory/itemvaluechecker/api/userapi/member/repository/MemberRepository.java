package com.choimory.itemvaluechecker.api.userapi.member.repository;

import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.querydsl.QMemberRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member, String>, QMemberRepository {
    Optional<Member> findMemberByMemberIdEquals(final String memberId);
    boolean existsByMemberId(final String memberId);
}
