package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.ConviteRequestDTO
import com.techbridge.techbridge.dto.ConviteResponseDTO
import com.techbridge.techbridge.entity.Convite
import com.techbridge.techbridge.repository.ConviteRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class ConviteService(private val conviteRepository: ConviteRepository,
    private val usuarioRepository: UsuarioRepository) {


    fun enviarConvite(convite: ConviteRequestDTO): ConviteRequestDTO {
        // Buscar o aventureiro (obrigatório)
        val aventureiro = usuarioRepository.findUsuariosById_usuario(convite.fkUsuario)
            ?: throw RuntimeException("Usuário não encontrado")

        // Buscar o convidado (opcional)
        val convidado = convite.fkConvidado?.let {
            usuarioRepository.findById(it.toInt()).orElse(null)
        }

        // Salvar entidade
        val novaInformacao = conviteRepository.save(convite.toEntity(aventureiro, convidado))

        // Retornar DTO (reconstruindo os dados)
        return ConviteRequestDTO(
            dataConvite = novaInformacao.dataConvite.toString(),
            emailConvidado = novaInformacao.emailConvidado,
            conviteAceito = novaInformacao.conviteAceito,
            fkUsuario = novaInformacao.aventureiro.id_usuario?.toLong(),
            fkConvidado = novaInformacao.fkConvidado?.id_usuario?.toLong()
        )
    }


    fun atualizarConvite(id: Long, atualizacao: Boolean): Convite {
        val convite = conviteRepository.findById(id)
            .orElseThrow { RuntimeException("Convite não encontrado") }

        convite.conviteAceito = atualizacao
        return conviteRepository.save(convite)
    }

        fun listarConvite(): List<ConviteResponseDTO> {
        return conviteRepository.findAll().map {
            ConviteResponseDTO(
                dataConvite = it.dataConvite.toString(),
                fkUsuario = it.aventureiro.id_usuario,
                nomeAventureiro = it.aventureiro.nome.toString(),
            )
        }
    }
}