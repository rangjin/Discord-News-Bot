package com.rangjin.batch.global.kafka

import com.rangjin.batch.global.kafka.config.KafkaConnectionTestConfig
import com.rangjin.core.domain.article.dto.CrawlingResultDto
import com.rangjin.core.global.kafka.dto.KafkaMessage
import com.rangjin.core.global.kafka.dto.KafkaMessageType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.TestPropertySource
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import kotlin.test.Test
import kotlin.test.assertEquals

@SpringBootTest
@EmbeddedKafka(topics = ["test-topic"], brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
@Import(KafkaConnectionTestConfig::class)
@TestPropertySource(properties = [
    "spring.kafka.consumer.bootstrap-servers=localhost:9092",
    "spring.kafka.consumer.group-id=test-topic-group",
])
class KafkaConnectionTest (

    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, KafkaMessage>

) {

    private val messageQueue = LinkedBlockingQueue<KafkaMessage>()

    @KafkaListener(topics = ["test-topic"], groupId = "test-topic-group")
    fun consume(kafkaMessage: KafkaMessage) {
        messageQueue.offer(kafkaMessage)
    }

    @Test
    fun kafka_connection_test() {
        // given
        val data = CrawlingResultDto(1L, 1, "기사 제목 1", "https://news.com/1234567890", 1000)
        val kafkaMessage = KafkaMessage(KafkaMessageType.DATA, data)

        // when
        kafkaTemplate.send("test-topic", kafkaMessage)

        // then
        val receivedMessage = messageQueue.poll(5, TimeUnit.SECONDS)!!
        assertEquals(kafkaMessage, receivedMessage)
        assertEquals(kafkaMessage.type, receivedMessage.type)
        assertEquals(kafkaMessage.data, receivedMessage.data)
    }

}