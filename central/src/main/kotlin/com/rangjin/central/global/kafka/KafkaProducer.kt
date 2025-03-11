package com.rangjin.central.global.kafka

import com.rangjin.core.global.kafka.dto.KafkaMessage
import com.rangjin.core.global.kafka.dto.KafkaMessageType
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class KafkaProducer (

    @Value("\${spring.kafka.producer.topic}")
    private val topicName: String,

    private val kafkaTemplate: KafkaTemplate<String, KafkaMessage?>

) {

    fun send(kafkaMessage: KafkaMessage) {
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