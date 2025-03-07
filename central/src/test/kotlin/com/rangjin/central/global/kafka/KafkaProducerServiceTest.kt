package com.rangjin.central.global.kafka

import com.rangjin.core.domain.article.dto.CrawlingResultDto
import com.rangjin.core.global.kafka.dto.KafkaMessage
import com.rangjin.core.global.kafka.dto.KafkaMessageType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.apache.kafka.common.PartitionInfo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.kafka.core.KafkaTemplate

class KafkaProducerServiceTest {

    private val topicName = "test-topic"
    private val kafkaTemplate: KafkaTemplate<String, KafkaMessage<CrawlingResultDto>> = mockk()
    private val kafkaProducerService: KafkaProducerService = KafkaProducerService(topicName, kafkaTemplate)

    @Test
    fun kafka_producer_send_test() {
        // given
        val crawlingResultDto = CrawlingResultDto(
            pressId = 1L,
            rank = 1,
            headTitle = "기사 제목 1",
            link = "https://news.com/1234567890",
            viewCount = 1000
        )

        val kafkaMessage = KafkaMessage(
            type = KafkaMessageType.DATA,
            data = crawlingResultDto
        )

        every { kafkaTemplate.send(topicName, any(), any()) } returns mockk()

        // when
        kafkaProducerService.send(kafkaMessage)

        // then
        verify(exactly = 1) {
            kafkaTemplate.send(topicName, any(), any())
        }
    }

    @Test
    fun kafka_producer_send_complete_test() {
        // given
        every { kafkaTemplate.send(topicName, any(), any(), any()) } returns mockk()
        every { kafkaTemplate.partitionsFor(topicName) } returns mutableListOf(
            PartitionInfo(topicName, 0, null, null, null),
            PartitionInfo(topicName, 1, null, null, null)
        )

        // when
        kafkaProducerService.sendComplete()

        // then
        verify(exactly = 2) {
            kafkaTemplate.send(topicName, any(), any(), any())
        }
    }

}