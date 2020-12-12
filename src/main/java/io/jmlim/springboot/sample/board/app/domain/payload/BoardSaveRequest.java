package io.jmlim.springboot.sample.board.app.domain.payload;

import io.jmlim.springboot.sample.board.app.domain.entity.Board;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class BoardSaveRequest {
    String writer;
    String title;
    String content;

    @Builder
    public BoardSaveRequest(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Board toEntity() {
        return Board.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .build();
    }
}
