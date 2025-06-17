/*package com.techbridge.techbridge.controller

import com.techbridge.techbridge.entity.Aventureiro
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/aventureiros")
class AventureiroController {

    val aventureiro = mutableListOf<Aventureiro>()

    @PostMapping("/cadastrar")
    fun cadastrar(@RequestBody novoAventureiro: Aventureiro):ResponseEntity<Aventureiro> {
        aventureiro.add(novoAventureiro)
        return ResponseEntity.status(201).body(novoAventureiro)
    }

    @PutMapping("/editar-informacoes/{id}")
    fun editarUsuario(@PathVariable id:Int, @RequestBody aventureiroEditado: Aventureiro):ResponseEntity<Aventureiro>{
        aventureiro[id] = aventureiroEditado;
        return ResponseEntity.status(201).body(aventureiroEditado);
    }

    @DeleteMapping("/deletar/{id}")
    fun deletarUsuario(@PathVariable id: Int): ResponseEntity<Unit>{
        aventureiro.removeAt(id)
        return ResponseEntity.status(204).build();
    }

}*/

package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.InscricaoDTO
import com.techbridge.techbridge.entity.Comentario
import com.techbridge.techbridge.entity.Convite
import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.entity.Inscricao
import com.techbridge.techbridge.repository.AtivacaoEventoRepository

import com.techbridge.techbridge.repository.AventureiroRepository
import com.techbridge.techbridge.repository.ConviteRepository
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository
import com.techbridge.techbridge.repository.InscricaoRepository
import com.techbridge.techbridge.enums.TipoUsuario
import com.techbridge.techbridge.repository.ComentarioRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import com.techbridge.techbridge.service.InscricaoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.Optional

@RestController
@RequestMapping("/Aventureiro")
class AventureiroController(
    val repositorioAventureiro: AventureiroRepository,
    val repositorioInformacoes: InformacoesPessoaisRepository,
    val repositorioConvite: ConviteRepository,
    private val repositorioEvento: EventoRepository,
    val repositorioInscricao: InscricaoRepository,
    val repositorioAtivacaoEvento: AtivacaoEventoRepository,
    val repositorioUsuario: UsuarioRepository,
    val repositorioComentario: ComentarioRepository,
) {
    val tipoguia = TipoUsuario.AVENTUREIRO

    @PutMapping("/preencher-informacoes-restantes/{id}")
    fun putInformacoesRestantes(
        @PathVariable id: Long,
        @RequestBody informacoesfaltando: InformacoesPessoais
    ): ResponseEntity<InformacoesPessoais?> {
        val aventureiro = repositorioAventureiro.findByIdUsuario(id)
        if (aventureiro !== null) {
            val informacoes = repositorioInformacoes.findById(id)
            if (informacoes.isPresent) {
                var informacoesAtualizadas = informacoes.get()
                informacoesAtualizadas = informacoesfaltando
                repositorioInformacoes.save(informacoesAtualizadas)
                return ResponseEntity.status(200).body(informacoesAtualizadas)
            } else {
                return ResponseEntity.status(404).body(null)
            }
        } else {
            return ResponseEntity.status(204).build()
        }

    }

    //mexer nessa função se algum método de convite der errado
    @PostMapping("/enviar-convite/{id}/{idConvidado}")
    fun postConvite(
        @PathVariable id: Int,
        @PathVariable idConvidado: Long,
        @RequestBody convite: Convite
    ): ResponseEntity<Convite?> {
        var aventureiro = repositorioAventureiro.findByIdAndTipo(id, TipoUsuario.AVENTUREIRO)
        var convidado = repositorioUsuario.findById(idConvidado).get()
        if (aventureiro !== null) {
            convite.aventureiro = aventureiro
            convite.convidado = convidado
            repositorioConvite.save(convite)
            return ResponseEntity.status(200).body(convite)
        } else {
            return ResponseEntity.status(404).build()
        }
    }

    @PatchMapping("responder-convite/{id}/{resposta}")
    fun patchConvite(@PathVariable id: Long, @PathVariable resposta: Int): ResponseEntity<Boolean> {
        val convite = repositorioConvite.findById(id)

        var respostaValida: Boolean

        if (resposta == 1) {
            respostaValida = true
        } else {
            respostaValida = false
        }

        if (convite.isPresent) {
            val conviteAtualizado = convite.get()
            conviteAtualizado.conviteAceito = respostaValida
            repositorioConvite.save(conviteAtualizado)
            return ResponseEntity.status(200).body(respostaValida)
        } else {
            return ResponseEntity.status(404).build()
        }
    }

    val tipoAventureiro = TipoUsuario.AVENTUREIRO

    @PostMapping("/inscrever-evento/{idEvento}/{idAventureiro}")
    fun inscreverEvento(@PathVariable idEvento: Long, @PathVariable idAventureiro: Int): ResponseEntity<Inscricao> {
        val aventureiro = repositorioAventureiro.findByIdAndTipo(idAventureiro, TipoUsuario.AVENTUREIRO)
        val usuario = repositorioUsuario.findById(idAventureiro.toLong())
        val evento = repositorioEvento.findById(idEvento)
        val ativacaoEvento = repositorioAtivacaoEvento.findById(idEvento)
        val dataInscricao = LocalDateTime.now()

        //if (aventureiro !== null && evento.isPresent) {
        val inscricao = Inscricao(evento = evento.get(), aventureiro = usuario.get(), dataInscricao = dataInscricao)
        repositorioInscricao.save(inscricao)
        val inscricaoSalva = InscricaoService().criarInscricao(idEvento, idAventureiro.toLong())
        return ResponseEntity.status(201).body(inscricao)
        //} else {
        //    return ResponseEntity.status(404).body(null)
    }


    @DeleteMapping("/cancelar-inscricao/{idInscricao}")
    fun deleteInscricao(@PathVariable idInscricao: Long): ResponseEntity<Void> {
        if (repositorioInscricao.existsById(idInscricao)) {
            repositorioInscricao.deleteById(idInscricao)
            return ResponseEntity.status(204).build()
        }
        return ResponseEntity.status(404).build()
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

}

