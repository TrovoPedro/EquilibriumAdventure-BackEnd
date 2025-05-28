package com.techbridge.techbridge.repository

import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.dto.InformacoesPessoaisGetPerfilDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface InformacoesPessoaisRepository : JpaRepository<InformacoesPessoais, Long> {

    @Query("""
    SELECT new com.techbridge.techbridge.dto.InformacoesPessoaisGetPerfilDTO(
        u.telefone_contato,
        u.nome,
        ip.data_nascimento,
        ip.nivel,
        e.rua
    )
    FROM Usuario u, com.techbridge.techbridge.entity.InformacoesPessoais ip, Endereco e
    WHERE u.idUsuario = ip.usuario
      AND e.id_endereco = ip.endereco
      AND u.idUsuario = :idUsuario
""")
    fun buscarInformacoes(@Param("idUsuario") idUsuario: Long): InformacoesPessoaisGetPerfilDTO?

}
