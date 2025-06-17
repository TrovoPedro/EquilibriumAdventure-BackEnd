package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.InscricaoDTO
import com.techbridge.techbridge.service.InscricaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inscricoes")
class InscricaoController {

    @Autowired
    lateinit var inscricaoService: InscricaoService

    @PostMapping("/ativacaoEvento/{eventoId}/usuario/{usuarioId}")
    fun criarInscricao(
        @PathVariable eventoId: Long,
        @PathVariable usuarioId: Long
    ): ResponseEntity<InscricaoDTO> {
        val inscricao = inscricaoService.criarInscricao(eventoId, usuarioId)
        return ResponseEntity.ok(inscricao)
    }

    @GetMapping("/ativacaoEvento/{eventoId}")
    fun listarInscritos(@PathVariable eventoId: Long): ResponseEntity<List<InscricaoDTO>> {
        val inscritos = inscricaoService.listarInscritos(eventoId)
        return ResponseEntity.ok(inscritos)
    }

    // Guia pode remover um inscrito
    @DeleteMapping("/{idInscricao}")
    fun removerInscrito(@PathVariable idInscricao: Long): ResponseEntity<Void> {
        inscricaoService.removerInscrito(idInscricao)
        return ResponseEntity.noContent().build()
    }

    // Usuário pode cancelar sua inscrição
    @DeleteMapping("/remover/{idInscricao}")
    fun cancelarInscricao(@PathVariable idInscricao: Long): ResponseEntity<Void> {
        inscricaoService.cancelarInscricao(idInscricao)
        return ResponseEntity.noContent().build()
    }
}