package com.rangjin.batch.global.batch.daily_statistics.ranking_selection

import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class RankingSelectionStepConfig (

    private val jobRepository: JobRepository,

    private val rankingSelectionTasklet: RankingSelectionTasklet,

    private val platformTransactionManager: PlatformTransactionManager

) {

    @Bean
    fun rankingSelectionStep(): Step {
        return StepBuilder("RankingSelectionStep", jobRepository)
            .tasklet(rankingSelectionTasklet, platformTransactionManager)
            .build()
    }

}