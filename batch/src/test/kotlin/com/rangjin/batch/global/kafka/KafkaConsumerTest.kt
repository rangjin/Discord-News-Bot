package com.rangjin.batch.global.kafka

import com.rangjin.batch.global.batch.crawling_result_to_ranking.component.TemporalDatabase
import com.rangjin.core.domain.article.dto.CrawlingResultDto
import com.rangjin.core.global.kafka.dto.KafkaMessage
import com.rangjin.core.global.kafka.dto.KafkaMessageType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.launch.JobLauncher

class KafkaConsumerTest {

    private val jobLauncher: JobLauncher = mockk()
    private val saveDatabaseJob: Job = mockk()
    private val temporalDatabase: TemporalDatabase = TemporalDatabase()

    private val kafkaConsumer: KafkaConsumer = KafkaConsumer(jobLauncher, saveDatabaseJob, temporalDatabase)

    @BeforeEach
    fun setUp() {
        temporalDatabase.clear()
    }

    @Test
    fun consume_data_test() {
        // given
        val kafkaMessageList = mutableListOf(
            CrawlingResultDto(1L, 1, "기사 제목 1", "https://news.com/1234567890", 1000),
            CrawlingResultDto(1L, 2, "기사 제목 2", "https://news.com/1234567891", 900),
            CrawlingResultDto(1L, 3, "기사 제목 3", "https://news.com/1234567892", 800),
            CrawlingResultDto(1L, 4, "기사 제목 4", "https://news.com/1234567893", 700),
            CrawlingResultDto(1L, 5, "기사 제목 5", "https://news.com/1234567894", 600),
            CrawlingResultDto(1L, 6, "기사 제목 6", "https://news.com/1234567895", 500),
            CrawlingResultDto(1L, 7, "기사 제목 7", "https://news.com/1234567896", 400),
            CrawlingResultDto(1L, 8, "기사 제목 8", "https://news.com/1234567897", 300),
            CrawlingResultDto(1L, 9, "기사 제목 9", "https://news.com/1234567898", 200),
            CrawlingResultDto(1L, 10, "기사 제목 10", "https://news.com/1234567899", 100)
        ).map { crawlingResultDto -> KafkaMessage(KafkaMessageType.DATA, crawlingResultDto) }

        // when
        kafkaMessageList.forEach {
            kafkaConsumer.consumeMessage(it)
        }

        // then
        assertEquals(10, temporalDatabase.size)
    }

    @Test
    fun consume_complete_test() {
        // given
        val dataKafkaMessage = KafkaMessage(
            KafkaMessageType.DATA,
            CrawlingResultDto(1L, 1, "기사 제목 1", "https://news.com/1234567890", 1000)
        )
        val completeKafkaMessage = KafkaMessage(KafkaMessageType.COMPLETE, null)
        every { jobLauncher.run(any(), any()) } returns mockk<JobExecution>()

        // when
        kafkaConsumer.consumeMessage(dataKafkaMessage)
        kafkaConsumer.consumeMessage(completeKafkaMessage)

        // then
        verify(exactly = 1) { jobLauncher.run(any(), any()) }
    }

    @Test
    fun consume_complete_without_data_test() {
        // given
        val completeKafkaMessage = KafkaMessage(KafkaMessageType.COMPLETE, null)
        every { jobLauncher.run(any(), any()) } returns mockk<JobExecution>()

        // when
        kafkaConsumer.consumeMessage(completeKafkaMessage)

        // then
        verify(exactly = 0) { jobLauncher.run(any(), any()) }
    }

}