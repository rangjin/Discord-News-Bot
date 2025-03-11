package com.rangjin.batch.global.batch.daily_statistics.ranking_selection

import com.rangjin.core.domain.ranking.entity.Ranking
import com.rangjin.core.domain.ranking.entity.TemporalType
import com.rangjin.core.domain.ranking.repository.RankingRepository
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
@JobScope
class RankingSelectionTasklet (

    @Value("#{jobParameters['time']}")
    private val time: LocalDate,

    private val rankingRepository: RankingRepository

): Tasklet {

    @Transactional
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val temporalType = TemporalType.getTypeByTemporal(time)!!

        // 날짜가 time 과 같은 ranking 서치
        val rankingList = rankingRepository.findByTemporalTypeAndTemporalStartsWith(
            TemporalType.HOURLY, time.toString())

        // 조회수 합산 상위 10개 ranking 필터링
        val top10RankingList = rankingList
            .groupBy { it.article.id }
            .map { (_, rankingList) ->
                val article = rankingList.first().article
                val totalViewCount = rankingList.sumOf { it.viewCount }

                Ranking(
                    id = null,
                    article = article,
                    idx = 0, // 이후 순위 설정
                    viewCount = totalViewCount,
                    temporalType = temporalType,
                    temporal = time.toString()
                )
            }
            .sortedByDescending { it.viewCount }
            .take(10)
            .mapIndexed { index, ranking -> ranking.apply { idx = index + 1 } }

        // 조회수 합산 상위 10개 새로 등록 후, 나머지 삭제
        rankingRepository.saveAll(top10RankingList)
        rankingRepository.deleteAll(rankingList)

        return RepeatStatus.FINISHED
    }

}