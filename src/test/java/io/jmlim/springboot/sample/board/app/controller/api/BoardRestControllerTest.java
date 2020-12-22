package io.jmlim.springboot.sample.board.app.controller.api;

import io.jmlim.springboot.sample.board.app.common.BaseControllerTest;
import io.jmlim.springboot.sample.board.app.domain.payload.BoardSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardRestControllerTest extends BaseControllerTest {

    @Test
    @DisplayName("생성")
    void create() throws Exception {
        BoardSaveRequest boardSaveRequest = BoardSaveRequest.builder().writer("jmlim")
                .title("타이틀112")
                .content("글이써지나?").build();

        this.mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardSaveRequest))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("writer").exists())
                .andExpect(jsonPath("title").exists())
                .andExpect(jsonPath("createdDate").exists())
                .andExpect(jsonPath("updatedDate").exists());
    }

    @Test
    @DisplayName("목록 가져오기")
    void getAll() throws Exception {
        this.mockMvc.perform(get("/board")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("content[0].id").exists())
                .andExpect(jsonPath("content[0].writer").exists())
                .andExpect(jsonPath("content[0].title").exists())
                .andExpect(jsonPath("content[0].createdDate").exists())
                .andExpect(jsonPath("content[0].updatedDate").exists());
    }

    @Test
    @DisplayName("수정")
    void update() throws Exception {
        BoardSaveRequest boardSaveRequest = BoardSaveRequest.builder().writer("jmlim")
                .title("타이틀수정")
                .content("글이써지나?").build();

        this.mockMvc.perform(put("/board/{id}", 1000006)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardSaveRequest))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("writer").exists())
                .andExpect(jsonPath("title").exists())
                .andExpect(jsonPath("createdDate").exists())
                .andExpect(jsonPath("updatedDate").exists());

    }

    @Test
    @DisplayName("삭제")
    void remove() throws Exception {
        this.mockMvc.perform(delete("/board/{id}", 1000006)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}