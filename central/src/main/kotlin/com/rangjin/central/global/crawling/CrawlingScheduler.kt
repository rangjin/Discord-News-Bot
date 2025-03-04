package com.rangjin.central.global.crawling

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CrawlingScheduler (

    private val articleCrawler: ArticleCrawler

) {

    @Scheduled(cron = "0 0 * * * *")
    fun schedule() {
        val rankingDtoList = articleCrawler.crawlingRankingArticles()

        // todo: rankingDtoList 를 배치 서버로 전송
        for (rankingDto in rankingDtoList) {
            println(rankingDto)
        }
    }

}