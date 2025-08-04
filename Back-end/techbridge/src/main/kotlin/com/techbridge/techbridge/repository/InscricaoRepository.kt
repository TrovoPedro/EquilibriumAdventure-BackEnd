package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.entity.Inscricao
import com.techbridge.techbridge.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface InscricaoRepository : JpaRepository<Inscricao, Long> {

    @Modifying
    @Transactional
    @Query(
        value = """
        INSERT INTO inscricao (fk_aventureiro, fk_ativacao_evento, data_inscricao)
        VALUES (:aventureiro, :evento, :dataInscricao)
    """, nativeQuery = true
    )
    fun inserirInscricao(
        @Param("aventureiro") aventureiroId: Long,
        @Param("evento") eventoId: Long,
        @Param("dataInscricao") dataInscricao: java.time.LocalDateTime
    )

    fun findByAventureiro_IdUsuario(aventureiro: Long): List<Inscricao>

    @Query("SELECT i FROM Inscricao i WHERE i.ativacaoEvento.idAtivacao = :eventoId and i.aventureiro.idUsuario = :aventureiroId")
    fun findByAventureiroAndEvento(aventureiroId: Long, eventoId: Long): Inscricao?

    fun findByAtivacaoEventoAndAventureiro(ativacaoEvento: AtivacaoEvento, aventureiro: Usuario): Inscricao?

    fun countByAtivacaoEvento(ativacaoEvento: AtivacaoEvento): Int
}