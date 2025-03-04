package com.rangjin.core.article.dto

data class CrawlingResultDto (

    val pressId: Long,

    val rank: Int,

    val headTitle: String,

    val link: String,

    val viewCount: Int

) {
}