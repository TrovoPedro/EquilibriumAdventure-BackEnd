package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.EventoGuiaEnderecoDTO
import com.techbridge.techbridge.dto.EventoRequestDTO
import com.techbridge.techbridge.dto.EventoResponseDTO
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.service.GuiaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/guia")
class GuiaController {

    @Autowired
    lateinit var eventoService: GuiaService

    @PostMapping("/cadastrar-evento")
    fun postEvento(@RequestBody novoEvento: EventoRequestDTO): ResponseEntity<Any>{
        return try {
            val eventoSalvo = eventoService.postEvento(novoEvento);
            ResponseEntity.ok(novoEvento)
        }catch (e: RuntimeException){
            ResponseEntity.status(400).body(e.message)
        }
    }

    @GetMapping("/buscar-eventos")
    fun getAllEventos(): ResponseEntity<Any>{
        return try {
            val eventosEncontrados: List<Map<String, Any>> = eventoService.getEventos();
            return ResponseEntity.status(200).body(eventosEncontrados)
        }catch (e: RuntimeException){
           return ResponseEntity.status(404).body(e.message)
        }
    }

    @GetMapping("/buscar-eventos/{nome}")
    fun getEventoPorGuia(@PathVariable nome:String): ResponseEntity<Any>{
        return try {
            val eventosEncontrados: List<Map<String, Any>> = eventoService.getEventoPorGuia(nome);
            return ResponseEntity.status(200).body(eventosEncontrados)
        }catch (e: RuntimeException){
            return ResponseEntity.status(404).body(e.message)
        }
    }
}
