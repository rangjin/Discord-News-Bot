package com.rangjin.batch.global.batch.crawling_result_to_ranking.component

import com.rangjin.core.domain.article.dto.CrawlingResultDto
import com.rangjin.core.domain.article.entity.Article
import com.rangjin.core.domain.article.repository.ArticleRepository
import com.rangjin.core.domain.press.repository.PressRepository
import com.rangjin.core.domain.ranking.entity.Ranking
import com.rangjin.core.domain.ranking.entity.TemporalType
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@StepScope
class CrawlingResultToRankingItemProcessor (

    @Value("#{jobParameters['time']}")
    private val temporal: LocalDateTime,

    pressRepository: PressRepository,

    private val articleRepository: ArticleRepository

): ItemProcessor<CrawlingResultDto, Ranking> {

    private val pressList = pressRepository.findAll()

    override fun process(item: CrawlingResultDto): Ranking? {
        val (pressId, articleId) = extractIds(item.link)

        val press = pressList.find { it.id == pressId }

        val article = articleRepository.findById(articleId).orElseGet {
            articleRepository.save(Article(
                id = articleId,
                headTitle = item.headTitle,
                link = item.link,
                press = press!!
            ))
        }

        return Ranking(
            id = null,
            article = article,
            idx = item.rank,
            viewCount = item.viewCount,
            temporalType = TemporalType.getTypeByTemporal(temporal)!!,
            temporal = temporal.toString()
        )
    }

    private fun extractIds(link: String): Pair<Long, Long> {
        val regex = """/article/(\d+)/0*(\d+)""".toRegex()
        val matchResult = regex.find(link) ?: throw IllegalArgumentException("Invalid link format: $link")

        val pressId = matchResult.groupValues[1].toLong()
        val articleId = matchResult.groupValues[2].toLong()

        return Pair(pressId, articleId)
    }

}