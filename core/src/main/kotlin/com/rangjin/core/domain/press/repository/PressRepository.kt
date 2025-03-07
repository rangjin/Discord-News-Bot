package com.rangjin.core.domain.press.repository

import com.rangjin.core.domain.press.entity.Press
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PressRepository: JpaRepository<Press, Long> {
}