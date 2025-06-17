package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.AgendaResponsavel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface AgendaResponsavelRepository:JpaRepository<AgendaResponsavel, Long> {
    @Query("SELECT a FROM AgendaResponsavel a WHERE a.dataDisponivel > CURRENT_TIMESTAMP")
    fun findAllDisponiveis(): List<AgendaResponsavel>

    @Query("SELECT a FROM AgendaResponsavel a WHERE a.dataDisponivel > CURRENT_TIMESTAMP and a.fkresponsavel = :fkresponsavel")
    fun findByFkresponsavelAndDataDisponivel(
        fkresponsavel: Long
    ): List<AgendaResponsavel>
}