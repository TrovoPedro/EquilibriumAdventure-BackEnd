package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.EventoRequestDTO
import com.techbridge.techbridge.entity.AgendaResponsavel
import com.techbridge.techbridge.entity.Comentario
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.enums.TipoUsuario
import com.techbridge.techbridge.repository.AgendaResponsavelRepository
import com.techbridge.techbridge.repository.AgendamentoAnamneseRepository
import com.techbridge.techbridge.repository.AtivacaoEventoRepository
import com.techbridge.techbridge.repository.AventureiroRepository
import com.techbridge.techbridge.repository.ComentarioRepository
import com.techbridge.techbridge.repository.ConviteRepository
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository
import com.techbridge.techbridge.repository.InscricaoRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import com.techbridge.techbridge.service.GuiaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/guia")
class GuiaControllerJpa(
    val repositorioAventureiro: AventureiroRepository,
    val repositorioInformacoes: InformacoesPessoaisRepository,
    val repositorioConvite: ConviteRepository,
    private val repositorioEvento: EventoRepository,
    val repositorioInscricao: InscricaoRepository,
    val repositorioAtivacaoEvento: AtivacaoEventoRepository,
    val repositorioUsuario: UsuarioRepository,
    val repositorioComentario: ComentarioRepository,
    val repositorioAgendaResponsavel: AgendaResponsavelRepository,
    val repositorioAgendaAnamnese: AgendamentoAnamneseRepository
) {

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
        val eventosEncontrados: List<Map<String, Any>> = eventoService.getEventos();
        if (eventosEncontrados.isEmpty()) {
        return ResponseEntity.status(204).build()
        }

            return ResponseEntity.status(200).body(eventosEncontrados)
    }

    @GetMapping("/buscar-eventos/{nome}")
    fun getEventoPorGuia(@PathVariable nome:String): ResponseEntity<Any>{

            val eventosEncontrados: List<Map<String, Any>> = eventoService.getEventoPorGuia(nome);
        if (eventosEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(200).body(eventosEncontrados)



    }

    @GetMapping("Adicionar-comentario/{idEvento}/{idAventureiro}")
    fun postComentario(
        @PathVariable idEvento: Long,
        @PathVariable idAventureiro: Int,
        @RequestBody comentario: String
    ): ResponseEntity<String> {
        val evento = repositorioAtivacaoEvento.findById(idEvento)
        val aventureiro = repositorioAventureiro.findByIdAndTipo(idAventureiro, TipoUsuario.AVENTUREIRO)

        if (evento.isPresent && aventureiro !== null) {
            val comentario = Comentario(
                texto = comentario,
                usuario = aventureiro,
                ativacaoEvento = evento.get()
            )
            repositorioComentario.save(comentario)

            return ResponseEntity.status(200).body("Comentário adicionado com sucesso!")
        } else {
            return ResponseEntity.status(404).body("Evento ou aventureiro não encontrado.")
        }
    }

    @DeleteMapping("/deletar-comentario/{idComentario}")
    fun deleteComentario(@PathVariable idComentario: Int): ResponseEntity<Void> {
        val comentario = repositorioComentario.findById(idComentario)
        if (comentario.isPresent) {
            repositorioComentario.deleteById(idComentario)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
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
    fun getEventosAtivos(): ResponseEntity<MutableList<Evento>> {
        val eventosAtivos = repositorioAtivacaoEvento.findAll()
        return (if (eventosAtivos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(eventosAtivos)
        }) as ResponseEntity<MutableList<Evento>>
    }

    @GetMapping("buscar-eventos-base")
    fun getEventosBase(): ResponseEntity<MutableList<Evento>> {
        val eventosAtivos = repositorioEvento.findAll()
        return if (eventosAtivos.isEmpty()) {
            ResponseEntity.status(204).build()
        } else {
            ResponseEntity.status(200).body(eventosAtivos)
        }
    }

    @DeleteMapping("/deletar-inscricao-evento/{eventoSelecionado}/{usuarioSelecionado}")
    fun deleteInscricao(@PathVariable eventoSelecionado: Long,@PathVariable usuarioSelecionado: Long):ResponseEntity<Void>{
        if (repositorioAtivacaoEvento.existsById(eventoSelecionado)) {
            val inscricao = repositorioInscricao.findByAventureiroAndEvento(usuarioSelecionado, eventoSelecionado)
            if (inscricao != null) {
                repositorioInscricao.delete(inscricao)
            } else {
                return ResponseEntity.status(202).build()
            }
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(404).build()
    }

    @PostMapping("/adicionar-agenda-disponivel")
    fun postAgenda(@RequestBody dataAgenda: AgendaResponsavel?): ResponseEntity<Void> {
        if (dataAgenda == null) {
            return ResponseEntity.status(400).build()
        } else {
            val agendaSalva = repositorioAgendaResponsavel.save(dataAgenda)

            return ResponseEntity.status(200).build()
        }
    }

    @GetMapping("/buscar-historico-agenda/{usuarioSelecionado}")
    fun getHistoricoAgenda(@PathVariable usuarioSelecionado: Int): ResponseEntity<out List<Any>?> {
        val agenda = repositorioAgendaAnamnese.listarHistoricoGuia(usuarioSelecionado)

        if (agenda.isEmpty()) {
            return ResponseEntity.status(204).build()
        } else {
            return ResponseEntity.status(200).body(agenda)
        }
    }

    @GetMapping("/buscar-datas-disponiveis/{usuarioSelecionado}")
    fun getDatasDisponiveis(@PathVariable usuarioSelecionado: Long): ResponseEntity<out List<Any>?> {
        val datasDisponiveis = repositorioAgendaResponsavel.findByFkresponsavelAndDataDisponivel(usuarioSelecionado)
        if (datasDisponiveis.isEmpty()) {
            return ResponseEntity.status(204).build()
        } else {
            return ResponseEntity.status(200).body(datasDisponiveis)
        }
    }
/*
    @GetMapping("/buscar-eventos-ativos")
    fun getHistoricoEventoAtivos(): ResponseEntity<List<AtivacaoEvento>?> {
        val eventosAtivos = repositorioAtivacaoEvento.()
        if (eventosAtivos.isEmpty()) {
            return ResponseEntity.status(204).build()
        } else {
            return ResponseEntity.status(200).body(eventosAtivos)
        }
    }
*/
    @GetMapping("/buscar-informacoes-pessoais/{usuarioSelecionado}")
    fun getInformacoesPessoas(@PathVariable usuarioSelecionado: Long): ResponseEntity<out Any?> {
        val informacoes = repositorioInformacoes.buscarInformacoes(usuarioSelecionado)
        if (informacoes == null) {
            return  ResponseEntity.status(204).build()
        } else {
            return ResponseEntity.status(200).body(informacoes)
        }
    }
}
