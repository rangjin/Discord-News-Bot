package com.rangjin.central.domain.press.service

import com.rangjin.core.domain.press.dto.PressDto
import com.rangjin.core.domain.press.repository.PressRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PressService (

    private val pressRepository: PressRepository

) {

    @Transactional(readOnly = true)
    fun getAllPress(): List<PressDto> {
        return pressRepository.findAll().map {
            press -> PressDto.from(press)
        }
    }

}