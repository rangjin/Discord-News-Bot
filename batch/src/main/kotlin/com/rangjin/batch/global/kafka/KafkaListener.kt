package com.rangjin.batch.global.kafka

import com.rangjin.batch.logger
import com.rangjin.core.domain.article.dto.CrawlingResultDto
import com.rangjin.core.global.kafka.dto.KafkaMessage
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaListener {

    private val log = logger()

    @KafkaListener(topics = ["\${spring.kafka.consumer.topic}"])
    fun consumerMessage(kafkaMessage: KafkaMessage<CrawlingResultDto>) {
        // todo: 배치 처리 필요
        log.info("{}", kafkaMessage)
    }

}