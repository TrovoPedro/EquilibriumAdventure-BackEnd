package com.techbridge.techbridge.repository

import AtivacaoEventoDTO
import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.Guia
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.enums.TipoUsuario
import jakarta.persistence.EnumType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GuiaRepository: JpaRepository<Guia, Int> {

    @Query("SELECT u FROM Usuario  u WHERE u.tipo_usuario = :tipo_usuario")
    fun findByTipoUsuario(@Param("tipo_usuario") tipo_usuario: TipoUsuario): List<Usuario>

    @Query("SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario and u.tipo_usuario = :tipo_usuario")
    fun findByIdAndTipo(@Param("idUsuario") idUsuario: Int, @Param("tipo_usuario") tipo_usuario: TipoUsuario): Usuario?

    @Query("""
        SELECT * FROM ativacao_evento 
        JOIN Evento ON ativacao_evento.id_ativacao = evento.id_evento  
        JOIN usuario ON evento.fk_responsavel = usuario.id_usuario
        WHERE evento.responsavel = :fkAventureiro AND usuario.fk_tipo_usuario = :fkAventureiro
        ORDER BY data_ativacao
    """, nativeQuery = true)
    fun findEventoByFkAventureiro(fkAventureiro: Int): List<Evento>

    @Query("""
    SELECT ae 
    FROM AtivacaoEvento ae
    JOIN FETCH ae.evento e
    WHERE e.id_evento = :idEvento
""")
    fun findByEventoId(@Param("idEvento") idEvento: Long): List<AtivacaoEvento>


    fun save(guia: Usuario): Usuario
}