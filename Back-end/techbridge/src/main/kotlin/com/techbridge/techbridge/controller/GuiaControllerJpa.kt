package com.techbridge.techbridge.controller;

import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.repository.GuiaRepository;
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guia")
public class GuiaControllerJpa(val repositorio:GuiaRepository) {

    // cadastrar evento
    @PostMapping("/cadastrar-evento")
    fun postEvento(@RequestBody novoEvento: Evento): ResponseEntity<Evento>{
        val repositorio = repositorio.save(novoEvento)
        return ResponseEntity.status(201).body(novoEvento)
    }

    // buscar todos os eventos
    @GetMapping("/buscar-todos-eventos")
    fun getAllEventos(): ResponseEntity<MutableList<Evento>> {
        val eventos = repositorio.findAll()

        return if (eventos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(eventos)
        }
    }

    // buascar um evento especifico por id
    @GetMapping("/buscar-evento-especifico/{id}")
    fun getEventoEspecifico(@PathVariable id: Int): ResponseEntity<Evento>{
        val evento = repositorio.findById(id)

        return ResponseEntity.of(evento)
    }

    // editar evento especifico por id
    @PutMapping("editar-evento/{id}")
    fun putEvento(@PathVariable id: Int, @RequestBody eventoAtualizado: Evento): ResponseEntity<Evento>{
        if(!repositorio.existsById(id)){
            return ResponseEntity.status(404).build()
        }
        eventoAtualizado.idEvento = id
        val evento = repositorio.save(eventoAtualizado)
        return ResponseEntity.status(200).body(eventoAtualizado)
    }

    // deletar evento especifico por id
    @DeleteMapping("/deletar-evento/{id}")
    fun deleteEvento(@PathVariable id: Int): ResponseEntity<Void>{
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

}