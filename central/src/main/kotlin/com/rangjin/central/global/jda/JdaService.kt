package com.rangjin.central.global.jda

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.MessageEmbed
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JdaService (

    private val jda: JDA,

    @Value("\${discord.channel-id}")
    private val channelId: String

) {

    fun sendMessage(messageEmbed: MessageEmbed) {
        try {
            val textChannel = jda.getTextChannelById(channelId)
            textChannel!!.sendMessage("").setEmbeds(messageEmbed).queue()
        } catch (e: Exception) {
            println(e)
        }
    }


}