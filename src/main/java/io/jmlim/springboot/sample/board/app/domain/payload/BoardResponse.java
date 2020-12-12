package io.jmlim.springboot.sample.board.app.domain.payload;

import io.jmlim.springboot.sample.board.app.domain.entity.Board;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"content"})
@EqualsAndHashCode(of = {"id"})
public class BoardResponse {
    long id;
    String writer;
    String title;
    String content;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.writer = board.getWriter();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdDate = board.getCreatedDate();
        this.updatedDate = board.getUpdatedDate();
    }
}
