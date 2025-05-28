package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.ConviteReqDTO
import com.techbridge.techbridge.dto.ConviteRequestDTO
import com.techbridge.techbridge.dto.ConviteResponseDTO
import com.techbridge.techbridge.entity.Convite
import com.techbridge.techbridge.service.ConviteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/convites")
class ConviteController(
    @Autowired open val conviteService: ConviteService
) {

    // POST /convites
    @PostMapping
    fun enviarConvite(@RequestBody conviteDTO: ConviteRequestDTO): ResponseEntity<String> {
        return try {
            val conviteSalvo = conviteService.enviarConvite(conviteDTO)
            if (conviteSalvo.isNullOrBlank()) {
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.ok(conviteSalvo)
            }
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body("Recurso não encontrado: ${e.message}")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(422).body("Parâmetro inválido: ${e.message}")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Erro inesperado ao enviar convite: ${e.message}")
        }
    }

    // PATCH /convites/{id}
    @PatchMapping("/{idConvite}")
    fun atualizarConvite(
        @PathVariable idConvite: Long,
        @RequestBody conviteAceito: ConviteReqDTO
    ): ResponseEntity<String> {
        return try {
            val conviteAtualizado = conviteService.atualizarConvite(idConvite, conviteAceito)
            if (conviteAtualizado.isNullOrBlank()) {
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.ok(conviteAtualizado)
            }
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body("Convite não encontrado: ${e.message}")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(422).body("Parâmetro inválido: ${e.message}")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Erro inesperado ao atualizar convite: ${e.message}")
        }
    }

    // GET /convites/{idAventureiro}
    @GetMapping("/{idAventureiro}")
    fun listarConvites(@PathVariable idAventureiro: Long): ResponseEntity<List<ConviteResponseDTO>> {
        return try {
            val convites = conviteService.listarConvites(idAventureiro)
            if (convites.isNullOrEmpty()) {
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.ok(convites)
            }
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(422).build()
        } catch (e: Exception) {
            ResponseEntity.status(500).build()
        }
    }
}
