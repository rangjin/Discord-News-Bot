package com.rangjin.central.global.kafka

import com.rangjin.core.article.dto.CrawlingResultDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.kafka.core.KafkaTemplate

class KafkaProducerServiceTest {

    private val kafkaTemplate: KafkaTemplate<String, CrawlingResultDto> = mockk()
    private val kafkaProducerService: KafkaProducerService = KafkaProducerService(kafkaTemplate)

    @Test
    fun kafka_producer_test() {
        // given
        val crawlingResultDto = CrawlingResultDto(
            pressId = 1L,
            rank = 1,
            headTitle = "기사 제목 1",
            link = "https://news.com/1234567890",
            viewCount = 1000
        )

        every { kafkaTemplate.send(any<String>(), any<CrawlingResultDto>()) } returns mockk()

        // when
        kafkaProducerService.send(crawlingResultDto)

        // then
        verify(exactly = 1) {
            kafkaTemplate.send("crawling-result", any())
        }
    }

}