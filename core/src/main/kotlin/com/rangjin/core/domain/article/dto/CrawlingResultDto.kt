package com.rangjin.core.domain.article.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CrawlingResultDto (

    @JsonProperty("pressId")
    val pressId: Long,

    @JsonProperty("rank")
    val rank: Int,

    @JsonProperty("headTitle")
    val headTitle: String,

    @JsonProperty("link")
    val link: String,

    @JsonProperty("viewCount")
    val viewCount: Int

) {
}