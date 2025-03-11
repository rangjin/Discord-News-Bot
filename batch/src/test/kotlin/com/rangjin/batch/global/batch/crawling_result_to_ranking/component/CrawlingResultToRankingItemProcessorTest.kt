package com.rangjin.batch.global.batch.crawling_result_to_ranking.component

import com.rangjin.core.domain.article.dto.CrawlingResultDto
import com.rangjin.core.domain.article.entity.Article
import com.rangjin.core.domain.article.repository.ArticleRepository
import com.rangjin.core.domain.press.entity.Press
import com.rangjin.core.domain.press.repository.PressRepository
import com.rangjin.core.domain.ranking.entity.TemporalType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class CrawlingResultToRankingItemProcessorTest {

    private val pressRepository: PressRepository = mockk()
    private val articleRepository: ArticleRepository = mockk()

    init {
        val pressList = listOf(
            Press(1L, "언론사 1"),
            Press(2L, "언론사 1"),
            Press(3L, "언론사 1"),
            Press(4L, "언론사 1"),
            Press(5L, "언론사 1"),
        )
        every { pressRepository.findAll() } returns pressList
    }

    private val crawlingResultToRankingItemProcessor = CrawlingResultToRankingItemProcessor(
        LocalDateTime.parse("2025-03-08T02:00:00"), pressRepository, articleRepository)

    @Test
    fun save_database_item_processor_test() {
        // given
        every { articleRepository.findById(any()) } returns Optional.empty()
        every { articleRepository.save(any()) } answers { it.invocation.args[0] as Article }
        val item = CrawlingResultDto(1L, 1, "기사 제목 1", "https://news.com/article/001/1234567890", 1000)

        // when
        val result = crawlingResultToRankingItemProcessor.process(item)

        // then
        assertTrue(result != null)
        assertEquals(1, result!!.idx)
        assertEquals(1000, result.viewCount)
        assertEquals(TemporalType.HOURLY, result.temporalType)
        assertEquals("2025-03-08T02:00", result.temporal)
    }

}