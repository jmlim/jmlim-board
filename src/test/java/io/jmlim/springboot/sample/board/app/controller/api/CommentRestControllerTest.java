package io.jmlim.springboot.sample.board.app.controller.api;

import io.jmlim.springboot.sample.board.app.common.BaseControllerTest;
import io.jmlim.springboot.sample.board.app.domain.payload.CommentSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentRestControllerTest extends BaseControllerTest {

    private static final long BOARD_ID = 1;

    @Test
    @DisplayName("생성")
    void create() throws Exception {
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
                .content("글이써지나?").build();

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