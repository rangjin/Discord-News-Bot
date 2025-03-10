package com.rangjin.batch.global.batch.daily_statistics.delete_article

import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class DeleteArticleStepConfig (

    private val jobRepository: JobRepository,

    private val deleteArticleTasklet: DeleteArticleTasklet,

    private val platformTransactionManager: PlatformTransactionManager

) {

    @Bean
    fun deleteArticleStep(): Step {
        return StepBuilder("DeleteArticleStep", jobRepository)
            .tasklet(deleteArticleTasklet, platformTransactionManager)
            .build()
    }

}