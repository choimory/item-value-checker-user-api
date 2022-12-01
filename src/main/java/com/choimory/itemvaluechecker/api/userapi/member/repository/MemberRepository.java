package com.choimory.itemvaluechecker.api.userapi.member.repository;

import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.querydsl.QMemberRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, QMemberRepository {
    Optional<Member> findMemberByIdentityEquals(String identity);
    Optional<Member> findMemberByIdentityEqualsAndDeletedAtIsNull(String identity);
    boolean existsByIdentity(String identity);
}
