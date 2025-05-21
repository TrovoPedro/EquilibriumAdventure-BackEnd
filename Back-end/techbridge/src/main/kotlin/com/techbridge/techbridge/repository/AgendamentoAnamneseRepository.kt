package com.techbridge.techbridge.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import com.techbridge.techbridge.entity.AgendamentoAnamnese
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
interface AgendamentoAnamneseRepository : JpaRepository<AgendamentoAnamnese, Int> {

    fun findByFkAventureiro(fkAventureiro: Int): List<AgendamentoAnamnese>

    @Query(
        value = """
        SELECT aa.id_anamnese AS idAnamnese, ar.data_disponivel AS dataDisponivel, u.nome AS nomeUsuario
        FROM agendamento_anamnese aa
        JOIN agenda_responsavel ar ON aa.fk_data = ar.id_agenda
        JOIN usuario u ON aa.fk_aventureiro = u.id_usuario
        WHERE ar.data_disponivel > CURRENT_TIMESTAMP
    """,
        nativeQuery = true
    )
    fun listarHistorico(): List<Map<String, Any>>

    @Modifying
    @Query(
        value = """
        UPDATE informacoes_pessoais
        SET relatorio_anamnese = :descricao
        WHERE cpf = :cpf
    """,
        nativeQuery = true
    )
    fun atualizarRelatorio(@Param("cpf") cpf: String, @Param("descricao") descricao: String): Int

    @Query(
        value = "SELECT id_agenda AS idAgenda, data_disponivel AS dataDisponivel FROM agenda_responsavel",
        nativeQuery = true
    )
    fun listarDatasDisponiveis(): List<Map<String, Any>>
}