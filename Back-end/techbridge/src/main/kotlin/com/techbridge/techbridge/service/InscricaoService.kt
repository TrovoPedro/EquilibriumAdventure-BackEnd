package com.techbridge.techbridge.service

import InscricaoAgendaDTO
import com.techbridge.techbridge.dto.InscricaoDTO
import com.techbridge.techbridge.entity.Inscricao
import com.techbridge.techbridge.enums.Nivel
import com.techbridge.techbridge.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class InscricaoService {

    @Autowired
    private lateinit var eventoRepository: EventoRepository

    @Autowired
    lateinit var inscricaoRepository: InscricaoRepository

    @Autowired
    lateinit var ativacaoEventoRepository: AtivacaoEventoRepository

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    lateinit var informacoesPessoaisRepository: InformacoesPessoaisRepository

    @Transactional
    fun criarInscricao(ativacaoId: Long, usuarioId: Long): InscricaoDTO {
        val ativacao = ativacaoEventoRepository.findById(ativacaoId)
            .orElseThrow { RuntimeException("Ativação de evento não encontrada para ID $ativacaoId") }

        val usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow { RuntimeException("Usuário com ID $usuarioId não encontrado.") }

        inscricaoRepository.findByAtivacaoEventoAndAventureiro(ativacao, usuario)?.let {
            throw RuntimeException("Usuário já está inscrito neste evento.")
        }

        val totalInscritos = inscricaoRepository.countByAtivacaoEvento(ativacao)
        ativacao.limiteInscritos?.let { limite ->
            if (totalInscritos >= limite) {
                throw RuntimeException("Limite de inscritos alcançado para este evento.")
            }
        }

        val totalInscricoesUsuario = inscricaoRepository.findByAventureiro_IdUsuario(usuarioId).size
        val informacoesPessoais = informacoesPessoaisRepository.buscarPorUsuario(usuarioId)
            ?: throw RuntimeException("Informações pessoais não encontradas para o usuário com ID $usuarioId.")

        if (
            informacoesPessoais.cpf.isNullOrBlank() ||
            informacoesPessoais.contatoEmergencia.isNullOrBlank() ||
            informacoesPessoais.dataNascimento == null ||
            informacoesPessoais.endereco?.rua.isNullOrBlank()
        ) {
            throw RuntimeException("Preencha todas as informações pessoais antes de se inscrever em um evento.")
        }
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
            }

            else -> {
                throw RuntimeException("Nível do usuário inválido ou não definido.")
            }
        }
        val novaInscricao = Inscricao(
            aventureiro = usuario,
            ativacaoEvento = ativacao,
            dataInscricao = LocalDateTime.now()
        )
        val inscricaoSalva = inscricaoRepository.save(novaInscricao)
        return InscricaoDTO(
            idInscricao = inscricaoSalva.idInscricao,
            idAtivacaoEvento = ativacao.idAtivacao,
            idUsuario = usuario.idUsuario,
            dataInscricao = inscricaoSalva.dataInscricao
        )
    }

    @Transactional
    fun cancelarInscricaoAventureiro(usuarioId: Long, ativacaoId: Long) {
        inscricaoRepository.deleteByAventureiro_IdUsuarioAndAtivacaoEvento_IdAtivacao(usuarioId, ativacaoId)
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

    fun verificarInscricao(idAventureiro: Long, idAtivacao: Long): Boolean {
        val usuario = usuarioRepository.findById(idAventureiro)
            .orElseThrow { IllegalArgumentException("Aventureiro não encontrado") }
        val ativacao = ativacaoEventoRepository.findById(idAtivacao)
            .orElseThrow { IllegalArgumentException("Ativação de evento não encontrada") }

        return inscricaoRepository.existsByAventureiroAndAtivacaoEvento(usuario, ativacao)
    }

    @Transactional
    fun cancelarInscricao(idAventureiro: Long, idAtivacao: Long) {
        val usuario = usuarioRepository.findById(idAventureiro)
            .orElseThrow { IllegalArgumentException("Aventureiro não encontrado") }

        val ativacao = ativacaoEventoRepository.findById(idAtivacao)
            .orElseThrow { IllegalArgumentException("Ativação de evento não encontrada") }

        if (!inscricaoRepository.existsByAventureiroAndAtivacaoEvento(usuario, ativacao)) {
            throw IllegalArgumentException("Inscrição não encontrada para esta ativação e aventureiro.")
        }

        inscricaoRepository.deleteByAventureiroAndAtivacaoEvento(usuario, ativacao)
    }

    fun listarEventosDoAventureiro(idAventureiro: Long): List<InscricaoAgendaDTO> {
        return inscricaoRepository.listarEventosSimples(idAventureiro).map { arr ->
            InscricaoAgendaDTO(
                idInscricao = null,
                idAtivacaoEvento = (arr[0] as Number).toLong(),
                idUsuario = idAventureiro,
                dataInscricao = null,
                nomeEvento = arr[1] as String,
                dataAtivacao = arr[2] as Date,
                imagemEventoBytes = arr[3] as? ByteArray
            )
        }
    }

    fun listarEventosHistoricoDoAventureiro(idAventureiro: Long): List<InscricaoAgendaDTO> {
        return inscricaoRepository.listarHistoricoSimples(idAventureiro).map { arr ->
            InscricaoAgendaDTO(
                idInscricao = (arr[0] as Number).toLong(),
                idUsuario = (arr[1] as Number).toLong(),
                dataInscricao = arr[2] as? Date,
                idAtivacaoEvento = (arr[3] as Number).toLong(),
                nomeEvento = arr[4] as String,
                dataAtivacao = arr[5] as? Date,
                imagemEventoBytes = null
            )
        }
    }
}