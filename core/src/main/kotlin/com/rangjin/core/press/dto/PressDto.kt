package com.rangjin.core.press.dto

import com.rangjin.core.press.entity.Press

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
