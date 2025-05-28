package com.techbridge.techbridge.controller
import com.techbridge.techbridge.entity.Agenda
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.entity.InscricaoEvento
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.GuiaRepository
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/guia")
class GuiaControllerJpa(
    val repositorioGuia: GuiaRepository,
    val repositorioEvento: EventoRepository,
    val repositorioEventoAtivo: EventoAtivoRepository,
    val repositorioInscricao: InscricaoEventoRepository,
    val repositorioGuiaAgenda: AgendaResponsavelRepository,
    val repositorioInformacoesPessoas: InformacoesPessoaisRepository
){

    @PostMapping("/cadastrar-evento")
    fun postEvento(@RequestBody novoEvento: Evento): ResponseEntity<Evento> {
        val eventoSalvo = repositorioEvento.save(novoEvento)
        return ResponseEntity.status(201).body(eventoSalvo)
    }

    @GetMapping("/buscar-todos-eventos")
    fun getAllEventos(): ResponseEntity<MutableList<Evento>> {
        val eventos = repositorioEvento.findAll()
        return if (eventos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(eventos)
        }
    }

    @GetMapping("/buscar-evento-especifico/{id}")
    fun getEventoEspecifico(@PathVariable id: Long): ResponseEntity<Evento> {
        val eventoOptional = repositorioEvento.findById(id)

        return if (eventoOptional.isPresent) {
            ResponseEntity.ok(eventoOptional.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/editar-evento/{id}")
    fun putEvento(@PathVariable id: Long, @RequestBody eventoAtualizado: Evento): ResponseEntity<Evento> {
        if (!repositorioEvento.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        eventoAtualizado.id_evento = id
        val evento = repositorioEvento.save(eventoAtualizado)
        return ResponseEntity.status(200).body(evento)
    }

    @DeleteMapping("/deletar-evento/{id}")
    fun deleteEvento(@PathVariable id: Long): ResponseEntity<Void> {
        if (repositorioEvento.existsById(id)) {
            repositorioEvento.deleteById(id)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
    }

    @GetMapping("buscar-eventos-ativos")
    fun getEventosAtivos(): ResponseEntity<List<ativacaoEvento>> {
        val eventosAtivos = repositorioEventoAtivo.findAll()
        if (eventosAtivos.isEmpty()) {
            return ResponseEntity.status(204).build()
        }else {
            return ResponseEntity.status(200).body(eventosAtivos)
        }

    }

    @GetMapping("buscar-eventos-base")
    fun getEventosBase(): ResponseEntity<List<Evento>> {
        val eventosAtivos = repositorioEventoAtivo.findAll()
        if (eventosAtivos.isEmpty()) {
           return ResponseEntity.status(204).build()
        } else {
             return ResponseEntity.status(200).body(eventosAtivos)
        }
    }

    @DeleteMapping("/deletar-inscricao-evento/{eventoSelecionado}/{usuarioSelecionado}")
    fun deleteInscricao(@PathVariable eventoSelecionado: Int,@PathVariable usuarioSelecionado: Int):ResponseEntity<Void>{
        if (repositorioEventoAtivo.existsById(eventoSelecionado)) {
            val inscricao = repositorioInscricao.findByFkUsuarioAndFkAtivacaoEvento(usuarioSelecionado, eventoSelecionado)
            if (inscricao != null) {
                repositorioInscricao.deleteById(inscricao.id_inscricao)
            } else {
                return ResponseEntity.status(202).build()
            }
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(404).build()
    }

    @PostMapping("/adicionar-agenda")
    fun postAgenda(@RequestBody dataAgenda: Agenda): ResponseEntity<Void> {
        if (dataAgenda == null) {
            return ResponseEntity.status(400).build()
        } else {
        val agendaSalva = repositorioGuiaAgenda.save(dataAgenda)

        return ResponseEntity.status(200).build()
        }
    }

    @GetMapping("/buscar-historico-agenda")
    fun getHistoricoAgenda(): ResponseEntity<List<Agenda>?> {
        val agenda = repositorioGuiaAgenda.findByFkResponsavelOrderByDataDisponivelAsc()

        if (agenda.isEmpty()) {
            return ResponseEntity.status(204).build()
        } else {
            return ResponseEntity.status(200).body(agenda)
        }
    }

    @GetMapping("/buscar-eventos-inscritos/{usuarioSelecionado}")
    fun getHistoricoEventoAtivos(@PathVariable usuarioSelecionado: Int): ResponseEntity<List<Evento?>?> {
        val eventos = repositorioEventoAtivo.findByFkUsuario(usuarioSelecionado)
        if (eventos.isEmpty()) {
            return ResponseEntity.status(204).build()
        } else {
            return ResponseEntity.status(200).body(eventos)
        }
    }

    @GetMapping("/buscar-informacoes-pessoas/{usuarioSelecionado}")
    fun getInformacoesPessoas(@PathVariable usuarioSelecionado: Int): ResponseEntity<List<InformacoesPessoais>?> {
        val informacoes = repositorioInformacoesPessoas.findByFkAventureiro(usuarioSelecionado)
        if (informacoes.isEmpty()) {
          return  ResponseEntity.status(204).build()
        } else {
           return ResponseEntity.status(200).body(informacoes)
        }
    }


}



