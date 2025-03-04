package com.rangjin.central

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = ["com.rangjin.central", "com.rangjin.core"])
@EntityScan(basePackages = ["com.rangjin.core"])
@EnableJpaRepositories(basePackages = ["com.rangjin.core"])
class CentralApplication

fun main(args: Array<String>) {
    runApplication<CentralApplication>(*args)
}
