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

    @Query("""
    SELECT DISTINCT i.ativacaoEvento.dataAtivacao
    FROM Inscricao i
    WHERE i.aventureiro.idUsuario = :aventureiroId
      AND i.ativacaoEvento.evento.nivel_dificuldade = :nivel
      AND i.ativacaoEvento.estado = 'FINALIZADO'
""")
    fun listarEventoPorNivelAventureiro(
        aventureiroId: Long,
        nivel: String
    ): List<java.time.LocalDate>

    @Query("SELECT i FROM Inscricao i WHERE i.ativacaoEvento.idAtivacao = :eventoId and i.aventureiro.idUsuario = :aventureiroId")
    fun findByAventureiroAndEvento(aventureiroId: Long, eventoId: Long): Inscricao?

    fun findByAtivacaoEventoAndAventureiro(ativacaoEvento: AtivacaoEvento, aventureiro: Usuario): Inscricao?

    fun countByAtivacaoEvento(ativacaoEvento: AtivacaoEvento): Int

    fun deleteByAventureiro_IdUsuarioAndAtivacaoEvento_IdAtivacao(usuarioId: Long, ativacaoId: Long): Int

    @Query("""
    SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END
    FROM Inscricao i
    WHERE i.aventureiro.idUsuario = :idAventureiro
    AND i.ativacaoEvento.idAtivacao = :idAtivacao
""")
    fun existsByAventureiroAndAtivacao(
        @Param("idAventureiro") idAventureiro: Long,
        @Param("idAtivacao") idAtivacao: Long
    ): Boolean


    fun existsByAventureiroAndAtivacaoEvento(aventureiro: Usuario, ativacaoEvento: AtivacaoEvento): Boolean
    fun deleteByAventureiroAndAtivacaoEvento(aventureiro: Usuario, ativacaoEvento: AtivacaoEvento)

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Inscricao i 
        WHERE i.aventureiro.idUsuario = :idAventureiro 
        AND i.ativacaoEvento.idAtivacao = :idAtivacao
    """)
    fun deleteByAventureiroAndEvento(
        @Param("idAventureiro") idAventureiro: Long,
        @Param("idAtivacao") idAtivacao: Long
    )

    @Query(
        value = """
    SELECT 
        ae.id_ativacao AS idAtivacaoEvento,
        e.nome AS nomeEvento,
        ae.data_ativacao AS dataAtivacao,
        e.img_evento AS imagemEvento
    FROM inscricao i
    JOIN ativacao_evento ae ON i.fk_ativacao_evento = ae.id_ativacao
    JOIN evento e ON ae.fk_evento = e.id_evento
    WHERE i.fk_aventureiro = :idAventureiro
    AND ae.data_ativacao >= CURRENT_TIMESTAMP
    ORDER BY ae.data_ativacao ASC
    """,
        nativeQuery = true
    )
    fun listarEventosSimples(@Param("idAventureiro") idAventureiro: Long): List<Array<Any>>

    @Query(
        value = """
        SELECT 
            i.id_inscricao,
            i.fk_aventureiro,
            i.data_inscricao,
            ae.id_ativacao AS idAtivacaoEvento,
            e.nome AS nomeEvento,
            ae.data_ativacao
        FROM inscricao i
        JOIN ativacao_evento ae ON i.fk_ativacao_evento = ae.id_ativacao
        JOIN evento e ON ae.fk_evento = e.id_evento
        WHERE i.fk_aventureiro = :idAventureiro
          AND (ae.log = 'FINALIZADO' OR ae.data_ativacao < CURRENT_TIMESTAMP)
        ORDER BY ae.data_ativacao ASC
        """,
        nativeQuery = true
    )
    fun listarHistoricoSimples(@Param("idAventureiro") idAventureiro: Long): List<Array<Any>>

    fun findByAtivacaoEvento_IdAtivacao(ativacaoId: Long): List<Inscricao>
}