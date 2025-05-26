package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.Evento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GuiaEventoAtivoRepository: JpaRepository<ativacaoEvento, Int> {

    fun findByAtivoTrue():MutableList<Evento>?

    override fun findAll(): List<ativacaoEvento>

    @Query("""
        SELECT * FROM ativacao_evento join Evento on ativacao_evento.id_ativacao=evento.id_evento  
        join usuario on evento.fk_responsavel = usuario.id_usuario
        order by data_ativacao WHERE evento.fk_responsavel = :fkAventureiro and usuario.fk_tipo_usuario = :fkAventureiro
    """, nativeQuery = true)
    fun findEventoByFkAventureiro(fkAventureiro: Int): List<Evento>

    fun findById(id: Int): Evento?

}