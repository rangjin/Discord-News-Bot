package com.rangjin.core.article.entity

import com.rangjin.core.press.entity.Press
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class Article (

    @Id
    val id: Long,

    val headTitle: String,

    val link: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val press: Press

) {
}