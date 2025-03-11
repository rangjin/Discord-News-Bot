package com.rangjin.central.global.jda

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JdaConfig (

    @Value("\${discord.bot.token}")
    private val discordBotToken: String

) {

    @Bean
    fun jda(): JDA {
        return JDABuilder.createDefault(discordBotToken)
            .build()
    }

}