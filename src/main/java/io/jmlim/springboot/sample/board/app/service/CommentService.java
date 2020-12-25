package io.jmlim.springboot.sample.board.app.service;

import io.jmlim.springboot.sample.board.app.domain.payload.CommentResponse;
import io.jmlim.springboot.sample.board.app.domain.payload.CommentSaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<CommentResponse> findByBoardId(long boardId, Pageable pageable);

    CommentResponse save(long boardId, CommentSaveRequest request);

    void delete(Long id);

    int deleteByBoardId(long boardId);
}
