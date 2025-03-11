package com.rangjin.core.domain.press.dto

import com.rangjin.core.domain.press.entity.Press

data class PressDto (

    val id: Long,

    val name: String

) {

    companion object {
        fun from (press: Press): PressDto {
            return PressDto(
                id = press.id,
                name = press.name
            )
        }
    }

}
