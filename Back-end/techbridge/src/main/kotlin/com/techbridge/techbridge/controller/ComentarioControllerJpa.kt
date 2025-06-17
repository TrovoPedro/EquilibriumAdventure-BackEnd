package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.ComentarioRequestDTO
import com.techbridge.techbridge.dto.ComentarioResponseDTO
import com.techbridge.techbridge.service.ComentarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/comentarios")
class ComentarioControllerJpa(private val comentarioService: ComentarioService) {

    @PostMapping("/adicionar")
    fun adicionarComentario(@RequestBody novoComentario: ComentarioRequestDTO): ResponseEntity<ComentarioResponseDTO> {
        return try {
            val comentarioCriado = comentarioService.adicionarComentario(novoComentario)
            ResponseEntity.status(201).body(comentarioCriado)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(null)
        } catch (e: Exception) {
            ResponseEntity.status(500).body(null)
        }
    }

    @DeleteMapping("/excluir/{id}")
    fun excluirComentario(@PathVariable id: Int): ResponseEntity<Void> {
        return try {
            if (comentarioService.excluirComentario(id)) {
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).build()
        }
    }

    @PostMapping("/responder/{id}")
    fun responderComentario(
        @PathVariable id: Int,
        @RequestBody resposta: ComentarioRequestDTO
    ): ResponseEntity<ComentarioResponseDTO> {
        return try {
            val comentarioRespondido = comentarioService.responderComentario(id, resposta)
                ?: return ResponseEntity.status(404).build()
            ResponseEntity.status(201).body(comentarioRespondido)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(null)
        } catch (e: Exception) {
            ResponseEntity.status(500).body(null)
        }
    }

    @GetMapping("/listar")
    fun listarComentarios(): ResponseEntity<List<ComentarioResponseDTO>> {
        return try {
            val comentarios = comentarioService.listarComentarios()
            ResponseEntity.status(200).body(comentarios)
        } catch (e: Exception) {
            ResponseEntity.status(500).build()
        }
    }
}