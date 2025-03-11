package com.rangjin.core.global.kafka.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.rangjin.core.domain.article.dto.CrawlingResultDto

data class KafkaMessage (

    @JsonProperty("type")
    val type: KafkaMessageType,

    @JsonProperty("data")
    val data: CrawlingResultDto?

)
