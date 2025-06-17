package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.InscricaoDTO
import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.entity.Inscricao
import com.techbridge.techbridge.repository.AtivacaoEventoRepository
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
    lateinit var ativacaoEventoRepository: AtivacaoEventoRepository

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    fun criarInscricao(eventoId: Long, usuarioId: Long): InscricaoDTO {
        val evento = ativacaoEventoRepository.findById(eventoId)
            .orElseThrow { RuntimeException("Evento não encontrado") }
        val usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow { RuntimeException("Usuário não encontrado") }

        val inscricaoExistente = inscricaoRepository.findByAtivacaoEventoAndAventureiro(evento, usuario)
        if (inscricaoExistente != null) {
            throw RuntimeException("Usuário já inscrito neste evento")
        }

        val totalInscritos = inscricaoRepository.countByAtivacaoEvento(evento)
        val limiteInscritos = evento.limiteInscritos
        if (limiteInscritos != null && totalInscritos >= limiteInscritos) {
            throw RuntimeException("Limite de inscritos alcançado para este evento")
        }

        val novaInscricao = Inscricao(
            ativacaoEvento = evento,
            aventureiro = usuario,
            dataInscricao = LocalDateTime.now()
        )
        val inscricaoSalva = inscricaoRepository.save(novaInscricao)

        return InscricaoDTO(
            idInscricao = inscricaoSalva.id_inscricao,
            idAtivacaoEvento = inscricaoSalva.ativacaoEvento.idAtivacao,
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
        val evento = ativacaoEventoRepository.findById(eventoId)
            .orElseThrow { RuntimeException("Evento não encontrado") }

        val inscritos = inscricaoRepository.findAll()
            .filter { it.ativacaoEvento.idAtivacao == evento.idAtivacao }

        return inscritos.map {
            InscricaoDTO(
                idInscricao = it.id_inscricao,
                idAtivacaoEvento = it.ativacaoEvento.idAtivacao,
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