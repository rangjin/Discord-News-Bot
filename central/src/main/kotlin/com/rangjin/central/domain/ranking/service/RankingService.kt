package com.rangjin.central.domain.ranking.service

import com.rangjin.core.domain.ranking.entity.TemporalType
import com.rangjin.core.domain.ranking.repository.RankingRepository
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import org.springframework.stereotype.Service
import java.awt.Color
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class RankingService (

    private val rankingRepository: RankingRepository

) {

    fun getDailyTopNews(
        temporal: LocalDate
    ): MessageEmbed {
        val rankingList = rankingRepository.findByTemporalTypeAndTemporalStartsWith(TemporalType.DAILY, temporal.toString())

        val title = temporal.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
        var embedBuilder = EmbedBuilder()
            .setTitle(title)
            .setColor(Color.GREEN)

        rankingList.forEach {
            embedBuilder = embedBuilder.addField(
                "${it.idx}: ${it.article.headTitle} (${it.viewCount})",
                it.article.link,
                false
            )
        }

        return embedBuilder.build()
    }

}