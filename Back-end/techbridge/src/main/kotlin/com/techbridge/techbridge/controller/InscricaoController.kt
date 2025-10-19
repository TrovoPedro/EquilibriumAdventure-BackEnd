package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.InscricaoDTO
import com.techbridge.techbridge.dto.VerificaInscricaoDTO
import com.techbridge.techbridge.repository.AtivacaoEventoRepository
import com.techbridge.techbridge.repository.InscricaoRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import com.techbridge.techbridge.service.InscricaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inscricoes")
@CrossOrigin(origins = ["*"])
class InscricaoController {

    @Autowired
    private lateinit var inscricaoRepository: InscricaoRepository

    @Autowired
    private lateinit var ativacaoEventoRepository: AtivacaoEventoRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    lateinit var inscricaoService: InscricaoService

    @PostMapping("/ativacaoEvento/{ativacaoId}/usuario/{usuarioId}")
    fun criarInscricao(
        @PathVariable ativacaoId: Long,
        @PathVariable usuarioId: Long
    ): ResponseEntity<InscricaoDTO> {
        val inscricao = inscricaoService.criarInscricao(ativacaoId, usuarioId)
        return ResponseEntity.ok(inscricao)
    }

    @GetMapping("/ativacaoEvento/{eventoId}")
    fun listarInscritos(@PathVariable eventoId: Long): ResponseEntity<List<InscricaoDTO>> {
        val inscritos = inscricaoService.listarInscritos(eventoId)
        return ResponseEntity.ok(inscritos)
    }

    @DeleteMapping("/{idInscricao}")
    fun removerInscrito(@PathVariable idInscricao: Long): ResponseEntity<Void> {
        inscricaoService.removerInscrito(idInscricao)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/remover/{idInscricao}")
    fun cancelarInscricao(@PathVariable idInscricao: Long): ResponseEntity<Void> {
        inscricaoService.cancelarInscricao(idInscricao)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{idInscricao}/avaliar")
    fun avaliarEvento(
        @PathVariable idInscricao: Long,
        @RequestParam avaliacao: Int
    ): ResponseEntity<Void> {
        inscricaoService.avaliarEvento(idInscricao, avaliacao)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/verificar")
    fun verificarInscricao(@RequestBody request: VerificaInscricaoDTO): ResponseEntity<Any> {
        return try {
            val jaInscrito = inscricaoService.verificarInscricao(request.idAventureiro, request.idAtivacao)
            ResponseEntity.ok(mapOf("jaInscrito" to jaInscrito))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/cancelar-inscricao/{idAventureiro}/{idAtivacao}")
    fun cancelarInscricao(
        @PathVariable idAventureiro: Long,
        @PathVariable idAtivacao: Long
    ): ResponseEntity<String> {
        return try {
            inscricaoService.cancelarInscricao(idAventureiro, idAtivacao)
            ResponseEntity.ok("Inscrição cancelada com sucesso.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}