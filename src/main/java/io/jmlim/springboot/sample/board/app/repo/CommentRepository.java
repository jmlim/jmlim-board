package io.jmlim.springboot.sample.board.app.repo;

import io.jmlim.springboot.sample.board.app.domain.entity.Board;
import io.jmlim.springboot.sample.board.app.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByBoard(Board board, Pageable pageable);

    @Modifying // @Query Annotation으로 작성 된 변경, 삭제 쿼리 메서드를 사용할 때 필요
    @Query("delete from Comment c where c.board.id = :boardId")
    int deleteByBoardId(@Param("boardId") long boardId);
}
