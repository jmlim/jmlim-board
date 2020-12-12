package io.jmlim.springboot.sample.board.app.repo;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jmlim.springboot.sample.board.app.domain.entity.Board;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static io.jmlim.springboot.sample.board.app.domain.entity.QBoard.board;

@Repository
public class BoardRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public BoardRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Board.class);
        this.queryFactory = jpaQueryFactory;
    }

    public List<Board> findBoardConveringIndex(int pageNo, int pageSize) {
        List<Long> ids = queryFactory
                .select(board.id)
                .from(board)
                .orderBy(board.id.desc())
                .limit(pageSize)
                .offset(pageNo * pageSize)
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        // Projections.field : select 절에서 조회대상 지정.
        // https://icarus8050.tistory.com/5
        return queryFactory.select(Projections.fields(Board.class,
                    board.id,
                    board.writer,
                    board.title))
                .from(board)
                .where(board.id.in(ids))
                .fetch();

    }
}
