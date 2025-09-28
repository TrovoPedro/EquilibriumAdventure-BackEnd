package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.PerguntaComRespostaDTO
import com.techbridge.techbridge.dto.RespostaAventureiroDTO
import com.techbridge.techbridge.entity.RespostaAventureiro
import com.techbridge.techbridge.enums.Nivel
import com.techbridge.techbridge.service.RespostaAventureiroService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/respostas-aventureiro")
class RespostaAventureiroController(private val respostaService: RespostaAventureiroService) {

    @PostMapping("/salvar")
    fun salvarRespostas(@RequestBody respostas: List<RespostaAventureiroDTO>): ResponseEntity<Void> {
        return try {
            respostaService.salvarRespostas(respostas)
            ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        } catch (e: IllegalStateException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @GetMapping("/perguntas-com-respostas")
    fun listarPerguntasComRespostas(@RequestParam idUsuario: Long): ResponseEntity<List<PerguntaComRespostaDTO>> {
        return try {
            val lista = respostaService.listarPerguntasComRespostas(idUsuario)
            ResponseEntity.ok(lista)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @GetMapping("/listar")
    fun listarRespostas(@RequestParam idUsuario: Long): ResponseEntity<List<RespostaAventureiro>> {
        return try {
            val respostas = respostaService.listarRespostas(idUsuario)
            ResponseEntity.ok(respostas)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @PostMapping("/calcular-nivel/{idUsuario}")
    fun calcularNivel(@PathVariable idUsuario: Long): ResponseEntity<Nivel> {
        return try {
            val nivel = respostaService.calcularEAtualizarNivelUsuario(idUsuario)
            ResponseEntity.ok(nivel)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}