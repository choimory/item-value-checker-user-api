package com.choimory.itemvaluechecker.api.userapi.member.querydsl;

import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.member.data.dto.MemberDto;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface QMemberRepository {
    Page<Member> findAll(Pageable pageable, String identity, String nickname, String email, AuthLevel authLevel, LocalDateTime createdFrom, LocalDateTime createdTo, LocalDateTime modifiedFrom, LocalDateTime modifiedTo, LocalDateTime deletedFrom, LocalDateTime deletedTo);
    List<MemberDto> findAllNoOffset(int lastId, int size, String identity, String nickname, String email, AuthLevel authLevel, LocalDateTime createdFrom, LocalDateTime createdTo, LocalDateTime modifiedFrom, LocalDateTime modifiedTo, LocalDateTime deletedFrom, LocalDateTime deletedTo);
}
