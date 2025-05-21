package com.techbridge.techbridge.service

import com.techbridge.techbridge.entity.Comentario
import com.techbridge.techbridge.repository.ComentarioRepository
import org.springframework.stereotype.Service

@Service
class ComentarioService(private val comentarioRepository: ComentarioRepository) {

    fun adicionarComentario(novoComentario: Comentario): Comentario {
        return comentarioRepository.save(novoComentario)
    }

    fun excluirComentario(id: Int): Boolean {
        return if (comentarioRepository.existsById(id)) {
            comentarioRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun responderComentario(id: Int, resposta: Comentario): Comentario? {
        val comentarioOriginal = comentarioRepository.findById(id).orElse(null)
        if (comentarioOriginal == null) {
            println("Comentário com ID $id não encontrado.")
            return null
        }
        println("Comentário original encontrado: $comentarioOriginal")
        val novaResposta = resposta.copy(fkAtivacaoEvento = comentarioOriginal.fkAtivacaoEvento)
        return comentarioRepository.save(novaResposta)
    }

    fun listarComentarios(): List<Comentario> {
        return comentarioRepository.findAll()
    }
}