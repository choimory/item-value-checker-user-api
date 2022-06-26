package com.choimory.itemvaluechecker.api.userapi.common.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Getter
public class CommonPageRequest {
    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_SIZE = 20;
    private static final String SORT_SEPARATOR = "-";
    private static final String SORT_PATTERN = "prop-direction";
    private final Integer page;
    private final Integer size;
    private final List<String> sort;

    @Builder
    public CommonPageRequest(Integer page, Integer size, List<String> sort) {
        this.page = adjustPage(page);
        this.size = adjustSize(size);
        this.sort = sort;
    }

    public Pageable of(final String defaultProperty, final Sort.Direction defaultDirection){
        return CollectionUtils.isEmpty(sort)
                ? PageRequest.of(page, size)
                : PageRequest.of(page, size, parseSort(defaultProperty, defaultDirection));
    }

    private Integer adjustPage(Integer page){
        return (page == null || page < 0)
                ? DEFAULT_PAGE - 1
                : page - 1;
    }

    private Integer adjustSize(Integer size){
        return (size == null || size < 0)
                ? DEFAULT_SIZE
                : size;
    }

    private Sort parseSort(final String defaultProperty, final Sort.Direction defaultDirection){
        /*sort값이 이상하게 들어올수 있는 경우*/
        //1. -를 안적었다 -> 값 확인후 defaultProperty 혹은 defaultDirection 붙여서 적용
        //2. 프로퍼티만 적었다 -> defaultDirection 적용
        //3. 디렉션만 적었다 ->  defaultProperty 적용
        //4. 프로퍼티와 디렉션 순서를 뒤집어 적었다 -> 뒤에 확인해서 디렉션 종류가 이상하면 그냥 기본으로 적용
        //5. -를 여러개 적었다 -> 기본으로 적용
        //6. -만 적었다 -> 기본으로 적용

        List<Sort.Order> orders = new ArrayList<>();

        /* -로 스플릿 후 값확인*/
        for (String s : sort) {
            List<String> splittedSort = Arrays.asList(s.split(SORT_SEPARATOR));

            /* - 정상일시*/
            if(splittedSort.size() ==  2) {
                orders.add(whenSortNormal(splittedSort, defaultDirection));
            }
            /* prop 혹은 dir만 보냈을시*/
            else if (splittedSort.size() ==  1){
                orders.add(whenSortAbnormal(splittedSort, defaultDirection, defaultProperty));
            }
            /* - 두개 이상이거나 -만 보냈을시 등*/
            else{
                //기본값 적용
                orders.add(new Sort.Order(defaultDirection, defaultProperty));
            }
        }

        return Sort.by(orders);
    }

    private Sort.Order whenSortNormal(List<String> splittedSort, Sort.Direction defaultDirection){
        String value = splittedSort.get(1).toUpperCase(Locale.ROOT);

        //dir 제대로 적었는지 확인하고 제대로 적었으면 해당 Dir 적용, 아니면 기본 dir 적용
        //prop은 검증 제외, 왜냐면 요청별로 유효한 컬럼인지를 다 확인할 방법이 없음.
        if(StringUtils.pathEquals(value, Sort.Direction.ASC.toString())
                || StringUtils.pathEquals(value, Sort.Direction.DESC.toString())){
            return new Sort.Order(Sort.Direction.valueOf(value), splittedSort.get(0));
        } else {
            return new Sort.Order(defaultDirection, splittedSort.get(0));
        }
    }

    private Sort.Order whenSortAbnormal(List<String> splittedSort, Sort.Direction defaultDirection, String defaultProperty){
        String value = splittedSort.get(0).toUpperCase(Locale.ROOT);

        //dir만 보냈는지 prop만 보냈는지 확인 후 안보낸값을 기본값으로 적용
        //dir중 하나라도 일치할시 dir만 보낸거고 아니면 prop만 보낸것
        if(StringUtils.pathEquals(value, Sort.Direction.ASC.toString())
                || StringUtils.pathEquals(value, Sort.Direction.DESC.toString())){
            return new Sort.Order(Sort.Direction.valueOf(value), defaultProperty);
        } else {
            return new Sort.Order(defaultDirection, value);
        }
    }
}
