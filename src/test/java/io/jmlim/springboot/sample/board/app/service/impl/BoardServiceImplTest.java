package io.jmlim.springboot.sample.board.app.service.impl;

import io.jmlim.springboot.sample.board.app.domain.payload.BoardResponse;
import io.jmlim.springboot.sample.board.app.domain.payload.BoardSaveRequest;
import io.jmlim.springboot.sample.board.app.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    BoardService boardService;

    @Test
    void findById() {
        BoardResponse boardResponse = boardService.findById(29l);
        System.out.println(boardResponse);
        long id = boardResponse.getId();
        assertThat(id).isEqualTo(29);
    }

    @Test
    void save_update_delete() {
        BoardResponse save = boardService.save(BoardSaveRequest.builder().writer("jmlim")
                .title("타이틀")
                .content("글글긅ㅌㅌㅌㅌㅌㅌㅌㅌㅌㅌㅌㅌㅌ").build());
        System.out.println(save);
        BoardResponse update = boardService.update(save.getId(), BoardSaveRequest.builder().writer("jmlim")
                .title("타이틀 업데이트")
                .content("글글긅ㅌㅌㅌㅌㅌㅌㅌㅌㅌㅌㅌㅌㅌ").build());
        boardService.delete(update.getId());
    }


    @Test
    void findByPaging() {
        Pageable pageable = PageRequest.of(99999, 10);
        Page<BoardResponse> page = boardService.findByPaging(pageable);

        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        int number = page.getNumber();
        List<BoardResponse> boards = page.getContent();

        System.out.println(totalPages);
        System.out.println(totalElements);
        System.out.println(number);
        System.out.println(boards);

        assertThat(totalPages).isEqualTo(100001);
        assertThat(boards).hasSize(10);
    }
}