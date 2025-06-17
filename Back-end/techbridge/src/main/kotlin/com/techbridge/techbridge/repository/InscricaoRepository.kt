package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.AtivacaoEvento
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import com.techbridge.techbridge.entity.Inscricao
import com.techbridge.techbridge.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InscricaoRepository : JpaRepository<Inscricao, Long> {

    @Query("""
        INSERT INTO inscricao (aventureiro, ativacaoEvento, dataInscricao)
        VALUES (:aventureiro, :evento, :dataInscricao)
    """, nativeQuery = true)
    fun inserirInscricao(
        @Param("aventureiro") aventureiroId: Long,
        @Param("evento") eventoId: Long,
        @Param("dataInscricao") dataInscricao: java.time.LocalDateTime
    )

<<<<<<< HEAD
    fun findByAventureiro_IdUsuario(aventureiro: Long): List<Inscricao>

    @Query("delete from Inscricao i where i.id_inscricao = :id")
    fun deleteByIdInscricao(id: Long?)

    @Query("SELECT i FROM Inscricao i WHERE i.evento.id_evento = :eventoId and i.aventureiro.idUsuario = :aventureiroId")
    fun  findByAventureiroAndEvento(aventureiroId: Long, eventoId: Long): Inscricao?
=======
    fun findByAtivacaoEventoAndAventureiro(ativacaoEvento: AtivacaoEvento, aventureiro: Usuario): Inscricao?

    fun countByAtivacaoEvento(ativacaoEvento: AtivacaoEvento): Int

>>>>>>> e2e3fcb979e3c68a4ec32aa0475136c12ed82436
}