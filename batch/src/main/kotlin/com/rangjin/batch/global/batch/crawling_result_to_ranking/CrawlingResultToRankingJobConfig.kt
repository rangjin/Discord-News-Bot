package com.rangjin.batch.global.batch.crawling_result_to_ranking

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CrawlingResultToRankingJobConfig (

    private val jobRepository: JobRepository,

    private val crawlingResultToRankingStep: Step

) {

    @Bean
    fun crawlingResultToRankingJob(): Job {
        return JobBuilder("CrawlingResultToRankingJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(crawlingResultToRankingStep)
            .build()
    }

}