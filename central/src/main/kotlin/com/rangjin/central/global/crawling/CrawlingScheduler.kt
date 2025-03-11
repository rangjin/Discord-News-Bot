package com.rangjin.central.global.crawling

import com.rangjin.central.global.kafka.KafkaProducer
import com.rangjin.core.global.kafka.dto.KafkaMessage
import com.rangjin.core.global.kafka.dto.KafkaMessageType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CrawlingScheduler (

    private val articleCrawler: ArticleCrawler,

    private val kafkaProducer: KafkaProducer

) {

    @Scheduled(cron = "0 0 * * * *")
    fun schedule() {
        val crawlingResultDtoList = articleCrawler.crawlingRankingArticles()

        for (crawlingResultDto in crawlingResultDtoList) {
            kafkaProducer.send(
                KafkaMessage(
                    type = KafkaMessageType.DATA,
                    data = crawlingResultDto
                )
            )
        }

        kafkaProducer.sendComplete()
    }

}