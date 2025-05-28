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
        JOIN usuario u ON e.responsavel_id = u.id_usuario
        JOIN endereco end ON e.endereco_id = end.id_endereco
    """,
        nativeQuery = true
    )
    fun buscarTodosEventosComUsuarioEndereco(): List<Map<String, Any>>

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
        JOIN usuario u ON e.responsavel_id = u.id_usuario
        JOIN endereco end ON e.endereco_id = end.id_endereco
        WHERE u.nome = :nome
    """,
        nativeQuery = true
    )
    fun buscarEventoPorGuia(@Param("nome") nome: String): List<Map<String, Any>>
}
