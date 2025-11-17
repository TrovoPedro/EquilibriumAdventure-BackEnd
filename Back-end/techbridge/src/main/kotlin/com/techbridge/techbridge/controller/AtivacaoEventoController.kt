package com.techbridge.techbridge.controller

import com.techbridge.techbridge.service.AtivacaoEventoService
import com.techbridge.techbridge.dto.AtivacaoEventoRequestDTO
import com.techbridge.techbridge.enums.EstadoEvento
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ativacoes")
class AtivacaoEventoController {

    @Autowired
    lateinit var service: AtivacaoEventoService

    @PostMapping("criar-ativacao")
    fun criar(@RequestBody dto: AtivacaoEventoRequestDTO): ResponseEntity<Any> {
        return try {
            val criado = service.criar(dto)
            ResponseEntity.status(201).body(criado)
        } catch (e: Exception) {
            ResponseEntity.status(400).body(mapOf("erro" to e.message))
        }
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody dto: AtivacaoEventoRequestDTO): ResponseEntity<Any> {
        return try {
            val atualizado = service.atualizar(id, dto)
            ResponseEntity.ok(atualizado)
        } catch (e: RuntimeException) {
            ResponseEntity.status(404).body(mapOf("erro" to e.message))
        }
    }

    @PutMapping("/{id}/estado")
    fun alterarEstado(
        @PathVariable id: Long,
        @RequestParam estado: String
    ): ResponseEntity<Any> {
        return try {
            val estadoEnum = EstadoEvento.valueOf(estado.uppercase())
            val atualizada = service.alterarEstado(id, estadoEnum)
            ResponseEntity.ok(atualizada)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(mapOf("erro" to "Estado inválido: $estado"))
        } catch (e: RuntimeException) {
            ResponseEntity.status(404).body(mapOf("erro" to e.message))
        }
    }

    @GetMapping("/media-avaliacoes/{idAtivacao}")
    fun obterMediaAvaliacoes(@PathVariable idAtivacao: Long): ResponseEntity<Any> {
        return try {
            val media = service.obterMediaAvaliacoes(idAtivacao)
            if (media > 0) {
                ResponseEntity.ok(mapOf("mediaAvaliacoes" to media))
            } else {
                ResponseEntity.ok(mapOf("mensagem" to "Esse evento ainda não tem avaliações."))
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("erro" to e.message))
        }
    }

    @GetMapping("/media-avaliacoes/evento-base/{idEventoBase}")
    fun obterMediaAvaliacoesPorEventoBase(@PathVariable idEventoBase: Long): ResponseEntity<Any> {
        return try {
            val media = service.obterMediaAvaliacoesPorEventoBase(idEventoBase)
            if (media > 0) {
                ResponseEntity.ok(mapOf("mediaAvaliacoes" to media))
            } else {
                ResponseEntity.ok(mapOf("mensagem" to "Esse evento base ainda não tem avaliações."))
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("erro" to e.message))
        }
    }

    @GetMapping("/media-avaliacoes/media-das-medias/evento-base/{idEventoBase}")
    fun obterMediaDasMediasPorEventoBase(@PathVariable idEventoBase: Long): ResponseEntity<Any> {
        return try {
            val media = service.obterMediaDasMediasPorEventoBase(idEventoBase)
            if (media > 0) {
                ResponseEntity.ok(mapOf("mediaAvaliacoes" to media))
            } else {
                ResponseEntity.ok(mapOf("mensagem" to "Esse evento base ainda não tem avaliações."))
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("erro" to e.message))
        }
    }

}
