package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.dto.InformacoesPessoaisGetPerfilDTO
import com.techbridge.techbridge.enums.Nivel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface InformacoesPessoaisRepository : JpaRepository<InformacoesPessoais, Long> {

    @Query("""
    SELECT new com.techbridge.techbridge.dto.InformacoesPessoaisGetPerfilDTO(
        u.telefoneContato,
        u.nome,
        ip.dataNascimento,
        ip.nivel,
        e.rua
    )
    FROM Usuario u, InformacoesPessoais ip, Endereco e
    WHERE u.idUsuario = ip.usuario
      AND e.id_endereco = ip.endereco
      AND u.idUsuario = :idUsuario
""")
    fun buscarInformacoes(@Param("idUsuario") idUsuario: Long): InformacoesPessoaisGetPerfilDTO?

    @Modifying
    @Query(
        value = """
        UPDATE informacoes_pessoais
        SET relatorio_anamnese = :descricao
        WHERE cpf = :cpf
    """,
        nativeQuery = true
    )
    fun atualizarRelatorioPorCpf(@Param("cpf") cpf: String, @Param("descricao") descricao: String): Int

    @Query("""
    SELECT ip
    FROM InformacoesPessoais ip
    WHERE ip.usuario = :usuarioId
""")
    fun buscarPorUsuario(@Param("usuarioId") usuarioId: Long): InformacoesPessoais?

    @Modifying
    @Query("""
    UPDATE InformacoesPessoais ip
    SET ip.nivel = :nivel
    WHERE ip.usuario = :usuarioId
""")
    fun atualizarNivelPorUsuario(@Param("usuarioId") usuarioId: Long, @Param("nivel") nivel: Nivel): Int
}


