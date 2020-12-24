package io.jmlim.springboot.sample.board.app.repo;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jmlim.springboot.sample.board.app.domain.entity.Board;
import io.jmlim.springboot.sample.board.app.domain.payload.BoardResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
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

    public long findTotalCount() {
        long totalCount = queryFactory
                .select(board.id)
                .from(board)
                .where(board.createdDate.after(LocalDateTime.of(2020, 01, 01, 00, 00, 00)))
                .fetchCount();
        return totalCount;
    }

    public List<BoardResponse> findByPagingConveringIndex(Pageable pageable) {
        List<Long> ids = queryFactory
                .select(board.id)
                .from(board)
                .where(board.createdDate
                        .after(LocalDateTime.of(2020, 01, 01, 00, 00, 00)))
                .orderBy(board.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        // Projections.field : select 절에서 조회대상 지정.
        // https://icarus8050.tistory.com/5
        return queryFactory.select(Projections.fields(BoardResponse.class,
                board.id,
                board.writer,
                board.title,
                board.createdDate,
                board.updatedDate))
                .from(board)
                .where(board.id.in(ids))
                .orderBy(board.id.desc())
                .fetch();
    }
}
