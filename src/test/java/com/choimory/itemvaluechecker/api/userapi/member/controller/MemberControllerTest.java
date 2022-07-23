package com.choimory.itemvaluechecker.api.userapi.member.controller;

import com.choimory.itemvaluechecker.api.userapi.config.SpringRestDocsConfig;
import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberJoinRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import({SpringRestDocsConfig.class})
@ActiveProfiles({"dev", "local"})
class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    /*
    응답값 인코딩 문제 발생시
    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }*/

    @Test
    @DisplayName("회원 단일조회 테스트")
    void view() throws Exception {
        /*given*/
        final String identity = "choimory";

        /*when*/
        ResultActions when = mockMvc.perform(RestDocumentationRequestBuilders.get("/member/{identity}", identity)
        //ResultActions when = mockMvc.perform(MockMvcRequestBuilders.get("/member/{id}", id) // -> MockMvcRequestBuilder.get()이 pathRequest 지원하는 버전일시 사용
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        /*then*/
        when.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("status").value(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("member.identity").value(identity))
                .andExpect(MockMvcResultMatchers.jsonPath("member.password").doesNotExist())
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("get-member-identity",
                        HeaderDocumentation.requestHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.ACCEPT).description("요청 헤더"),
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 형식")
                        ),
                        RequestDocumentation.relaxedPathParameters(
                                RequestDocumentation.parameterWithName("identity").description("회원 아이디")
                        ),
                        HeaderDocumentation.responseHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 형식")
                        ),
                        PayloadDocumentation.relaxedResponseFields(
                                PayloadDocumentation.fieldWithPath("member").type(JsonFieldType.OBJECT).description("유저 정보").optional(),
                                PayloadDocumentation.fieldWithPath("member.identity").type(JsonFieldType.STRING).description("ID").optional(),
                                PayloadDocumentation.fieldWithPath("member.nickname").type(JsonFieldType.STRING).description("이름").optional(),
                                PayloadDocumentation.fieldWithPath("member.email").type(JsonFieldType.STRING).description("이메일").optional(),
                                PayloadDocumentation.fieldWithPath("member.createdAt").type(JsonFieldType.STRING).description("가입일").optional(),
                                PayloadDocumentation.fieldWithPath("member.modifiedAt").type(JsonFieldType.STRING).description("수정일").optional(),
                                PayloadDocumentation.fieldWithPath("member.deletedAt").type(JsonFieldType.STRING).description("탈퇴일").optional(),
                                PayloadDocumentation.fieldWithPath("member.memberAuthority").type(JsonFieldType.OBJECT).description("권한").optional(),
                                PayloadDocumentation.fieldWithPath("member.memberSocials[]").type(JsonFieldType.ARRAY).description("소셜 아이디").optional(),
                                PayloadDocumentation.fieldWithPath("member.memberSuspensions[]").type(JsonFieldType.ARRAY).description("정지 일자").optional(),
                                PayloadDocumentation.fieldWithPath("_links").type(JsonFieldType.OBJECT).description("HATEOAS").optional(),
                                PayloadDocumentation.fieldWithPath("_links.self").type(JsonFieldType.OBJECT).description("해당 API 요청정보").optional(),
                                PayloadDocumentation.fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("해당 API 요청주소").optional()
                        )
                ));
    }

    @ParameterizedTest
    @MethodSource({"viewMethodSource"})
    @DisplayName("회원 단일조회 동적 테스트")
    void viewParameterizedTest(final boolean isSuccess, final String identity, final HttpStatus httpStatus) throws Exception {
        /*when*/
        // ResultActions when = mockMvc.perform(RestDocumentationRequestBuilders.get("/member/{identity}", identity) - MockMvcRequestBuilder.get()이 pathRequest 지원 안하는 버전일시 사용
        ResultActions when = mockMvc.perform(MockMvcRequestBuilders.get("/member/{identity}", identity)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        /*then*/
        if(isSuccess) {
            when.andExpect(MockMvcResultMatchers.status().is(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("status").value(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("message").value(httpStatus.getReasonPhrase()))
                    .andExpect(MockMvcResultMatchers.jsonPath("member.identity").value(identity))
                    .andExpect(MockMvcResultMatchers.jsonPath("member.password").doesNotExist());
        } else {
            when.andExpect(MockMvcResultMatchers.status().is(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("status").value(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("message").value(httpStatus.getReasonPhrase()))
                    .andExpect(MockMvcResultMatchers.jsonPath("user").doesNotExist());
        }

        when.andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원 목록조회 테스트")
    void views() throws Exception {
        /*given*/
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        String page = "1";
        String size = "20";
        String sort = "createdAt:desc,id:asc";
        String identity = "choimory";
        String nickname = "중윤최";
        String email = "choimory";
        String authLevel = "MEMBER";
        String createdFrom = "1980-01-01T00:00:00";
        String createdTo = "2020-01-01T00:00:00";
        String modifiedFrom = "1980-01-01T00:00:00";
        String modifiedTo = "2020-01-01T00:00:00";

        param.add("page", page);
        param.add("size", size);
        param.add("sort", sort);
        param.add("idName", identity);
        param.add("nickname", nickname);
        param.add("email", email);
        param.add("authLevel", authLevel);
        param.add("createdFrom", createdFrom);
        param.add("createdTo", createdTo);
        param.add("modifiedFrom", modifiedFrom);
        param.add("modifiedTo", modifiedTo);

        /*when*/
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/member")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .params(param));

        /*then*/
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("page").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("size").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("sort").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("members[0].identity").value(identity))
                .andExpect(MockMvcResultMatchers.jsonPath("members[0].nickname", Matchers.containsString(nickname)))
                .andExpect(MockMvcResultMatchers.jsonPath("members[0].email", Matchers.containsString(email)))
                .andExpect(MockMvcResultMatchers.jsonPath("members[0].memberAuthority.authLevel").value(authLevel))
                //.andExpect(MockMvcResultMatchers.jsonPath("members[0].createdAt", Matchers.))
                //.andExpect(MockMvcResultMatchers.jsonPath("members[0].modifiedAt", Matchers.))
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("get-member",
                        HeaderDocumentation.requestHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.ACCEPT).description("요청 헤더"),
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 형식")
                        ),
                        RequestDocumentation.relaxedRequestParameters(
                                RequestDocumentation.parameterWithName("page").description("페이지").optional(),
                                RequestDocumentation.parameterWithName("size").description("사이즈").optional(),
                                RequestDocumentation.parameterWithName("sort").description("정렬정보(prop:direction)").optional(),
                                RequestDocumentation.parameterWithName("idName").description("아이디").optional(),
                                RequestDocumentation.parameterWithName("nickname").description("닉네임").optional(),
                                RequestDocumentation.parameterWithName("email").description("이메일").optional(),
                                RequestDocumentation.parameterWithName("authLevel").description("권한").optional(),
                                RequestDocumentation.parameterWithName("createdFrom").description("가입일 시작범위").optional(),
                                RequestDocumentation.parameterWithName("createdTo").description("가입일 끝범위").optional(),
                                RequestDocumentation.parameterWithName("modifiedFrom").description("수정일 시작범위").optional(),
                                RequestDocumentation.parameterWithName("modifiedTo").description("수정일 끝범위").optional()
                        ),
                        HeaderDocumentation.responseHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 형식")
                        ),
                        PayloadDocumentation.relaxedResponseFields(
                                PayloadDocumentation.fieldWithPath("page").type(JsonFieldType.NUMBER).description("페이지").optional(),
                                PayloadDocumentation.fieldWithPath("size").type(JsonFieldType.NUMBER).description("사이즈").optional(),
                                PayloadDocumentation.fieldWithPath("sort").type(JsonFieldType.STRING).description("정렬정보").optional(),
                                PayloadDocumentation.fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("총 갯수").optional(),
                                PayloadDocumentation.fieldWithPath("totalPage").type(JsonFieldType.NUMBER).description("총 페이지").optional(),
                                PayloadDocumentation.fieldWithPath("members").type(JsonFieldType.ARRAY).description("회원목록").optional(),
                                PayloadDocumentation.fieldWithPath("members[].memberAuthority").type(JsonFieldType.OBJECT).description("권한").optional(),
                                PayloadDocumentation.fieldWithPath("members[].memberSocials[]").type(JsonFieldType.ARRAY).description("SNS 계정정보").optional(),
                                PayloadDocumentation.fieldWithPath("members[].memberSuspensions[]").type(JsonFieldType.ARRAY).description("정지이력").optional(),
                                PayloadDocumentation.fieldWithPath("_links").type(JsonFieldType.OBJECT).description("HATEOAS").optional(),
                                PayloadDocumentation.fieldWithPath("_links.self").type(JsonFieldType.OBJECT).description("요청 API 주소").optional()
                        )));
    }

    //@ParameterizedTest
    //@MethodSource("viewsMethodSource")
    void viewsDynamicTest() throws Exception{
        /*given*/
        /*when*/
        /*then*/
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void join() throws Exception {
        /*given*/
        MemberJoinRequest request = MemberJoinRequest.builder()
                .identity("morychoi")
                .password("Asdqwe123!@#")
                .nickname("morychoi")
                .email("morychoi@naver.com")
                .authLevel(AuthLevel.MEMBER)
                .build();

        /*when*/
        ResultActions when = mockMvc.perform(MockMvcRequestBuilders.put("/member")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        /*then*/
        when.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value(HttpStatus.CREATED.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("_links").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("_links.self.href").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("_links.view-id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("put-member-join",
                        HeaderDocumentation.requestHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.ACCEPT).description("요청 헤더"),
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 형식")
                        ),
                        PayloadDocumentation.relaxedRequestFields(
                                PayloadDocumentation.fieldWithPath("identity").type(JsonFieldType.STRING).description("아이디").optional(),
                                PayloadDocumentation.fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").optional(),
                                PayloadDocumentation.fieldWithPath("nickname").type(JsonFieldType.STRING).description("이름").optional(),
                                PayloadDocumentation.fieldWithPath("email").type(JsonFieldType.STRING).description("이메일").optional(),
                                PayloadDocumentation.fieldWithPath("authLevel").type(JsonFieldType.STRING).description("권한 - MEMBER|ADMIN").optional()
                        ),
                        HeaderDocumentation.responseHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 형식")
                        ),
                        PayloadDocumentation.relaxedResponseFields(
                                PayloadDocumentation.fieldWithPath("status").type(JsonFieldType.NUMBER).description("결과 코드").optional(),
                                PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지").optional(),
                                PayloadDocumentation.fieldWithPath("_links").type(JsonFieldType.OBJECT).description("HATEOAS").optional(),
                                PayloadDocumentation.fieldWithPath("_links.self").type(JsonFieldType.OBJECT).description("요청 API 정보").optional(),
                                PayloadDocumentation.fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("요청 API 주소").optional(),
                                PayloadDocumentation.fieldWithPath("_links.view-id").type(JsonFieldType.OBJECT).description("회원 조회 API 정보").optional(),
                                PayloadDocumentation.fieldWithPath("_links.view-id.href").type(JsonFieldType.STRING).description("현재 가입한 아이디의 조회 API 주소").optional()
                        )
                ));
    }

    void joinParameterizedTest(){

    }

    void login() {
    }

    void logout() {
    }

    void update(){
    }

    void ban(){
    }

    static Stream<Arguments> viewMethodSource(){
        return Stream.<Arguments>builder()
                .add(Arguments.arguments(true, "choimory", HttpStatus.OK))
                .add(Arguments.arguments(false, "choimoryy", HttpStatus.NOT_FOUND))
                .build();
    }

    static Stream<Arguments> viewsMethodSource(){
        return null;
    }
}