package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.dto.InformacoesPessoaisGetPerfilDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisNivelDTO
import com.techbridge.techbridge.enums.Nivel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface InformacoesPessoaisRepository : JpaRepository<InformacoesPessoais, Long> {

    @Query("""
        SELECT ip
        FROM InformacoesPessoais ip
        WHERE ip.usuario.idUsuario = :usuarioId
    """)
    fun buscarInformacoes(@Param("usuarioId") usuarioId: Long): InformacoesPessoais?

    @Query("""
        SELECT new com.techbridge.techbridge.dto.InformacoesPessoaisGetPerfilDTO(
            u.nome,
            u.email,
            u.telefoneContato,
            ip.dataNascimento,
            ip.cpf,
            ip.rg,
            ip.idioma,
            ip.contatoEmergencia,
            new com.techbridge.techbridge.dto.EnderecoDTO(
                e.rua, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep
            ),
            ip.nivel,
            ip.relatorioAnamnese
        )
        FROM InformacoesPessoais ip
        JOIN ip.usuario u
        JOIN ip.endereco e
        WHERE u.idUsuario = :usuarioId
    """)
    fun buscarInformacoesPerfil(@Param("usuarioId") usuarioId: Long): InformacoesPessoaisGetPerfilDTO?

    @Query("""
    SELECT new com.techbridge.techbridge.dto.InformacoesPessoaisNivelDTO(
        ip.nivel
    )
    FROM InformacoesPessoais ip
    JOIN ip.usuario u
    WHERE u.idUsuario = :usuarioId
""")
    fun buscarInformacoesNível(@Param("usuarioId") usuarioId: Long): InformacoesPessoaisNivelDTO?

    @Modifying
    @Query(
        value = """
            UPDATE informacoes_pessoais
            SET relatorio_anamnese = :descricao
            WHERE fk_aventureiro = :fk_aventureiro
        """,
        nativeQuery = true
    )
    fun atualizarRelatorioPorFkAventureiro(@Param("fk_aventureiro") fk_aventureiro: Long, @Param("descricao") descricao: String): Int

    @Query("""
        SELECT ip
        FROM InformacoesPessoais ip
        WHERE ip.usuario.idUsuario = :usuarioId
    """)
    fun buscarPorUsuario(@Param("usuarioId") usuarioId: Long): InformacoesPessoais?

    @Modifying
    @Query("""
        UPDATE InformacoesPessoais ip
        SET ip.nivel = :nivel
        WHERE ip.usuario.idUsuario = :usuarioId
    """)
    fun atualizarNivelPorUsuario(@Param("usuarioId") usuarioId: Long, @Param("nivel") nivel: Nivel): Int


    fun save(informacoesPessoais: InformacoesPessoais): InformacoesPessoais
}