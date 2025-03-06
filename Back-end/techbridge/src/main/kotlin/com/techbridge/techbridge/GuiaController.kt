package com.techbridge.techbridge

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/guia")
class GuiaController() {

    @PostMapping("/cadastrar-evento")
    fun cadastrarEvento(@RequestBody novoEvento: Evento): ResponseEntity<Evento>{
        EventoRepository.eventos.add(novoEvento);
        return ResponseEntity.status(201).body(novoEvento);
    }

    @GetMapping("buscar-eventos")
    fun buscarEventos(): MutableList<Evento>{
        return EventoRepository.eventos;
    }

    @GetMapping("buscar-evento-especifico/{id}")
    fun buscarEventoPorId(@PathVariable id: Int): ResponseEntity<Evento> {
        if (id < EventoRepository.eventos.size) {
            return ResponseEntity.status(201).body(EventoRepository.eventos[id])
        }
        return ResponseEntity.status(204).build()
    }

    @PutMapping("editar-evento/{id}")
    fun editarEvento(@PathVariable id: Int, @RequestBody eventoEditado: Evento): ResponseEntity<Evento>{
        if(id < EventoRepository.eventos.size){
            EventoRepository.eventos[id] = eventoEditado
            return ResponseEntity.status(200).body(eventoEditado)
        }
        return ResponseEntity.status(204).build()
    }
}