package com.techbridge.techbridge

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/guia")
class GuiaController {

    val eventos = mutableListOf<Evento>()

    @PostMapping("/cadastrar-evento")
    fun cadastrarEvento(@RequestBody novoEvento: Evento): ResponseEntity<Evento>{
        eventos.add(novoEvento)
        return ResponseEntity.status(201).body(novoEvento);
    }

    @GetMapping("buscar-eventos")
    fun buscarEventos(): MutableList<Evento>{
        return eventos
    }

    @GetMapping("buscar-evento-especifico/{id}")
    fun buscarEventoPorId(@PathVariable id: Int): ResponseEntity<Evento> {
        if (id < eventos.size) {
            return ResponseEntity.status(201).body(eventos[id])
        }
        return ResponseEntity.status(204).build()
    }

    @PutMapping("editar-evento/{id}")
    fun editarEvento(@PathVariable id: Int, @RequestBody eventoEditado: Evento): ResponseEntity<Evento>{
        if(id < eventos.size){
            eventos[id] = eventoEditado
            return ResponseEntity.status(200).body(eventoEditado)
        }
        return ResponseEntity.status(204).build()
    }
}