package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Agenda
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.http.ResponseEntity

interface GuiaAgendaRepository: JpaRepository<agendaResponsavel, Int> {

    @Query("""
        INSERT INTO agenda_responsavel (data_disponivel, fk_responsavel)
        VALUES (:dataAgenda.data_disponivel, :dataAgenda.fk_responsavel)
    """, nativeQuery = true)
    fun adicionarAgenda(dataAgenda: Agenda): ResponseEntity<Void>

    fun findByFkResponsavelOrderByDataDisponivelAsc(): List<Agenda>
}