package com.rangjin.core.global.kafka.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KafkaMessage<T> (

    @JsonProperty("type")
    val type: KafkaMessageType,

    @JsonProperty("data")
    val data: T?

)
