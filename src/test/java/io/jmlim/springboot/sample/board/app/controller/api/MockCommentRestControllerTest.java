package io.jmlim.springboot.sample.board.app.controller.api;

import com.google.common.collect.Lists;
import io.jmlim.springboot.sample.board.app.common.BaseControllerTest;
import io.jmlim.springboot.sample.board.app.domain.payload.CommentResponse;
import io.jmlim.springboot.sample.board.app.domain.payload.CommentSaveRequest;
import io.jmlim.springboot.sample.board.app.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MockCommentRestControllerTest extends BaseControllerTest {

    private static final long BOARD_ID = 1;

    @MockBean
    CommentService commentService;

    @Test
    @DisplayName("생성")
    void create() throws Exception {
        //when
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
                .content("글이써지나?").build();
        CommentResponse commentResponse =
                new CommentResponse(1, 1, "글이써지나?", LocalDateTime.now(), LocalDateTime.now());

        // eq() 의 적절한 사용
        // https://github.com/HomoEfficio/dev-tips/blob/master/Mockito%20Stub%20%EC%9E%91%EC%84%B1%20%EC%8B%9C%20%EC%A3%BC%EC%9D%98%20%EC%82%AC%ED%95%AD.md
        System.out.println(commentResponse);
        when(commentService.save(eq(BOARD_ID), any()))
                .thenReturn(commentResponse);

        // then
        this.mockMvc.perform(post("/comment/{boardId}", BOARD_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentSaveRequest))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("createdDate").exists())
                .andExpect(jsonPath("updatedDate").exists())
                .andDo(document("create-comment"));
    }

    @Test
    @DisplayName("목록 가져오기")
    void getAll() throws Exception {

        // {"id":8538,"boardId":1,"content":"댓글 테스트1","createdDate":"2020-12-25T14:44:31.172","updatedDate":"2020-12-25T14:44:31.172"}

        List<CommentResponse> responseList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            responseList.add(new CommentResponse(1 + i, 1 + i, "글이써지나?", LocalDateTime.now(), LocalDateTime.now()));
        }

        Page<CommentResponse> page = new PageImpl<>(responseList, PageRequest.of(1, 10), 100);
        when(commentService.findByBoardId(eq(BOARD_ID), any()))
                .thenReturn(page);


        this.mockMvc.perform(get("/comment/{boardId}", BOARD_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("content[0].id").exists())
                .andExpect(jsonPath("content[0].content").exists())
                .andExpect(jsonPath("content[0].createdDate").exists())
                .andExpect(jsonPath("content[0].updatedDate").exists())
                .andDo(document("get-all-comment"));
    }

    @Test
    @DisplayName("삭제")
    void remove() throws Exception {
        this.mockMvc.perform(delete("/comment/{id}", 10754)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("delete-comment"));
    }
}