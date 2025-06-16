package com.techbridge.techbridge.mapper

import com.techbridge.techbridge.dto.UsuarioRequestDTO
import com.techbridge.techbridge.dto.UsuarioResponseDTO
import com.techbridge.techbridge.entity.Usuario

fun UsuarioRequestDTO.toResponseDTO(): UsuarioResponseDTO {
    return UsuarioResponseDTO(
        nome = this.nome,
        telefone_contato = this.telefone_contato,
        email = this.email,
        descricao_guia = this.descricao_guia
    )
}

fun UsuarioResponseDTO.toEntity(): Usuario {
    return Usuario(
        nome = this.nome,
        telefoneContato = this.telefone_contato,
        email = this.email,
        descricao_guia = this.descricao_guia
    )
}
