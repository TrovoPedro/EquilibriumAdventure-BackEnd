package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.*
import com.techbridge.techbridge.service.DashboardService
import com.techbridge.techbridge.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/dashboard")
class DashboardController(
    private val service: DashboardService,
    private val usuarioService: UsuarioService
) {

    private fun validarUsuario(usuarioId: Long) {
        usuarioService.getUsuario(usuarioId) // Lança exceção se o usuário não for encontrado
    }

    @GetMapping("/eventos-ativos-futuros")
    fun getEventosAtivosFuturos(@RequestParam usuarioId: Long): ResponseEntity<Int> {
        validarUsuario(usuarioId)
        val result = service.getEventosAtivosFuturos(usuarioId) ?: 0
        return ResponseEntity.ok(result)
    }

    @GetMapping("/taxa-ocupacao-media")
    fun getTaxaOcupacaoMedia(@RequestParam usuarioId: Long): ResponseEntity<List<TaxaOcupacaoDTO>> {
        validarUsuario(usuarioId)
        val result = service.getTaxaOcupacaoMedia(usuarioId)
        return ResponseEntity.ok(result.ifEmpty { emptyList() })
    }

    @GetMapping("/usuarios-novos-frequentes")
    fun getUsuariosNovosFrequentes(@RequestParam usuarioId: Long): ResponseEntity<Map<String, Int>> {
        validarUsuario(usuarioId)
        val result = service.getUsuariosNovosFrequentes(usuarioId)
        return ResponseEntity.ok(result.ifEmpty { mapOf("Novo" to 0, "Frequente" to 0) })
    }

    @GetMapping("/top-cidades")
    fun getTopCidades(@RequestParam usuarioId: Long): ResponseEntity<List<CidadeDTO>> {
        validarUsuario(usuarioId)
        val result = service.getTopCidades(usuarioId)
        return ResponseEntity.ok(result.ifEmpty { emptyList() })
    }

    @GetMapping("/ranking-eventos")
    fun getRankingEventos(@RequestParam usuarioId: Long): ResponseEntity<List<EventoRankingDTO>> {
        validarUsuario(usuarioId)
        val result = service.getRankingEventos(usuarioId)
        return ResponseEntity.ok(result.ifEmpty { emptyList() })
    }

    @GetMapping("/palavras-comentarios")
    fun getPalavrasComentarios(@RequestParam usuarioId: Long): ResponseEntity<List<PalavraDTO>> {
        validarUsuario(usuarioId)
        val result = service.getPalavrasComentarios(usuarioId)
        return ResponseEntity.ok(result.ifEmpty { emptyList() })
    }

    @GetMapping("/tendencias-ano")
    fun getTendenciasAno(@RequestParam usuarioId: Long): ResponseEntity<List<TendenciaAnoDTO>> {
        validarUsuario(usuarioId)
        val result = service.getTendenciasAno(usuarioId)
        return ResponseEntity.ok(result.ifEmpty { emptyList() })
    }

    @GetMapping("/tendencias-mes")
    fun getTendenciasMes(@RequestParam usuarioId: Long): ResponseEntity<List<TendenciaMesDTO>> {
        validarUsuario(usuarioId)
        val result = service.getTendenciasMes(usuarioId)
        return ResponseEntity.ok(result.ifEmpty { emptyList() })
    }

    @GetMapping("/tendencias-dia")
    fun getTendenciasDia(@RequestParam usuarioId: Long): ResponseEntity<List<TendenciaDiaDTO>> {
        validarUsuario(usuarioId)
        val result = service.getTendenciasDia(usuarioId)
        return ResponseEntity.ok(result.ifEmpty { emptyList() })
    }

    @GetMapping("/inscricao-limite")
    fun getInscricaoLimite(@RequestParam usuarioId: Long): ResponseEntity<List<InscricaoLimiteDTO>> {
        validarUsuario(usuarioId)
        val result = service.getInscricaoLimite(usuarioId)
        return ResponseEntity.ok(result.ifEmpty { emptyList() })
    }
}