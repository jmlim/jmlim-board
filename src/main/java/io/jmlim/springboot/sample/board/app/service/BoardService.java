package io.jmlim.springboot.sample.board.app.service;

import io.jmlim.springboot.sample.board.app.domain.payload.BoardResponse;
import io.jmlim.springboot.sample.board.app.domain.payload.BoardSaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    BoardResponse findById(Long id);

    BoardResponse save(BoardSaveRequest request);

    BoardResponse update(Long id, BoardSaveRequest request);

    void delete(Long id);

    Page<BoardResponse> findByPaging(Pageable pageable);
}
