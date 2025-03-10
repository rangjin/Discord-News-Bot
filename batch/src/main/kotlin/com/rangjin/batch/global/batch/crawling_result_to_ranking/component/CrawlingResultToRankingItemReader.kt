package com.rangjin.batch.global.batch.crawling_result_to_ranking.component

import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.core.domain.article.dto.CrawlingResultDto
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component

@Component
@StepScope
class CrawlingResultToRankingItemReader (

    private val objectMapper: ObjectMapper,

    temporalDatabase: TemporalDatabase

): ItemReader<CrawlingResultDto> {

    private val iterator: Iterator<CrawlingResultDto> = temporalDatabase.toList().iterator()

    override fun read(): CrawlingResultDto? {
        return if (iterator.hasNext()) {
            objectMapper.convertValue(iterator.next(), CrawlingResultDto::class.java)
        } else null
    }

}