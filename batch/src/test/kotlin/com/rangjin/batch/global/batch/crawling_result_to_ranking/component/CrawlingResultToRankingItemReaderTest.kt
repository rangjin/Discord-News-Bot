package com.rangjin.batch.global.batch.crawling_result_to_ranking.component

import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.core.domain.article.dto.CrawlingResultDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CrawlingResultToRankingItemReaderTest {

    private val temporalDatabase: TemporalDatabase = TemporalDatabase()

    private val crawlingResultDtoList = listOf(
        CrawlingResultDto(1L, 1, "기사 제목 1", "https://news.com/1234567890", 1000),
        CrawlingResultDto(1L, 2, "기사 제목 2", "https://news.com/1234567891", 900)
    )

    init {
        crawlingResultDtoList.forEach { temporalDatabase.offer(it) }
    }

    private val crawlingResultToRankingItemReader = CrawlingResultToRankingItemReader(ObjectMapper(), temporalDatabase)

    @Test
    fun save_database_item_reader_test() {
        // given
        val resultList = mutableListOf<CrawlingResultDto?>()

        // when
        for (i in 1 .. crawlingResultDtoList.size + 1) {
            resultList.add(crawlingResultToRankingItemReader.read())
        }

        // then
        assertEquals(crawlingResultDtoList.size + 1, resultList.size)
        assertEquals(crawlingResultDtoList[0], resultList[0])
        assertEquals(crawlingResultDtoList[1], resultList[1])
        assertEquals(null, resultList[2])
    }

}