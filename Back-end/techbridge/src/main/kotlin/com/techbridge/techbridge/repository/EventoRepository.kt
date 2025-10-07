package com.techbridge.techbridge.repository

import com.techbridge.techbridge.dto.EventoGuiaEnderecoDTO

import com.techbridge.techbridge.entity.Evento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface EventoRepository: JpaRepository<Evento, Long> {
    @Query(
        value = """
        SELECT 
            e.id_evento,
            e.nome AS nome_evento,
            e.descricao,
            e.nivel_dificuldade,
            e.distancia_km,
            u.nome AS nome_responsavel,
            end.rua
        FROM evento e
        JOIN usuario u ON e.fk_responsavel = u.id_usuario
        JOIN endereco end ON e.fk_endereco = end.id_endereco
    """,
        nativeQuery = true
    )
    fun listarEventosComResponsavelERua(): List<Map<String, Any>>

    @Query(
        value = """
        SELECT 
            e.id_evento,
            e.nome AS nome_evento,
            e.descricao,
            e.nivel_dificuldade,
            e.distancia_km,
            u.nome AS nome_responsavel,
            end.rua
        FROM evento e
        JOIN usuario u ON e.fk_responsavel = u.id_usuario
        JOIN endereco end ON e.fk_endereco = end.id_endereco
        WHERE e.fk_responsavel = :id
    """,
        nativeQuery = true
    )
    fun buscarEventoPorGuia(@Param("id") id: Long): List<Map<String, Any>>
    fun findByNome(nome: String?): Evento?

    @Query(
        value = """
        SELECT 
            e.id_evento,
            e.nome AS nome_evento,
            e.descricao,
            e.nivel_dificuldade,
            e.distancia_km,
            u.nome AS nome_responsavel,
            end.rua,
            a.data_ativacao,
            a.hora_inicio,
            a.hora_final,
            a.tipo,
            a.preco,
            a.log
        FROM evento e
        JOIN usuario u ON e.fk_responsavel = u.id_usuario
        JOIN endereco end ON e.fk_endereco = end.id_endereco
        JOIN ativacao_evento a ON e.id_evento = a.fk_evento
        WHERE e.fk_responsavel = :id
    """,
        nativeQuery = true
    )
    fun buscarEventoAtivoPorGuia(@Param("id") id: Long): List<Map<String, Any>>

}
