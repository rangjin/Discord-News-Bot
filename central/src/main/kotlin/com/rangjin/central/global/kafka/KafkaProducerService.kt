package com.rangjin.central.global.kafka

import com.rangjin.core.article.dto.CrawlingResultDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class KafkaProducerService (

    @Value("\${spring.kafka.producer.topic}")
    private val topicName: String,

    private val kafkaTemplate: KafkaTemplate<String, CrawlingResultDto>

) {

    fun send(crawlingResultDto: CrawlingResultDto) {
        kafkaTemplate.send(topicName, UUID.randomUUID().toString(), crawlingResultDto)
    }

}