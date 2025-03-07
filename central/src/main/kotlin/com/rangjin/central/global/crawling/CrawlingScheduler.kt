package com.rangjin.central.global.crawling

import com.rangjin.central.global.kafka.KafkaProducerService
import com.rangjin.core.global.kafka.dto.KafkaMessage
import com.rangjin.core.global.kafka.dto.KafkaMessageType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CrawlingScheduler (

    private val articleCrawler: ArticleCrawler,

    private val kafkaProducerService: KafkaProducerService

) {

    @Scheduled(cron = "0 0 * * * *")
    fun schedule() {
        val crawlingResultDtoList = articleCrawler.crawlingRankingArticles()

        for (crawlingResultDto in crawlingResultDtoList) {
            kafkaProducerService.send(
                KafkaMessage(
                    type = KafkaMessageType.DATA,
                    data = crawlingResultDto
                )
            )
        }

        kafkaProducerService.sendComplete()
    }

}