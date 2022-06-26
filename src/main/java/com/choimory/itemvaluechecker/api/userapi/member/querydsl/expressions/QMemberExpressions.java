package com.choimory.itemvaluechecker.api.userapi.member.querydsl.expressions;

import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberListRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

import static com.choimory.itemvaluechecker.api.userapi.member.entity.QMember.member;

public class QMemberExpressions {
    public static BooleanExpression eqMemberId(final MemberListRequest param){
        return param != null && StringUtils.hasText(param.getMemberId())
                ? member.memberId.eq(param.getMemberId())
                : null;
    }

    public static BooleanExpression containsNickname(final MemberListRequest param){
        return param != null && StringUtils.hasText(param.getNickname())
                ? member.nickname.contains(param.getNickname())
                : null;
    }

    public static BooleanExpression containsEmail(final MemberListRequest param){
        return param != null && StringUtils.hasText(param.getEmail())
                ? member.email.contains(param.getEmail())
                : null;
    }

    public static BooleanExpression eqAuthLevel(final MemberListRequest param){
        return param != null && param.getAuthLevel() != null
                ? member.memberAuthority.authLevel.eq(param.getAuthLevel())
                : null;
    }

    public static BooleanExpression betweenCreatedAt(final MemberListRequest param){
        /*둘다 없을시*/
        if(param == null || (param.getCreatedFrom() == null && param.getCreatedTo() == null)){
            return null;
        }
        /*둘다 있을시*/
        else if(param.getCreatedFrom() != null && param.getCreatedTo() != null){
            return member.createdAt.goe(param.getCreatedFrom())
                    .and(member.createdAt.loe(param.getCreatedTo()));
        }
        /*시작일만 있을시*/
        else if(param.getCreatedFrom() != null && param.getCreatedTo() == null){
            return member.createdAt.goe(param.getCreatedFrom());
        }
        /*종료일만 있을시*/
        else if(param.getCreatedFrom() == null && param.getCreatedTo() != null){
            return member.createdAt.loe(param.getCreatedTo());
        }

        return null;
    }

    public static BooleanExpression betweenModifiedAt(final MemberListRequest param){
        /*둘다 없을시*/
        if(param == null || (param.getModifiedFrom() == null && param.getModifiedTo() == null)){
            return null;
        }
        /*둘다 있을시*/
        else if(param.getModifiedFrom() != null && param.getModifiedTo() != null){
            return member.modifiedAt.goe(param.getModifiedFrom())
                    .and(member.modifiedAt.loe(param.getModifiedTo()));
        }
        /*시작일만 있을시*/
        else if(param.getModifiedFrom() != null && param.getModifiedTo() == null){
            return member.modifiedAt.goe(param.getModifiedFrom());
        }
        /*종료일만 있을시*/
        else if(param.getModifiedFrom() == null && param.getModifiedTo() != null){
            return member.modifiedAt.loe(param.getModifiedTo());
        }

        return null;
    }

    public static BooleanExpression betweenDeletedAt(final MemberListRequest param){
        /*둘다 없을시*/
        if(param == null || (param.getDeletedFrom() == null && param.getDeletedTo() == null)){
            return null;
        }
        /*둘다 있을시*/
        else if(param.getDeletedFrom() != null && param.getDeletedTo() != null){
            return member.deletedAt.goe(param.getDeletedFrom())
                    .and(member.deletedAt.loe(param.getDeletedTo()));
        }
        /*시작일만 있을시*/
        else if(param.getDeletedFrom() != null && param.getDeletedTo() == null){
            return member.deletedAt.goe(param.getDeletedFrom());
        }
        /*종료일만 있을시*/
        else if(param.getDeletedFrom() == null && param.getDeletedTo() != null){
            return member.deletedAt.loe(param.getDeletedTo());
        }

        return null;
    }
}
