package com.choimory.itemvaluechecker.api.userapi.member.querydsl.expressions;

import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberListRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

import static com.choimory.itemvaluechecker.api.userapi.member.entity.QMember.member;

public class QMemberExpressions {
    public static BooleanExpression eqIdentity(final String identity){
        return StringUtils.hasText(identity)
                ? member.identity.eq(identity)
                : null;
    }

    public static BooleanExpression containsNickname(final String nickname){
        return StringUtils.hasText(nickname)
                ? member.nickname.contains(nickname)
                : null;
    }

    public static BooleanExpression containsEmail(final String email){
        return StringUtils.hasText(email)
                ? member.email.contains(email)
                : null;
    }

    public static BooleanExpression eqAuthLevel(AuthLevel authLevel){
        return authLevel != null
                ? member.memberAuthority.authLevel.eq(authLevel)
                : null;
    }

    public static BooleanExpression betweenCreatedAt(final LocalDateTime createdFrom, final LocalDateTime createdTo){
        /*둘다 없을시*/
        if(createdFrom == null && createdTo == null){
            return null;
        }
        /*둘다 있을시*/
        else if(createdFrom != null && createdTo != null){
            return member.createdAt.goe(createdFrom)
                    .and(member.createdAt.loe(createdTo));
        }
        /*시작일만 있을시*/
        else if(createdFrom != null){
            return member.createdAt.goe(createdFrom);
        }
        /*종료일만 있을시*/
        else {
            return member.createdAt.loe(createdTo);
        }
    }

    public static BooleanExpression betweenModifiedAt(final LocalDateTime modifiedFrom, final LocalDateTime modifiedTo){
        /*둘다 없을시*/
        if(modifiedFrom== null && modifiedTo == null){
            return null;
        }
        /*둘다 있을시*/
        else if(modifiedFrom != null && modifiedTo != null){
            return member.modifiedAt.goe(modifiedFrom)
                    .and(member.modifiedAt.loe(modifiedTo));
        }
        /*시작일만 있을시*/
        else if(modifiedFrom != null){
            return member.modifiedAt.goe(modifiedFrom);
        }
        /*종료일만 있을시*/
        else {
            return member.modifiedAt.loe(modifiedTo);
        }
    }

    public static BooleanExpression betweenDeletedAt(final LocalDateTime deletedFrom, final LocalDateTime deletedTo){
        /*둘다 없을시*/
        if(deletedFrom == null && deletedTo == null){
            return null;
        }
        /*둘다 있을시*/
        else if(deletedFrom != null && deletedTo != null){
            return member.deletedAt.goe(deletedFrom)
                    .and(member.deletedAt.loe(deletedTo));
        }
        /*시작일만 있을시*/
        else if(deletedFrom != null){
            return member.deletedAt.goe(deletedFrom);
        }
        /*종료일만 있을시*/
        else {
            return member.deletedAt.loe(deletedTo);
        }
    }
}
