package io.jmlim.springboot.sample.board.app.repo;

import io.jmlim.springboot.sample.board.app.domain.payload.BoardResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardRepositorySupportTest {

    @Autowired
    BoardRepositorySupport boardRepositorySupport;

    @Test
    void findByPagingConveringIndex() {
        Pageable pageable = PageRequest.of(99999, 10);
        List<BoardResponse> boards =
                boardRepositorySupport.findByPagingConveringIndex(pageable);
        System.out.println(boards);

        assertThat(boards).hasSize(10);
    }

    @Test
    void findTotalCount() {
        long totalCount = boardRepositorySupport.findTotalCount();
        System.out.println(totalCount);

        // assertThat(totalCount).isEqualTo(1000000);
    }
}