package com.choimory.itemvaluechecker.api.userapi.user.controller;

import com.choimory.itemvaluechecker.api.userapi.config.SpringRestDocsConfig;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    /*@BeforeEach
    void setUp() {
    }*/

    @Test
    @DisplayName("회원조회 성공 테스트")
    void view() throws Exception {
        /*given*/
        final String id = "choimory";

        /*when*/
        // ResultActions when = mockMvc.perform(RestDocumentationRequestBuilders.get("/user/{id}", id) - MockMvcRequestBuilder.get()이 pathRequest 지원 안하는 버전일시 사용
        ResultActions when = mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        /*then*/
        when.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("status").value(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("user.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("user.password").doesNotExist())
                .andDo(MockMvcResultHandlers.print());
    }

    @ParameterizedTest
    @MethodSource({"argsForViewDynamicTest"})
    @DisplayName("회원조회 성공실패 동적 테스트")
    void viewDynamicTest(final boolean isSuccess, final String id, final HttpStatus httpStatus) throws Exception {
        /*when*/
        // ResultActions when = mockMvc.perform(RestDocumentationRequestBuilders.get("/user/{id}", id) - MockMvcRequestBuilder.get()이 pathRequest 지원 안하는 버전일시 사용
        ResultActions when = mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        /*then*/
        if(isSuccess) {
            when.andExpect(MockMvcResultMatchers.status().is(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("status").value(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("message").value(httpStatus.getReasonPhrase()))
                    .andExpect(MockMvcResultMatchers.jsonPath("user.id").value(id))
                    .andExpect(MockMvcResultMatchers.jsonPath("user.password").doesNotExist());
        } else {
            when.andExpect(MockMvcResultMatchers.status().is(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("status").value(httpStatus.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("message").value(httpStatus.getReasonPhrase()))
                    .andExpect(MockMvcResultMatchers.jsonPath("user").doesNotExist());
        }

        when.andDo(MockMvcResultHandlers.print());
    }

    static Stream<Arguments> argsForViewDynamicTest(){
        return Stream.<Arguments>builder()
                .add(Arguments.arguments(true, "choimory", HttpStatus.OK))
                .add(Arguments.arguments(false, "choimoryy", HttpStatus.NOT_FOUND))
                .build();
    }

    @Test
    void join() {
    }

    void login() {
    }

    void logout() {
    }

    void update(){
    }

    void ban(){
    }
}