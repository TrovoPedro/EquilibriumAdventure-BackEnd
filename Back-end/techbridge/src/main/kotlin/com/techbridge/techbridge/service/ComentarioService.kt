package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.ComentarioRequestDTO
import com.techbridge.techbridge.dto.ComentarioResponseDTO
import com.techbridge.techbridge.entity.Comentario
import com.techbridge.techbridge.repository.AtivacaoEventoRepository
import com.techbridge.techbridge.repository.ComentarioRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ComentarioService(
    private val comentarioRepository: ComentarioRepository,
    private val usuarioRepository: UsuarioRepository,
    private val ativacaoEventoRepository: AtivacaoEventoRepository
) {
    fun adicionarComentario(dto: ComentarioRequestDTO): ComentarioResponseDTO {
        val usuario = usuarioRepository.findById(dto.idUsuario)
            .orElseThrow { IllegalArgumentException("Usuário com ID ${dto.idUsuario} não encontrado.") }

        val ativacaoEvento = ativacaoEventoRepository.findById(dto.idAtivacaoEvento)
            .orElseThrow { IllegalArgumentException("Ativação de evento com ID ${dto.idAtivacaoEvento} não encontrada.") }

        val novoComentario = Comentario(
            texto = dto.texto,
            dataComentario = LocalDateTime.now(),
            usuario = usuario,
            ativacaoEvento = ativacaoEvento
        )

        val comentarioSalvo = comentarioRepository.save(novoComentario)

        return ComentarioResponseDTO(
            id = comentarioSalvo.id,
            texto = comentarioSalvo.texto,
            dataComentario = comentarioSalvo.dataComentario,
            nomeUsuario = comentarioSalvo.usuario?.nome ?: "Desconhecido",
            idAtivacaoEvento = comentarioSalvo.ativacaoEvento?.idAtivacao ?: 0L
        )
    }

    fun responderComentario(id: Int, dto: ComentarioRequestDTO): ComentarioResponseDTO {
        val comentarioOriginal = comentarioRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Comentário com ID $id não encontrado.") }

        val usuario = usuarioRepository.findById(dto.idUsuario)
            .orElseThrow { IllegalArgumentException("Usuário com ID ${dto.idUsuario} não encontrado.") }

        val ativacaoEvento = ativacaoEventoRepository.findById(dto.idAtivacaoEvento)
            .orElseThrow { IllegalArgumentException("Ativação de evento com ID ${dto.idAtivacaoEvento} não encontrada.") }

        val novaResposta = Comentario(
            texto = dto.texto,
            dataComentario = LocalDateTime.now(),
            usuario = usuario,
            ativacaoEvento = ativacaoEvento
        )

        val respostaSalva = comentarioRepository.save(novaResposta)

        return ComentarioResponseDTO(
            id = respostaSalva.id,
            texto = respostaSalva.texto,
            dataComentario = respostaSalva.dataComentario,
            nomeUsuario = respostaSalva.usuario?.nome ?: "Desconhecido",
            idAtivacaoEvento = respostaSalva.ativacaoEvento?.idAtivacao ?: 0L
        )
    }
    fun excluirComentario(id: Int): Boolean {
        val comentario = comentarioRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Comentário com ID $id não encontrado.") }

        comentarioRepository.delete(comentario)
        return true
    }

    fun listarComentarios(): List<ComentarioResponseDTO> {
        return comentarioRepository.findAll().map {
            ComentarioResponseDTO(
                id = it.id,
                texto = it.texto,
                dataComentario = it.dataComentario,
                nomeUsuario = it.usuario?.nome ?: "Desconhecido",
                idAtivacaoEvento = it.ativacaoEvento?.idAtivacao ?: 0L
            )
        }
    }
}