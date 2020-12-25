package io.jmlim.springboot.sample.board.app.domain.payload;

import io.jmlim.springboot.sample.board.app.domain.entity.Comment;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class CommentResponse {
    long id;
    long boardId;
    String content;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.boardId = comment.getBoard().getId();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.updatedDate = comment.getUpdatedDate();
    }
}
