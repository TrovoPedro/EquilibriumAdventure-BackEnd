package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.enums.EstadoEvento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AtivacaoEventoRepository : JpaRepository<AtivacaoEvento, Long>{
    @Query("SELECT a FROM AtivacaoEvento a WHERE a.evento.id_evento = :eventoId AND a.estado IN :estados")
    fun findByEventoIdAndEstadoIn(
        @Param("eventoId") eventoId: Long,
        @Param("estados") estados: List<EstadoEvento>
    ): AtivacaoEvento?

    @Query("SELECT a FROM AtivacaoEvento a WHERE a.evento.id_evento = :eventoId")
    fun findByEventoId(@Param("eventoId") eventoId: Long): AtivacaoEvento?

    @Query("SELECT AVG(i.avaliacao) FROM Inscricao i WHERE i.ativacaoEvento.id = :idAtivacao")
    fun calcularMediaAvaliacoes(@Param("idAtivacao") idAtivacao: Long): Double?
}
