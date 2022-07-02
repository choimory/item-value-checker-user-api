package com.choimory.itemvaluechecker.api.userapi.common.dto.request;

import com.choimory.itemvaluechecker.api.userapi.member.code.MemberDefaultSort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CommonPageRequestTest {

    @Test
    @DisplayName("Pageable 반환 메소드 테스트")
    void of() {
        /*given*/
        int page = 10;
        int size = 20;
        List<String> props = Arrays.asList("createdAt", "id", "name");
        List<String> directions = Arrays.asList("desc", "desc", "asc");


        List<String> sort = new ArrayList<>();
        sort.add(String.format("%s:%s", props.get(0), directions.get(0)));
        sort.add(String.format("%s:%s", props.get(1), directions.get(1)));
        sort.add(String.format("%s:%s", props.get(2), directions.get(2)));

        CommonPageRequest commonPageRequest = CommonPageRequest.builder()
                .page(page)
                .size(size)
                .sort(sort)
                .build();

        /*when*/
        Pageable pageable = commonPageRequest.of(MemberDefaultSort.VIEWS.getProperty(),
                MemberDefaultSort.VIEWS.getDirection());

        /*then*/
        Assertions.assertThat(pageable.getPageNumber()).isEqualTo(page - 1);
        Assertions.assertThat(pageable.getPageSize()).isEqualTo(size);

        int count = 0;
        for (Sort.Order order : pageable.getSort()) {
            Assertions.assertThat(order.getProperty()).isEqualTo(props.get(count));
            Assertions.assertThat(order.getDirection().name()).isEqualTo(directions.get(count).toUpperCase(Locale.ROOT));
            count++;
        }
    }
}