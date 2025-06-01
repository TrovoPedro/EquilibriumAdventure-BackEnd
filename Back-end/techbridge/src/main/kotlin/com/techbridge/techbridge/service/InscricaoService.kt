package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.InscricaoDTO
import com.techbridge.techbridge.entity.Inscricao
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.InscricaoRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class InscricaoService {

    @Autowired
    lateinit var inscricaoRepository: InscricaoRepository

    @Autowired
    lateinit var eventoRepository: EventoRepository

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    fun criarInscricao(eventoId: Long, usuarioId: Long): InscricaoDTO {
        val evento = eventoRepository.findById(eventoId)
            .orElseThrow { RuntimeException("Evento não encontrado") }
        val usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow { RuntimeException("Usuário não encontrado") }

        val novaInscricao = Inscricao(
            evento = evento,
            aventureiro = usuario,
            dataInscricao = LocalDateTime.now()
        )
        val inscricaoSalva = inscricaoRepository.save(novaInscricao)

        return InscricaoDTO(
            idInscricao = inscricaoSalva.id_inscricao,
            idEvento = inscricaoSalva.evento.id_evento,
            idUsuario = inscricaoSalva.aventureiro.idUsuario,
            dataInscricao = inscricaoSalva.dataInscricao
        )
    }

    fun cancelarInscricao(idInscricao: Long) {
        if (!inscricaoRepository.existsById(idInscricao)) {
            throw RuntimeException("Inscrição não encontrada")
        }
        inscricaoRepository.deleteById(idInscricao)
    }

    fun listarInscritos(eventoId: Long): List<InscricaoDTO> {
        val evento = eventoRepository.findById(eventoId)
            .orElseThrow { RuntimeException("Evento não encontrado") }

        val inscritos = inscricaoRepository.findAll()
            .filter { it.evento.id_evento == evento.id_evento }

        return inscritos.map {
            InscricaoDTO(
                idInscricao = it.id_inscricao,
                idEvento = it.evento.id_evento,
                idUsuario = it.aventureiro.idUsuario,
                dataInscricao = it.dataInscricao
            )
        }
    }

    fun removerInscrito(idInscricao: Long) {
        if (!inscricaoRepository.existsById(idInscricao)) {
            throw RuntimeException("Inscrição não encontrada")
        }
        inscricaoRepository.deleteById(idInscricao)
    }
}