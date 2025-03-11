package com.rangjin.core.domain.ranking.repository

import com.rangjin.core.domain.ranking.entity.Ranking
import com.rangjin.core.domain.ranking.entity.TemporalType
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RankingRepository: JpaRepository<Ranking, Long> {

    @EntityGraph(attributePaths = ["article"])
    fun findByTemporalTypeAndTemporalStartsWith(temporalType: TemporalType, temporal: String): List<Ranking>

}