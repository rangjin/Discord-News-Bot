package com.rangjin.central.global.jda

import com.rangjin.central.domain.ranking.service.RankingService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class JdaScheduler (

    private val rankingService: RankingService,

    private val jdaService: JdaService

) {

    @Scheduled(cron = "0 5 0 * * *")
    fun daily() {
        val temporal = LocalDate.now().minusDays(1)
        jdaService.sendMessage(rankingService.getDailyTopNews(temporal))
    }

}