package com.rangjin.core.domain.ranking.entity

import com.rangjin.core.domain.article.entity.Article
import jakarta.persistence.*

@Entity
class Ranking (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    val article: Article,

    var idx: Int,

    val viewCount: Int,

    val temporalType: TemporalType,

    val temporal: String

) {
}