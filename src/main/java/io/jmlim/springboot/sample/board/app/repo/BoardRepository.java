package io.jmlim.springboot.sample.board.app.repo;

import io.jmlim.springboot.sample.board.app.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
