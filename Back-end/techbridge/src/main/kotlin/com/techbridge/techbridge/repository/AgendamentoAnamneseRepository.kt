package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.AgendamentoAnamnese
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AgendamentoAnamneseRepository : JpaRepository<AgendamentoAnamnese, Int> {
    fun findByFkAventureiro(fkAventureiro: Int): List<AgendamentoAnamnese>
}