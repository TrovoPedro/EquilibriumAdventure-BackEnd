package com.techbridge.techbridge

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("administrador")
class AdministradorController : GuiaController(){
    val guias = mutableListOf<Guia>();

    @PostMapping("/cadastrar-guia")
    fun cadastrarGuia(@RequestBody novoGuia: Guia): ResponseEntity<Guia> {
        guias.add(novoGuia);
        return ResponseEntity.status(201).body(novoGuia);
    }

    @GetMapping("/buscar-guias")
    fun buscarGuias(): MutableList<Guia> {
        return guias;
    }

    @GetMapping("/buscar-guia-especifico/{id}")
    fun buscarGuiaPorId(@PathVariable id: Int): ResponseEntity<Guia>{
        if(id < guias.size){
            return ResponseEntity.status(200).body(guias[id]);
        }
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/editar-guia/{id}")
    fun editarGuia(@PathVariable id: Int, @RequestBody guiaEditado: Guia): ResponseEntity<Guia>{
        if(id < guias.size){
            guias[id] = guiaEditado;
            return ResponseEntity.status(200).body(guiaEditado);
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/cadastrar-evento")
    override fun cadastrarEvento(novoEvento: Evento): ResponseEntity<Evento> {
        return super.cadastrarEvento(novoEvento)
    }

    @GetMapping("/buscar-eventos")
    override fun buscarEventos(): MutableList<Evento> {
        return EventoRepository.eventos;
    }

    @GetMapping("/buscar-evento-especifico/{id}")
    override fun buscarEventoPorId(id: Int): ResponseEntity<Evento> {
        return super.buscarEventoPorId(id)
    }

    @PutMapping("/editar-evento/{id}")
    override fun editarEvento(id: Int, eventoEditado: Evento): ResponseEntity<Evento> {
        return super.editarEvento(id, eventoEditado)
    }

}