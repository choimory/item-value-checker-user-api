package com.choimory.itemvaluechecker.api.userapi.member.controller;

import com.choimory.itemvaluechecker.api.userapi.config.SpringRestDocsConfig;
import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberJoinRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    @DisplayName("회원조회 성공 테스트")
    void view() throws Exception {
        /*given*/
        final String id = "choimory";

        /*when*/
        ResultActions when = mockMvc.perform(RestDocumentationRequestBuilders.get("/member/{id}", id)
        //ResultActions when = mockMvc.perform(MockMvcRequestBuilders.get("/member/{id}", id) // -> MockMvcRequestBuilder.get()이 pathRequest 지원하는 버전일시 사용
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        /*then*/
        when.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("status").value(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("member.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("member.password").doesNotExist())
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("get-member-id",
                        HeaderDocumentation.requestHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.ACCEPT).description("요청 헤더"),
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 형식")
                        ),
                        RequestDocumentation.relaxedPathParameters(
                                RequestDocumentation.parameterWithName("id").description("회원 아이디")
                        ),
                        HeaderDocumentation.responseHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 형식")
                        ),
                        PayloadDocumentation.relaxedResponseFields(
                                PayloadDocumentation.fieldWithPath("status").description("API 결과"),
                                PayloadDocumentation.fieldWithPath("message").description("API 결과 메시지"),
                                PayloadDocumentation.fieldWithPath("member").description("유저 정보"),
                                PayloadDocumentation.fieldWithPath("member.id").description("ID"),
                                PayloadDocumentation.fieldWithPath("member.name").description("이름"),
                                PayloadDocumentation.fieldWithPath("member.email").description("이메일"),
                                PayloadDocumentation.fieldWithPath("member.createdAt").description("가입일"),
                                PayloadDocumentation.fieldWithPath("member.modifiedAt").description("수정일"),
                                PayloadDocumentation.fieldWithPath("member.deletedAt").description("탈퇴일"),
                                PayloadDocumentation.fieldWithPath("member.authLevel").description("권한"),
                                PayloadDocumentation.fieldWithPath("member.socials").description("소셜 아이디"),
                                PayloadDocumentation.fieldWithPath("member.suspensions").description("정지 일자"),
                                PayloadDocumentation.fieldWithPath("_links").description("HATEOAS"),
                                PayloadDocumentation.fieldWithPath("_links.self").description("해당 API 요청정보"),
                                PayloadDocumentation.fieldWithPath("_links.self.href").description("해당 API 요청주소")
                        )
                ));
    }

    @ParameterizedTest
    @MethodSource({"viewMethodSource"})
    @DisplayName("회원조회 성공실패 동적 테스트")
    void viewDynamicTest(final boolean isSuccess, final String id, final HttpStatus httpStatus) throws Exception {
        /*when*/
        // ResultActions when = mockMvc.perform(RestDocumentationRequestBuilders.get("/member/{id}", id) - MockMvcRequestBuilder.get()이 pathRequest 지원 안하는 버전일시 사용
        ResultActions when = mockMvc.perform(MockMvcRequestBuilders.get("/member/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        /*then*/
        if(isSuccess) {
            when.andExpect(MockMvcResultMatchers.status().is(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("status").value(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("message").value(httpStatus.getReasonPhrase()))
                    .andExpect(MockMvcResultMatchers.jsonPath("member.id").value(id))
                    .andExpect(MockMvcResultMatchers.jsonPath("member.password").doesNotExist());
        } else {
            when.andExpect(MockMvcResultMatchers.status().is(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("status").value(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("message").value(httpStatus.getReasonPhrase()))
                    .andExpect(MockMvcResultMatchers.jsonPath("user").doesNotExist());
        }

        when.andDo(MockMvcResultHandlers.print());
    }

    void views() {
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void join() throws Exception {
        /*given*/
        MemberJoinRequest request = MemberJoinRequest.builder()
                .id("choimory")
                .password("asdqwe123")
                .name("중윤최")
                .email("choimory@naver.com")
                .authLevel(AuthLevel.MEMBER)
                .build();

        /*when*/
        ResultActions when = mockMvc.perform(MockMvcRequestBuilders.put("/member/join")
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
                                PayloadDocumentation.fieldWithPath("id").description("아이디"),
                                PayloadDocumentation.fieldWithPath("password").description("비밀번호"),
                                PayloadDocumentation.fieldWithPath("name").description("이름"),
                                PayloadDocumentation.fieldWithPath("email").description("이메일"),
                                PayloadDocumentation.fieldWithPath("authLevel").description("권한")
                        ),
                        HeaderDocumentation.responseHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 형식")
                        ),
                        PayloadDocumentation.relaxedResponseFields(
                                PayloadDocumentation.fieldWithPath("status").description("결과 코드"),
                                PayloadDocumentation.fieldWithPath("message").description("결과 메시지"),
                                PayloadDocumentation.fieldWithPath("_links").description("HATEOAS"),
                                PayloadDocumentation.fieldWithPath("_links.self").description("요청 API 정보"),
                                PayloadDocumentation.fieldWithPath("_links.self.href").description("요청 API 주소"),
                                PayloadDocumentation.fieldWithPath("_links.view-id").description("회원 조회 API 정보"),
                                PayloadDocumentation.fieldWithPath("_links.view-id.href").description("현재 가입한 아이디의 조회 API 주소")
                        )
                ));
    }

    void joinDynamicTest(){

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

}