package com.rangjin.core.domain.press.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Press (

    @Id
    val id: Long,

    val name: String

) {
}