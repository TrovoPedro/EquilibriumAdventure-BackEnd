package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.AtivacaoEventoRequestDTO
import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.enums.EstadoEvento
import com.techbridge.techbridge.repository.AtivacaoEventoRepository
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.InscricaoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.sql.Time
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class AtivacaoEventoService {

    @Autowired
    lateinit var repository: AtivacaoEventoRepository

    @Autowired
    lateinit var inscricaoRepository: InscricaoRepository

    @Autowired
    lateinit var eventoRepository: EventoRepository

    private val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    fun criar(dto: AtivacaoEventoRequestDTO): AtivacaoEvento {
        val ativacao = AtivacaoEvento()

        ativacao.horaInicio = dto.horaInicio?.let { Time.valueOf(it) }
        ativacao.horaFinal = dto.horaFinal?.let { Time.valueOf(it) }
        ativacao.tempoEstimado = dto.tempoEstimado
        ativacao.limiteInscritos = dto.limiteInscritos
        ativacao.dataAtivacao = dto.dataAtivacao
        ativacao.tipo = dto.tipo
        ativacao.preco = dto.preco
        ativacao.estado = dto.estado?.let { EstadoEvento.valueOf(it.uppercase()) }

        ativacao.evento = dto.eventoId?.let {
            eventoRepository.findById(it).orElseThrow { RuntimeException("Evento não encontrado") }
        }

        val eventoId = dto.eventoId
            ?: throw IllegalArgumentException("EventoId é obrigatório para criar uma ativação")

        val ativacaoAtiva = repository.findByEventoIdAndEstadoIn(
            eventoId,
            listOf(EstadoEvento.NAO_INICIADO, EstadoEvento.EM_ANDAMENTO)
        )

        if (ativacaoAtiva != null) {
            throw RuntimeException("O evento já possui uma ativação ativa")
        }

        return repository.save(ativacao)
    }

    fun atualizar(id: Long, dto: AtivacaoEventoRequestDTO): AtivacaoEvento {
        val ativacaoExistente = repository.findById(id)
            .orElseThrow { RuntimeException("Ativação não encontrada") }

        ativacaoExistente.horaInicio = dto.horaInicio?.let { Time.valueOf(it) }
        ativacaoExistente.horaFinal = dto.horaFinal?.let { Time.valueOf(it) }
        ativacaoExistente.tempoEstimado = dto.tempoEstimado
        ativacaoExistente.limiteInscritos = dto.limiteInscritos
        ativacaoExistente.dataAtivacao = dto.dataAtivacao?.let { it}
        ativacaoExistente.tipo = dto.tipo
        ativacaoExistente.preco = dto.preco
        ativacaoExistente.estado = dto.estado?.let { EstadoEvento.valueOf(it.uppercase()) }
        ativacaoExistente.evento = dto.eventoId?.let {
            eventoRepository.findById(it).orElseThrow { RuntimeException("Evento não encontrado") }
        }

        return repository.save(ativacaoExistente)
    }

    @Transactional
    fun alterarEstado(idAtivacao: Long, novoEstado: EstadoEvento, forcar: Boolean): AtivacaoEvento {
        val ativacao = repository.findById(idAtivacao)
            .orElseThrow { RuntimeException("Ativação não encontrada: $idAtivacao") }
        val inscricoes = inscricaoRepository.findByAtivacaoEvento_IdAtivacao(idAtivacao)

        if (inscricoes.isNotEmpty() && !forcar) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Existem ${inscricoes.size} inscrições nesta ativação"
            )
        }

        if (forcar && inscricoes.isNotEmpty()) {
            inscricaoRepository.deleteAll(inscricoes)
        }
        // Atualiza o estado da ativação e salva
        ativacao.estado = novoEstado
        return repository.save(ativacao)
    }

    @Transactional
    fun excluirEventoBase(idEventoBase: Long) {
        val evento = eventoRepository.findById(idEventoBase)
            .orElseThrow {
                RuntimeException("Evento base não encontrado: $idEventoBase")
            }

        // Verifica se há ativações NÃO finalizadas
        val qtdNaoFinalizadas = repository.countAtivacoesPorEvento(idEventoBase)

        if (qtdNaoFinalizadas > 0) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Não é possível excluir o evento base. Existem $qtdNaoFinalizadas ativações não finalizadas vinculadas a ele."
            )
        }

        val ativacoesFinalizadas = repository.findFinalizadasPorEvento(idEventoBase)

        // Deletar ativações finalizadas
        if (ativacoesFinalizadas.isNotEmpty()) {
            repository.deleteAll(ativacoesFinalizadas)
        }

        // Agora pode excluir o evento base
        eventoRepository.delete(evento)
    }


    fun obterMediaAvaliacoes(idAtivacao: Long): Double {
        return repository.calcularMediaAvaliacoes(idAtivacao) ?: 0.0
    }

    fun obterMediaAvaliacoesPorEventoBase(idEventoBase: Long): Double {
        return repository.calcularMediaAvaliacoesPorEventoBase(idEventoBase) ?: 0.0
    }

    fun obterMediaDasMediasPorEventoBase(idEventoBase: Long): Double {
        return repository.calcularMediaDasMediasPorEventoBase(idEventoBase) ?: 0.0
    }
}
