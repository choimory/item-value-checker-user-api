package com.choimory.itemvaluechecker.api.userapi.member.repository;

import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, String> {
}
