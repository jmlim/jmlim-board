package io.jmlim.springboot.sample.board.app.service.impl;

import io.jmlim.springboot.sample.board.app.domain.entity.Board;
import io.jmlim.springboot.sample.board.app.domain.entity.Comment;
import io.jmlim.springboot.sample.board.app.domain.payload.CommentResponse;
import io.jmlim.springboot.sample.board.app.domain.payload.CommentSaveRequest;
import io.jmlim.springboot.sample.board.app.repo.BoardRepository;
import io.jmlim.springboot.sample.board.app.repo.CommentRepository;
import io.jmlim.springboot.sample.board.app.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Override
    public Page<CommentResponse> findByBoardId(long boardId, Pageable pageable) {
        Board board = this.getBoard(boardId);
        Page<CommentResponse> commentResponses
                = commentRepository.findByBoard(board, pageable)
                .map(CommentResponse::new);
        return commentResponses;
    }

    @Transactional
    @Override
    public CommentResponse save(long boardId, CommentSaveRequest request) {
        Board board = this.getBoard(boardId);
        request.setBoard(board);

        log.debug("----> board: {}", board);

        Comment savedComment = commentRepository.save(request.toEntity());
        return new CommentResponse(savedComment);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id = " + id));
        commentRepository.delete(comment);
    }

    @Transactional
    @Override
    public int deleteByBoardId(long boardId) {
        int count = commentRepository.deleteByBoardId(boardId);
        log.debug("----> delete count : {}", count);
        return count;
    }

    private Board getBoard(long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + boardId));
        return board;
    }
}
