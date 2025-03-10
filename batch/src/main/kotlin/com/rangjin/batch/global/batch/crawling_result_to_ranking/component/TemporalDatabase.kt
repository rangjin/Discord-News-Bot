package com.rangjin.batch.global.batch.crawling_result_to_ranking.component

import com.rangjin.core.domain.article.dto.CrawlingResultDto
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedQueue

@Component
class TemporalDatabase: ConcurrentLinkedQueue<CrawlingResultDto>() {
}