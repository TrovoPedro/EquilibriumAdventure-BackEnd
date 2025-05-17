package com.techbridge.techbridge.controller

import com.techbridge.techbridge.entity.Comentario
import com.techbridge.techbridge.repository.ComentarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comentarios")
class ComentarioControllerJpa(val repositorioComentario: ComentarioRepository) {

    @PostMapping("/adicionar")
    fun adicionarComentario(@RequestBody novoComentario: Comentario): ResponseEntity<Comentario> {
        val comentarioCriado = repositorioComentario.save(novoComentario)
        return ResponseEntity.status(201).body(comentarioCriado)
    }

    @DeleteMapping("/excluir/{id}")
    fun excluirComentario(@PathVariable id: Int): ResponseEntity<Void> {
        return if (repositorioComentario.existsById(id)) {
            repositorioComentario.deleteById(id)
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(404).build()
        }
    }

    @PostMapping("/responder/{id}")
    fun responderComentario(
        @PathVariable id: Int,
        @RequestBody resposta: Comentario
    ): ResponseEntity<Comentario> {
        val comentarioOriginal = repositorioComentario.findById(id).orElse(null)
            ?: return ResponseEntity.status(404).build()

        val novaResposta = resposta.copy(fkAtivacaoEvento = comentarioOriginal.fkAtivacaoEvento)
        val comentarioRespondido = repositorioComentario.save(novaResposta)
        return ResponseEntity.status(201).body(comentarioRespondido)
    }

    @GetMapping("/listar")
    fun listarComentarios(): ResponseEntity<List<Comentario>> {
        val comentarios = repositorioComentario.findAll()
        return ResponseEntity.status(200).body(comentarios)
    }
}