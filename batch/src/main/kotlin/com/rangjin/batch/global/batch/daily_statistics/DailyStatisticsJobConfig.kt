package com.rangjin.batch.global.batch.daily_statistics

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DailyStatisticsJobConfig (

    private val jobRepository: JobRepository,

    private val rankingSelectionStep: Step,

    private val deleteArticleStep: Step

) {

    @Bean
    fun dailyStatisticsJob(): Job {
        return JobBuilder("DailyStatisticsJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(rankingSelectionStep)
            .next(deleteArticleStep)
            .build()
    }

}