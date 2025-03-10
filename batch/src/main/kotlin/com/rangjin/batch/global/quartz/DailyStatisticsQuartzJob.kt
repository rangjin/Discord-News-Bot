package com.rangjin.batch.global.quartz

import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@DisallowConcurrentExecution
class DailyStatisticsQuartzJob (

    private val jobLauncher: JobLauncher,

    private val dailyStatisticsJob: org.springframework.batch.core.Job

): Job {

    override fun execute(context: JobExecutionContext?) {
        val jobParameters = JobParametersBuilder()
            .addString("key", System.currentTimeMillis().toString())
            .addLocalDate("time", LocalDate.now().minusDays(1))
            .toJobParameters()

        jobLauncher.run(dailyStatisticsJob, jobParameters)
    }

}