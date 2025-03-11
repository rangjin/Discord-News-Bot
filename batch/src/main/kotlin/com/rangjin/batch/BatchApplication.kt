package com.rangjin.batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!

@EnableBatchProcessing
@SpringBootApplication
@ComponentScan(basePackages = ["com.rangjin.batch", "com.rangjin.core"])
@EntityScan(basePackages = ["com.rangjin.core"])
@EnableJpaRepositories(basePackages = ["com.rangjin.core"])
class BatchApplication

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}
