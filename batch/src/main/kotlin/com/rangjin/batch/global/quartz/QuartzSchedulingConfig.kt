package com.rangjin.batch.global.quartz

import org.quartz.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuartzSchedulingConfig {

    @Bean
    fun dailyStatisticsJobDetail(): JobDetail {
        return JobBuilder.newJob(DailyStatisticsQuartzJob::class.java)
            .withIdentity("dailyStatisticsJob")
            .storeDurably()
            .build()
    }

    @Bean
    fun dailyStatisticsTrigger(): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(dailyStatisticsJobDetail())
            .withIdentity("dailyStatisticsTrigger")
            .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
            .build()
    }

}