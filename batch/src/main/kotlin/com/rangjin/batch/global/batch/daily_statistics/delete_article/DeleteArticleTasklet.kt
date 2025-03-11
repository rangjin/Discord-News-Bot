package com.rangjin.batch.global.batch.daily_statistics.delete_article

import com.rangjin.core.domain.article.repository.ArticleRepository
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@JobScope
class DeleteArticleTasklet (

    private val articleRepository: ArticleRepository

): Tasklet {

    @Transactional
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        // 더 이상 연결된 Ranking 이 없는 Article 을 서치해 삭제
        val deleteArticleList = articleRepository.findAllByRankingIsNull()
        articleRepository.deleteAll(deleteArticleList)

        return RepeatStatus.FINISHED
    }

}