package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.InscricaoAgendaDTO
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

    @GetMapping("/agenda/{idAventureiro}")
    fun listarEventosDoUsuario(@PathVariable idAventureiro: Long): ResponseEntity<List<InscricaoAgendaDTO>> {
        val eventos: List<InscricaoAgendaDTO> = inscricaoService.listarEventosDoAventureiro(idAventureiro)
        return ResponseEntity.ok(eventos)
    }

    @GetMapping("/agenda/historico/{idAventureiro}")
    fun listarHistoricoDoUsuario(@PathVariable idAventureiro: Long): ResponseEntity<List<InscricaoAgendaDTO>> {
        val eventos = inscricaoService.listarEventosHistoricoDoAventureiro(idAventureiro)
        return ResponseEntity.ok(eventos)
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
            val jaInscrito = inscricaoService.verificarInscricao(request.idAventureiro, request.idEvento)
            ResponseEntity.ok(mapOf("jaInscrito" to jaInscrito))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/cancelar-inscricao/{idAventureiro}/{idEvento}")
    fun cancelarInscricao(
        @PathVariable idAventureiro: Long,
        @PathVariable idEvento: Long
    ): ResponseEntity<String> {
        return try {
            inscricaoService.cancelarInscricao(idAventureiro, idEvento)
            ResponseEntity.ok("Inscrição cancelada com sucesso.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }


}