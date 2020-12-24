package io.jmlim.springboot.sample.board.app.service.impl;

import io.jmlim.springboot.sample.board.app.domain.entity.Board;
import io.jmlim.springboot.sample.board.app.domain.payload.BoardResponse;
import io.jmlim.springboot.sample.board.app.domain.payload.BoardSaveRequest;
import io.jmlim.springboot.sample.board.app.repo.BoardRepository;
import io.jmlim.springboot.sample.board.app.repo.BoardRepositorySupport;
import io.jmlim.springboot.sample.board.app.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final BoardRepositorySupport boardRepositorySupport;

    @Override
    public BoardResponse findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new BoardResponse(board);
    }

    @Transactional
    @Override
    public BoardResponse save(BoardSaveRequest request) {
        Board save = boardRepository.save(request.toEntity());
        return new BoardResponse(save);
    }

    @Transactional
    @Override
    public BoardResponse update(Long id, BoardSaveRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        board.update(request.getWriter(), request.getTitle(), request.getContent());
        return new BoardResponse(board);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        boardRepository.delete(board);
    }

    @Override
    public Page<BoardResponse> findByPaging(Pageable pageable) {
        List<BoardResponse> boardResponses = boardRepositorySupport.findByPagingConveringIndex(pageable);
        long totalCount = boardRepositorySupport.findTotalCount();
        Page<BoardResponse> page = new PageImpl<>(boardResponses, pageable, totalCount);
        return page;
    }
}
