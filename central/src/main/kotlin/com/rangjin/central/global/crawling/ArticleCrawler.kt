package com.rangjin.central.global.crawling

import com.rangjin.core.article.dto.CrawlingResultDto
import com.rangjin.central.domain.press.service.PressService
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ArticleCrawler (

    @Value("\${crawling.url}")
    private val urlTemplate: String,

    private val pressService: PressService

) {

    fun crawlingRankingArticles(): List<CrawlingResultDto> {
        val pressDtoList = pressService.getAllPress()

        val crawlingResultDtoList = ArrayList<CrawlingResultDto>()
        for (pressDto in pressDtoList) {
            val pressId = pressDto.id
            val document = Jsoup.connect(String.format(urlTemplate, pressDto.id)).get()
            val articleList = document.select("ul.press_ranking_list li.as_thumb")
                .mapNotNull { article -> crawlingResultToDto(pressId, article) }

            crawlingResultDtoList.addAll(articleList)
        }

        return crawlingResultDtoList
    }

    private fun crawlingResultToDto(pressId: Long, article: Element): CrawlingResultDto? {
        val rank = article.selectFirst("em.list_ranking_num")?.text()?.toInt()
        val headTitle = article.selectFirst("strong.list_title")?.text()
        val link = article.selectFirst("a")?.attr("href")
        val viewCount = article.selectFirst("span.list_view")?.text()
            ?.replace("[^0-9]".toRegex(), "")?.toIntOrNull()

        return if (rank != null && headTitle != null && link != null && viewCount != null) {
            CrawlingResultDto(
                pressId = pressId,
                rank = rank,
                headTitle = headTitle,
                link = link,
                viewCount = viewCount
            )
        } else {
            null
        }
    }

}