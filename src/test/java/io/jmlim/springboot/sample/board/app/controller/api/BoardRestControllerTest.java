package io.jmlim.springboot.sample.board.app.controller.api;

import com.google.common.collect.Lists;
import io.jmlim.springboot.sample.board.app.common.BaseControllerTest;
import io.jmlim.springboot.sample.board.app.domain.payload.BoardResponse;
import io.jmlim.springboot.sample.board.app.domain.payload.BoardSaveRequest;
import io.jmlim.springboot.sample.board.app.service.BoardService;
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
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardRestControllerTest extends BaseControllerTest {

    @MockBean
    BoardService boardService;

    @Test
    @DisplayName("생성")
    void create() throws Exception {
        BoardSaveRequest boardSaveRequest = BoardSaveRequest.builder().writer("jmlim")
                .title("타이틀112")
                .content("글이써지나?").build();

        when(boardService.save(any()))
                .thenReturn(new BoardResponse(1l, "jmlim", "타이틀112", "글이써지나?",
                        LocalDateTime.now(), LocalDateTime.now()));

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
                .andExpect(jsonPath("updatedDate").exists())
                .andDo(document("create-board"));
    }

    @Test
    @DisplayName("목록 가져오기")
    void getAll() throws Exception {
        List<BoardResponse> responseList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            responseList.add(new BoardResponse(1 + i, "jmlim" + i, "타이틀" + i, "글이써지나?",
                    LocalDateTime.now(), LocalDateTime.now()));
        }

        Page<BoardResponse> page = new PageImpl<>(responseList, PageRequest.of(1, 10), 100);
        when(boardService.findByPaging(any()))
                .thenReturn(page);


        this.mockMvc.perform(get("/board")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("content[0].id").exists())
                .andExpect(jsonPath("content[0].writer").exists())
                .andExpect(jsonPath("content[0].title").exists())
                .andExpect(jsonPath("content[0].createdDate").exists())
                .andExpect(jsonPath("content[0].updatedDate").exists())
                .andDo(document("get-all-board"));
    }

    @Test
    @DisplayName("수정")
    void update() throws Exception {
        BoardSaveRequest boardSaveRequest = BoardSaveRequest.builder().writer("jmlim")
                .title("타이틀수정")
                .content("글이써지나?").build();

        when(boardService.update(any(), any()))
                .thenReturn(new BoardResponse(1l, "jmlim", "타이틀수정", "글이써지나?",
                        LocalDateTime.now(), LocalDateTime.now()));

        this.mockMvc.perform(put("/board/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardSaveRequest))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("writer").exists())
                .andExpect(jsonPath("title").exists())
                .andExpect(jsonPath("createdDate").exists())
                .andExpect(jsonPath("updatedDate").exists())
                .andDo(document("update-board"));

    }

    @Test
    @DisplayName("삭제")
    void remove() throws Exception {
        this.mockMvc.perform(delete("/board/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("delete-board"));
    }
}