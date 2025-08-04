package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.InscricaoDTO
import com.techbridge.techbridge.entity.Inscricao
import com.techbridge.techbridge.enums.Nivel
import com.techbridge.techbridge.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class InscricaoService {

    @Autowired
    lateinit var inscricaoRepository: InscricaoRepository

    @Autowired
    lateinit var ativacaoEventoRepository: AtivacaoEventoRepository

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    lateinit var informacoesPessoaisRepository: InformacoesPessoaisRepository

    @Transactional
    fun criarInscricao(eventoId: Long, usuarioId: Long): InscricaoDTO {
        val evento = ativacaoEventoRepository.findById(eventoId)
            .orElseThrow { RuntimeException("Evento com ID $eventoId não encontrado.") }
        val usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow { RuntimeException("Usuário com ID $usuarioId não encontrado.") }

        inscricaoRepository.findByAtivacaoEventoAndAventureiro(evento, usuario)?.let {
            throw RuntimeException("Usuário já está inscrito neste evento.")
        }

        val totalInscritos = inscricaoRepository.countByAtivacaoEvento(evento)
        evento.limiteInscritos?.let { limite ->
            if (totalInscritos >= limite) {
                throw RuntimeException("Limite de inscritos alcançado para este evento.")
            }
        }

        val totalInscricoesUsuario = inscricaoRepository.findByAventureiro_IdUsuario(usuarioId).size
        val informacoesPessoais = informacoesPessoaisRepository.buscarPorUsuario(usuarioId)
            ?: throw RuntimeException("Informações pessoais não encontradas para o usuário com ID $usuarioId.")

        when (informacoesPessoais.nivel) {
            Nivel.EXPLORADOR -> {
                if (totalInscricoesUsuario + 1 >= 5) {
                    informacoesPessoaisRepository.atualizarNivelPorUsuario(usuarioId, Nivel.AVENTUREIRO)
                }
            }
            Nivel.AVENTUREIRO -> {
                if (totalInscricoesUsuario + 1 >= 10) {
                    informacoesPessoaisRepository.atualizarNivelPorUsuario(usuarioId, Nivel.DESBRAVADOR)
                }
            }
            Nivel.DESBRAVADOR -> {
                // Nível máximo, nenhuma ação necessária
            }
            else -> {
                throw RuntimeException("Nível do usuário inválido ou não definido.")
            }
        }

        val novaInscricao = Inscricao(
            ativacaoEvento = evento,
            aventureiro = usuario,
            dataInscricao = LocalDateTime.now()
        )
        val inscricaoSalva = inscricaoRepository.save(novaInscricao)

        return InscricaoDTO(
            idInscricao = inscricaoSalva.idInscricao,
            idAtivacaoEvento = inscricaoSalva.ativacaoEvento.idAtivacao,
            idUsuario = inscricaoSalva.aventureiro.idUsuario,
            dataInscricao = inscricaoSalva.dataInscricao
        )
    }

    @Transactional
    fun cancelarInscricao(idInscricao: Long) {
        if (!inscricaoRepository.existsById(idInscricao)) {
            throw RuntimeException("Inscrição com ID $idInscricao não encontrada.")
        }
        inscricaoRepository.deleteById(idInscricao)
    }

    fun listarInscritos(eventoId: Long): List<InscricaoDTO> {
        val evento = ativacaoEventoRepository.findById(eventoId)
            .orElseThrow { RuntimeException("Evento com ID $eventoId não encontrado.") }

        val inscritos = inscricaoRepository.findAll()
            .filter { it.ativacaoEvento.idAtivacao == evento.idAtivacao }

        return inscritos.map {
            InscricaoDTO(
                idInscricao = it.idInscricao,
                idAtivacaoEvento = it.ativacaoEvento.idAtivacao,
                idUsuario = it.aventureiro.idUsuario,
                dataInscricao = it.dataInscricao
            )
        }
    }

    @Transactional
    fun removerInscrito(idInscricao: Long) {
        if (!inscricaoRepository.existsById(idInscricao)) {
            throw RuntimeException("Inscrição com ID $idInscricao não encontrada.")
        }
        inscricaoRepository.deleteById(idInscricao)
    }

    @Transactional
    fun avaliarEvento(idInscricao: Long, avaliacao: Int) {
        if (avaliacao < 1 || avaliacao > 5) {
            throw IllegalArgumentException("A avaliação deve estar entre 1 e 5.")
        }

        val inscricao = inscricaoRepository.findById(idInscricao)
            .orElseThrow { RuntimeException("Inscrição com ID $idInscricao não encontrada.") }

        val inscricaoAtualizada = Inscricao(
            idInscricao = inscricao.idInscricao,
            aventureiro = inscricao.aventureiro,
            ativacaoEvento = inscricao.ativacaoEvento,
            dataInscricao = inscricao.dataInscricao,
            avaliacao = avaliacao
        )

        inscricaoRepository.save(inscricaoAtualizada)
    }

}