package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.PerguntaAlternativaResponseDTO
import com.techbridge.techbridge.service.PerguntaAlternativaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/perguntas")
class PerguntaAlternativaController(private val service: PerguntaAlternativaService) {

    @GetMapping("/inicializar")
    fun inicializarPerguntas(): ResponseEntity<Void> {
        service.verificarEInserirPerguntas()
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/listar")
    fun listarPerguntas(): ResponseEntity<List<PerguntaAlternativaResponseDTO>> {
        return ResponseEntity.ok(service.listarPerguntas())
    }
}