package com.rangjin.central.global.crawling

import com.rangjin.core.article.dto.CrawlingResultDto
import com.rangjin.core.press.dto.PressDto
import com.rangjin.central.domain.press.service.PressService
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ArticleCrawlerTest {

    private val pressService: PressService = mockk()
    private val articleCrawler: ArticleCrawler = ArticleCrawler("testUrl", pressService)

    @Test
    fun crawling_test() {
        // given
        val mockPressList = listOf(
            PressDto(id = 1L, name = "언론사 1")
        )
        every { pressService.getAllPress() } returns mockPressList

        val mockDocument = Jsoup.parse(
            """
                <ul class="press_ranking_list">
                    <li class="as_thumb">
                        <em class="list_ranking_num">1</em>
                        <strong class="list_title">기사 제목 1</strong>
                        <a href="https://news.com/1234567890">링크</a>
                        <span class="list_view">1000</span>
                    </li>
                    <li class="as_thumb">
                        <em class="list_ranking_num">2</em>
                        <strong class="list_title">기사 제목 2</strong>
                        <a href="https://news.com/0987654321">링크</a>
                        <span class="list_view">900</span>
                    </li>
                </ul>
            """
        )
        mockkStatic(Jsoup::class)
        every { Jsoup.connect(any()).get() } returns mockDocument

        // when
        val result = articleCrawler.crawlingRankingArticles()

        // then
        assertEquals(2, result.size)
        assertEquals(
            CrawlingResultDto(1L, 1, "기사 제목 1", "https://news.com/1234567890", 1000),
            result[0]
        )
        assertEquals(
            CrawlingResultDto(1L, 2, "기사 제목 2", "https://news.com/0987654321", 900),
            result[1]
        )
    }

}