package com.rangjin.core.global.kafka.dto

enum class KafkaMessageType (

    val type: String

) {

    DATA("data"),
    COMPLETE("complete")
    ;

}