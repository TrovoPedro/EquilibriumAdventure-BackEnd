package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.UsuarioRequestDTO
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.service.AdministradorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/administrador")
class AdministradorControllerJpa{

    @Autowired
    lateinit var administradorService: AdministradorService

    @PostMapping("/cadastrar-evento")
    fun postEvento(@RequestBody novoEvento: Evento): Any? {
        val eventoSalvo = administradorService.salvarEvento(novoEvento)
        try {
            return ResponseEntity.status(201).body(novoEvento)
        }catch (e:RuntimeException){
            return e.message
        }
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }

        if (eventoAtualizado.nome.isNullOrEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }

        eventoAtualizado.id_evento = id

        val evento = repositorio.save(eventoAtualizado)

        return ResponseEntity.status(HttpStatus.OK).body(evento)
    }


    @DeleteMapping("/deletar-evento/{id}")
    fun deleteEvento(@PathVariable id: Int): ResponseEntity<Void> {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

    // GUIAS

    @PostMapping("/cadastrar-guia")
    fun postGuia(@RequestBody novoGuia: UsuarioRequestDTO): Any? {
        val guiaSalvo = administradorService.salvarGuia(novoGuia)
        try {
            return ResponseEntity.status(201).body(novoGuia)
        }catch (e:RuntimeException){
            return e.message
        }
    }

    @GetMapping("/buscar-guias")
    fun getAllGuias(): ResponseEntity<List<Usuario>> {
        val guias = repositorioGuia.findByTipoUsuario(tipoGuia)

        return if (guias.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(guias)
        }
    }

    @GetMapping("/buscar-guia-especifico/{id}")
    fun getGuiaEspecifico(@PathVariable id: Int): ResponseEntity<Usuario> {
        val guiaOptional = repositorioGuia.findById(id)

        return if (guiaOptional.isPresent) {
            val guia = guiaOptional.get()
            if (guia.fk_tipo_usuario == 2) {
                ResponseEntity.ok(guia)
            } else {
                ResponseEntity.status(403).build()
            }
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/editar-guia/{id}")
    fun putGuia(@PathVariable id: Int, @RequestBody guiaAtualizado: Usuario): ResponseEntity<Usuario> {
        val guiaOptional = repositorioGuia.findById(id)

        return if (guiaOptional.isPresent) {
            val guiaExistente = guiaOptional.get()
            if (guiaExistente.fk_tipo_usuario == 2) {
                guiaAtualizado.id_usuario = id
                guiaAtualizado.fk_tipo_usuario = 2
                val guiaSalvo = repositorioGuia.save(guiaAtualizado)
                ResponseEntity.ok(guiaSalvo)
            } else {
                ResponseEntity.status(403).build()
            }
        } else {
            ResponseEntity.notFound().build()
        }
    }


    @DeleteMapping("/deletar-guia/{id}")
    fun deleteGuia(@PathVariable id: Int): ResponseEntity<Void> {
        val guiaOptional = repositorioGuia.findById(id)

        return if (guiaOptional.isPresent) {
            val guiaExistente = guiaOptional.get()
            if (guiaExistente.fk_tipo_usuario == 2) {
                repositorioGuia.deleteById(id)
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.status(403).build()
            }
        } else {
            ResponseEntity.notFound().build()
        }
    }
}