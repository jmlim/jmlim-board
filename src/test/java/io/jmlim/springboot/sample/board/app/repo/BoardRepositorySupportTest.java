package io.jmlim.springboot.sample.board.app.repo;

import io.jmlim.springboot.sample.board.app.domain.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardRepositorySupportTest {

    @Autowired
    BoardRepositorySupport boardRepositorySupport;

    @Test
    void findBoardConveringIndex() {
        List<Board> boards =
                boardRepositorySupport.findBoardConveringIndex(99999, 10);
        System.out.println(boards);

        assertThat(boards).hasSize(10);
    }
}