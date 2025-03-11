package com.rangjin.core.domain.article.repository

import com.rangjin.core.domain.article.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository: JpaRepository<Article, Long> {

    @Query("select a from Article a left join Ranking r on a = r.article where r.id is null")
    fun findAllByRankingIsNull(): List<Article>

}