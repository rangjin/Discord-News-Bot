package com.rangjin.central.global.kafka

import com.rangjin.core.article.dto.CrawlingResultDto
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService (

    private val kafkaTemplate: KafkaTemplate<String, CrawlingResultDto>

) {

    private final val topicName = "crawling-result"

    fun send(crawlingResultDto: CrawlingResultDto) {
        kafkaTemplate.send(topicName, crawlingResultDto)
    }

}