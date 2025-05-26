package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.Guia
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GuiaRepository: JpaRepository<Guia, Int> {

    @Query("""
        SELECT * FROM ativacao_evento 
        JOIN Evento ON ativacao_evento.id_ativacao = evento.id_evento  
        JOIN usuario ON evento.fk_responsavel = usuario.id_usuario
        WHERE evento.fk_responsavel = :fkAventureiro AND usuario.fk_tipo_usuario = :fkAventureiro
        ORDER BY data_ativacao
    """, nativeQuery = true)
    fun findEventoByFkAventureiro(fkAventureiro: Int): List<Evento>
}