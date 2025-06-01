package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.ComentarioRequestDTO
import com.techbridge.techbridge.dto.ComentarioResponseDTO
import com.techbridge.techbridge.service.ComentarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comentarios")
class ComentarioControllerJpa(private val comentarioService: ComentarioService) {

    @PostMapping("/adicionar")
    fun adicionarComentario(@RequestBody novoComentario: ComentarioRequestDTO): ResponseEntity<ComentarioResponseDTO> {
        val comentarioCriado = comentarioService.adicionarComentario(novoComentario)
        return ResponseEntity.status(201).body(comentarioCriado)
    }

    @DeleteMapping("/excluir/{id}")
    fun excluirComentario(@PathVariable id: Int): ResponseEntity<Void> {
        return if (comentarioService.excluirComentario(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/responder/{id}")
    fun responderComentario(
        @PathVariable id: Int,
        @RequestBody resposta: ComentarioRequestDTO
    ): ResponseEntity<ComentarioResponseDTO> {
        val comentarioRespondido = comentarioService.responderComentario(id, resposta)
            ?: return ResponseEntity.status(404).build()
        return ResponseEntity.status(201).body(comentarioRespondido)
    }

    @GetMapping("/listar")
    fun listarComentarios(): ResponseEntity<List<ComentarioResponseDTO>> {
        val comentarios = comentarioService.listarComentarios()
        return ResponseEntity.status(200).body(comentarios)
    }
}