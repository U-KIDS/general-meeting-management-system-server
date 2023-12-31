package io.ukids.generalmeetingmanagementsystem.domain.vote;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.ukids.generalmeetingmanagementsystem.common.exception.BaseException;
import io.ukids.generalmeetingmanagementsystem.common.exception.ErrorCode;
import io.ukids.generalmeetingmanagementsystem.domain.agenda.Agenda;
import io.ukids.generalmeetingmanagementsystem.domain.vote.enums.VoteValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static io.ukids.generalmeetingmanagementsystem.domain.vote.QVote.vote;

@Repository
@RequiredArgsConstructor
public class VoteQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Vote> findDynamicQueryVote(Agenda agenda, VoteSearchCondition condition, Pageable pageable) {

        return jpaQueryFactory
                .selectFrom(vote)
                .where(
                        eqAgenda(agenda),
                        eqVoteValue(condition.getVoteValue()),
                        containsName(condition.getName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression eqAgenda(Agenda agenda) {
        if(agenda == null) {
            throw new BaseException(ErrorCode.AGENDA_NOT_FOUND);
        }
        return vote.agenda.eq(agenda);
    }

    private BooleanExpression eqVoteValue(VoteValue voteValue) {
        if(!StringUtils.hasText(voteValue.toString())) {
            return null;
        }
        return vote.voteValue.eq(voteValue);
    }

    private BooleanExpression containsName(String searchName) {
        if (!StringUtils.hasText(searchName)) {
            return null;
        }
        return vote.member.name.contains(searchName);
    }
}
