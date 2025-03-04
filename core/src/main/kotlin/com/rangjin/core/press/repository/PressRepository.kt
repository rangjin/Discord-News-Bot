package com.rangjin.core.press.repository

import com.rangjin.core.press.entity.Press
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PressRepository: JpaRepository<Press, Long> {
}