package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.ConviteReqDTO
import com.techbridge.techbridge.dto.ConviteRequestDTO
import com.techbridge.techbridge.dto.ConviteResponseDTO
import com.techbridge.techbridge.entity.Convite
import com.techbridge.techbridge.repository.ConviteRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class ConviteService {

    @Autowired
    lateinit var conviteRepository: ConviteRepository

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    fun enviarConvite(conviteRequestDTO: ConviteRequestDTO): String {
        val usuarioConvidado = usuarioRepository.findByEmail(conviteRequestDTO.emailConvidado)
            ?: throw RuntimeException("Usuário convidado não encontrado")

        val usuarioAventureiro = usuarioRepository.findById(conviteRequestDTO.aventureiro)
            .orElseThrow { RuntimeException("Usuário aventureiro não encontrado") }

        val convite = conviteRequestDTO.toEntity(usuarioAventureiro, usuarioConvidado)
        conviteRepository.save(convite)

        return """
        Convite enviado com sucesso!
        Data do convite: ${convite.dataConvite}
        Email do convidado: ${convite.emailConvidado}
    """.trimIndent()
    }

    fun listarConvites(aventureiro: Long): List<ConviteResponseDTO> {
        return conviteRepository.findByAventureiroIdUsuario(aventureiro).map { convite ->
            ConviteResponseDTO(
                dataConvite = convite.dataConvite.toString(),
                fkUsuario = convite.aventureiro?.idUsuario ?: 0,
                nomeAventureiro = convite.aventureiro?.nome ?: "Desconhecido",
                conviteAceito = convite.conviteAceito ?: false
            )
        }
    }



    fun atualizarConvite(idConvite: Long, conviteDTO: ConviteReqDTO): String {
        val conviteOptional = conviteRepository.findById(idConvite)

        if (conviteOptional.isPresent) {
            val convite = conviteOptional.get()
            convite.conviteAceito = conviteDTO.conviteAceito
            conviteRepository.save(convite)

            return """
            Convite atualizado com sucesso!
            ID do convite: ${convite.idConvite}
            Convite aceito: ${conviteDTO.conviteAceito}
        """.trimIndent()
        } else {
            throw RuntimeException("Convite não encontrado ou não atualizado")
        }
    }
}
