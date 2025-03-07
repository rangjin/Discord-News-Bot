package com.rangjin.central.global.kafka

import com.rangjin.core.domain.article.dto.CrawlingResultDto
import com.rangjin.core.global.kafka.dto.KafkaMessage
import com.rangjin.core.global.kafka.dto.KafkaMessageType
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class KafkaProducerService (

    @Value("\${spring.kafka.producer.topic}")
    private val topicName: String,

    private val kafkaTemplate: KafkaTemplate<String, KafkaMessage<CrawlingResultDto>>

) {

    fun send(kafkaMessage: KafkaMessage<CrawlingResultDto>) {
        kafkaTemplate.send(topicName, UUID.randomUUID().toString(), kafkaMessage)
    }

    fun sendComplete() {
        val partitions = kafkaTemplate.partitionsFor(topicName)

        for (partition in partitions) {
            kafkaTemplate.send(topicName, partition.partition(), UUID.randomUUID().toString(), KafkaMessage(
                KafkaMessageType.COMPLETE,
                null
            ))
        }
    }

}