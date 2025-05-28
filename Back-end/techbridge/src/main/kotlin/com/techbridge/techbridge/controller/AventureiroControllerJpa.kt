package com.techbridge.techbridge.controller

import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.entity.InscricaoEvento
import com.techbridge.techbridge.repository.AventureiroRepository
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/Aventureiro")
class AventureiroControllerJpa(
    val repositorioAventureiro: AventureiroRepository,
    val repositorioInformacoes: InformacoesPessoaisRepository,
    val repositorioConvite: ConviteRepository,
    val repositorioEvento: EventoRepository,

    ) {

    @PutMapping("/preencher-informacoes-restantes/{id}")
    fun putInformacoesRestantes(@PathVariable id: Long, @RequestBody informacoesfaltando: InformacoesPessoais ): ResponseEntity<InformacoesPessoais?> {
        val aventureiro = repositorioAventureiro.findById(id)
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

    @PostMapping("/enviar-convite/{id}/{idConvidado}")
    fun postConvite(@PathVariable id: Long, @PathVariable idConvidado: Long, @RequestBody convite: Convite): ResponseEntity<Convite?> {
        val aventureiro = repositorioAventureiro.findById(id)
        val convidado = repositorioAventureiro.findById(idConvidado)
        if (aventureiro !== null) {
            convite.aventureiro = aventureiro.get()
            convite.convidado = convidado.get()
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

        if (resposta == 1){
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

    @PostMapping("/inscrever-evento/{idEvento}/{idAventureiro}")
    fun inscreverEvento(@PathVariable idEvento: Long, @PathVariable idAventureiro: Int): ResponseEntity<Inscricao> {
        val aventureiro = repositorioAventureiro.findById(idAventureiro)
        val evento = repositorioEvento.findById(idEvento)
        if (aventureiro.isPresent) {
            val inscricao = Inscricao(evento = idEvento, aventureiro = aventureiro)
            repositorioInscricao.save(inscricao)
            return ResponseEntity.status(201).body(Inscricao)
        } else {
            return ResponseEntity.status(404).body(Inscricao)
        }
    }

    @DeleteMapping("/cancelar-inscricao/{idInscricao}")
    fun deleteInscricao(@PathVariable idInscricao: Long): ResponseEntity<Void> {
        if (repositorioInscricao.existsById(idInscricao)) {
            repositorioInscricao.deleteById(idInscricao)
    return ResponseEntity.status(204).build()
        }
    return ResponseEntity.status(404).build()
    }



}