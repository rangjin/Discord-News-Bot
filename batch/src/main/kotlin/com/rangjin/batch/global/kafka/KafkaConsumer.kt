package com.rangjin.batch.global.kafka

import com.rangjin.batch.global.batch.crawling_result_to_ranking.component.TemporalDatabase
import com.rangjin.batch.logger
import com.rangjin.core.global.kafka.dto.KafkaMessage
import com.rangjin.core.global.kafka.dto.KafkaMessageType
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class KafkaConsumer (

    private val jobLauncher: JobLauncher,

    private val crawlingResultToRankingJob: Job,

    private val temporalDatabase: TemporalDatabase

) {

    private val log = logger()

    @KafkaListener(topics = ["\${spring.kafka.consumer.topic}"])
    fun consumeMessage(kafkaMessage: KafkaMessage) {
        if (kafkaMessage.type == KafkaMessageType.DATA) {
            temporalDatabase.offer(kafkaMessage.data!!)
        } else {
            if (temporalDatabase.isNotEmpty()) {
                log.info("{} Messages Consumed", temporalDatabase.size)

                val jobParameters = JobParametersBuilder()
                    .addString("key", System.currentTimeMillis().toString())
                    .addLocalDateTime("time", LocalDateTime.now().withMinute(0).withSecond(0).withNano(0))
                    .toJobParameters()

                jobLauncher.run(crawlingResultToRankingJob, jobParameters)
                temporalDatabase.clear()
            } else {
                log.info("Message Queue is Empty")
            }
        }
    }

}