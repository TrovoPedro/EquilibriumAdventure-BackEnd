package com.techbridge.techbridge.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import com.techbridge.techbridge.entity.AgendamentoAnamnese
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
interface AgendamentoAnamneseRepository : JpaRepository<AgendamentoAnamnese, Int> {

    fun findByAventureiroIdUsuario(idUsuario: Int): List<AgendamentoAnamnese>

    fun findByAgendaResponsavelFkresponsavelIdUsuario(idResponsavel: Int): List<AgendamentoAnamnese>

    @Query(
        value = """
    SELECT aa.id_anamnese AS idAnamnese, ar.data_disponivel AS dataDisponivel, u.nome AS nomeUsuario
    FROM agendamento_anamnese aa
    JOIN agenda_responsavel ar ON aa.fk_data = ar.id_agenda
    JOIN usuario u ON aa.fk_aventureiro = u.id_usuario
    """,
        nativeQuery = true
    )
    fun listarHistorico(): List<Map<String, Any>>

    @Query(
        value = """
    SELECT aa.id_anamnese AS idAnamnese, ar.data_disponivel AS dataDisponivel, u.nome AS nomeUsuario
    FROM agendamento_anamnese aa
    JOIN agenda_responsavel ar ON aa.fk_data = ar.id_agenda
    JOIN usuario u ON aa.fk_aventureiro = u.id_usuario
    WHERE aa.fk_aventureiro = :idUsuario
    """,
        nativeQuery = true
    )
    fun listarHistoricoGuia(idUsuario: Int): List<Map<String, Any>>

    @Query(
        value = "SELECT id_agenda AS idAgenda, data_disponivel AS dataDisponivel, fk_responsavel AS fkResponsavel FROM agenda_responsavel WHERE fk_responsavel = :fkResponsavel",
        nativeQuery = true
    )
    fun listarDatasDisponiveisPorResponsavel(fkResponsavel: Int): List<Map<String, Any>>
}