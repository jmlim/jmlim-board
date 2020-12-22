package io.jmlim.springboot.sample.board.app.controller.api;

import io.jmlim.springboot.sample.board.app.domain.payload.BoardResponse;
import io.jmlim.springboot.sample.board.app.domain.payload.BoardSaveRequest;
import io.jmlim.springboot.sample.board.app.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardRestController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponse> create(@RequestBody BoardSaveRequest saveRequest) {
        // TODO: @Valid
        BoardResponse response = boardService.save(saveRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<BoardResponse>> getAll(@PageableDefault(page = 1, size = 10) Pageable pageable) {
        return ResponseEntity.ok(boardService.findByPaging(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> update(@PathVariable long id, @RequestBody BoardSaveRequest saveRequest) {
        BoardResponse response = boardService.update(id, saveRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        boardService.delete(id);
        return ResponseEntity.ok(id);
    }
}
