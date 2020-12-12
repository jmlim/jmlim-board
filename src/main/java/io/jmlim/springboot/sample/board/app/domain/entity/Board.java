package io.jmlim.springboot.sample.board.app.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "jmlim_board_")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "writer", "title"})
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    long id;

    @Column(name = "writer", nullable = false, length = 128)
    String writer;

    @Column(name = "title", nullable = false, length = 512)
    String title;

    @Lob
    @Column(name = "content")
    String content;

    @Builder
    public Board(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    /**
     * entity update
     *
     * @param writer
     * @param title
     * @param content
     */
    public void update(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
