package io.jmlim.springboot.sample.board.app.service.impl;

import io.jmlim.springboot.sample.board.app.domain.payload.CommentResponse;
import io.jmlim.springboot.sample.board.app.domain.payload.CommentSaveRequest;
import io.jmlim.springboot.sample.board.app.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    CommentService commentService;

    private static final long BOARD_ID = 1000011;

    @Test
    void save() {
        CommentSaveRequest request1 = CommentSaveRequest.builder()
                .content("댓글 테스트1")
                .build();
        CommentResponse save1 = commentService.save(BOARD_ID, request1);

        CommentSaveRequest request2 = CommentSaveRequest.builder()
                .content("댓글 테스트2")
                .build();
        CommentResponse save2 = commentService.save(BOARD_ID, request2);

        assertThat(save2.getBoardId()).isEqualTo(BOARD_ID);
        assertThat(save2.getContent()).isEqualTo("댓글 테스트2");

        commentService.delete(save1.getId());
        commentService.delete(save2.getId());

        for (int i = 0; i < 100; i++) {
            CommentSaveRequest request = CommentSaveRequest.builder()
                    .content("댓글 테스트" + (i + 1))
                    .build();
            commentService.save(BOARD_ID, request);
        }
    }

    @Test
    void findByBoardId() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CommentResponse> commentResponses
                = commentService.findByBoardId(BOARD_ID, pageable);

        int totalPages = commentResponses.getTotalPages();
        long totalElements = commentResponses.getTotalElements();
        int number = commentResponses.getNumber();
        List<CommentResponse> responses = commentResponses.getContent();

        System.out.println(totalPages);
        System.out.println(totalElements);
        System.out.println(number);
        System.out.println(responses);

        assertThat(responses).hasSize(10);
    }

    @Test
    void delete() {
        // commentService.delete(1l);
    }

    @Test
    void deleteByBoardId() {
        commentService.deleteByBoardId(BOARD_ID);
    }
}