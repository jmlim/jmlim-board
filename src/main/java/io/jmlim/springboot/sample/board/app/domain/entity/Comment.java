package io.jmlim.springboot.sample.board.app.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "jmlim_comment")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "writer"})
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    long id;

    @Column(name = "content", nullable = false, length = 1024)
    String content;

    @ManyToOne
    @JoinColumn(name = "board_id")
    Board board;
}
