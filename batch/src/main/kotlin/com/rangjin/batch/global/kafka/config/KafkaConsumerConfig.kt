package com.rangjin.batch.global.kafka.config

import com.rangjin.core.global.kafka.dto.KafkaMessage
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig (

    @Value("\${spring.kafka.consumer.bootstrap-servers}")
    private val bootstrapServers: String,

    @Value("\${spring.kafka.consumer.group-id}")
    private val groupId: String

) {

    @Bean
    fun consumerFactory(): DefaultKafkaConsumerFactory<String, KafkaMessage> {
        println(bootstrapServers)
        println(groupId)
        val props = mutableMapOf<String, Any>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.GROUP_ID_CONFIG to groupId,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
        )

        val deserializer = JsonDeserializer(KafkaMessage::class.java)
        deserializer.addTrustedPackages("com.rangjin.core.global.kafka.dto")
        deserializer.setRemoveTypeHeaders(false)

        return DefaultKafkaConsumerFactory(props, StringDeserializer(), deserializer)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, KafkaMessage>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

}