package io.jmlim.springboot.sample.board.app.controller.api;

import io.jmlim.springboot.sample.board.app.domain.payload.BoardResponse;
import io.jmlim.springboot.sample.board.app.domain.payload.BoardSaveRequest;
import io.jmlim.springboot.sample.board.app.domain.payload.CommentResponse;
import io.jmlim.springboot.sample.board.app.domain.payload.CommentSaveRequest;
import io.jmlim.springboot.sample.board.app.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public ResponseEntity<CommentResponse> create(@PathVariable long boardId,
                                                  @RequestBody CommentSaveRequest saveRequest) {
        // TODO: @Valid
        CommentResponse response = commentService.save(boardId, saveRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<Page<CommentResponse>> getAll(@PathVariable long boardId,
                                                        @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(commentService.findByBoardId(boardId, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        commentService.delete(id);
        return ResponseEntity.ok(id);
    }
}
