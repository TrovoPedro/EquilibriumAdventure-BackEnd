package com.techbridge.techbridge.controller

import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.repository.GuiaRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/guia")
class GuiaControllerJpa(val repositorio: GuiaRepository) {

    @PostMapping("/cadastrar-evento")
    fun postEvento(@RequestBody novoEvento: Evento): ResponseEntity<Evento> {
        val eventoSalvo = repositorio.save(novoEvento)
        return ResponseEntity.status(201).body(eventoSalvo)
    }

    @GetMapping("/buscar-todos-eventos")
    fun getAllEventos(): ResponseEntity<MutableList<Evento>> {
        val eventos = repositorio.findAll()
        return if (eventos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(eventos)
        }
    }

    @GetMapping("/buscar-evento-especifico/{id}")
    fun getEventoEspecifico(@PathVariable id: Int): ResponseEntity<Evento> {
        val eventoOptional = repositorio.findById(id)

        return if (eventoOptional.isPresent) {
            ResponseEntity.ok(eventoOptional.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }


    @PutMapping("/editar-evento/{id}")
    fun putEvento(@PathVariable id: Int, @RequestBody eventoAtualizado: Evento): ResponseEntity<Evento> {
        if (!repositorio.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        eventoAtualizado.idEvento = id
        val evento = repositorio.save(eventoAtualizado)
        return ResponseEntity.status(200).body(evento)
    }

    @DeleteMapping("/deletar-evento/{id}")
    fun deleteEvento(@PathVariable id: Int): ResponseEntity<Void> {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }
}
