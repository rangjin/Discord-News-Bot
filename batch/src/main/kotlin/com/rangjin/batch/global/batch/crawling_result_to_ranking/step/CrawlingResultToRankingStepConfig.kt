package com.rangjin.batch.global.batch.crawling_result_to_ranking.step

import com.rangjin.batch.global.batch.crawling_result_to_ranking.component.CrawlingResultToRankingItemProcessor
import com.rangjin.batch.global.batch.crawling_result_to_ranking.component.CrawlingResultToRankingItemReader
import com.rangjin.core.domain.article.dto.CrawlingResultDto
import com.rangjin.core.domain.ranking.entity.Ranking
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Step
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class CrawlingResultToRankingStepConfig (

    private val jobRepository: JobRepository,

    private val platformTransactionManager: PlatformTransactionManager,

    private val itemReader: CrawlingResultToRankingItemReader,

    private val itemProcessor: CrawlingResultToRankingItemProcessor,

    private val entityManagerFactory: EntityManagerFactory

) {

    @Bean
    fun crawlingResultToRankingStep(): Step {
        val itemWriter = JpaItemWriterBuilder<Ranking>()
            .entityManagerFactory(entityManagerFactory)
            .build()

        return StepBuilder("CrawlingResultToRankingStep", jobRepository)
            .chunk<CrawlingResultDto, Ranking>(5, platformTransactionManager)
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWriter)
            .build()
    }

}