package com.rangjin.central.domain.press.service

import com.rangjin.core.domain.press.dto.PressDto
import com.rangjin.core.domain.press.entity.Press
import com.rangjin.core.domain.press.repository.PressRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PressServiceTest {

    private val pressRepository: PressRepository = mockk()
    private val pressService = PressService(pressRepository)

    @Test
    fun get_all_press_test() {
        // given
        val mockPressList = listOf(
            Press(id = 1L, name = "언론사 1"),
            Press(id = 2L, name = "언론사 2")
        )
        every { pressRepository.findAll() } returns mockPressList

        // when
        val result = pressService.getAllPress()

        // then
        assertEquals(2, result.size)
        assertEquals(PressDto(1L, "언론사 1"), result[0])
        assertEquals(PressDto(2L, "언론사 2"), result[1])
    }

}