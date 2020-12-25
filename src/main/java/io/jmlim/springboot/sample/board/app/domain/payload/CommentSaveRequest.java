package io.jmlim.springboot.sample.board.app.domain.payload;

import io.jmlim.springboot.sample.board.app.domain.entity.Board;
import io.jmlim.springboot.sample.board.app.domain.entity.Comment;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class CommentSaveRequest {
    String content;

    @Setter
    Board board;

    @Builder
    public CommentSaveRequest(String content) {
        this.content = content;
    }


    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .board(board)
                .build();
    }
}