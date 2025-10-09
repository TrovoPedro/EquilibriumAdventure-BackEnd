package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.AtivacaoEventoRequestDTO
import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.enums.EstadoEvento
import com.techbridge.techbridge.repository.AtivacaoEventoRepository
import com.techbridge.techbridge.repository.EventoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class AtivacaoEventoService {

    @Autowired
    lateinit var repository: AtivacaoEventoRepository

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

    fun alterarEstado(id: Long, novoEstado: EstadoEvento): AtivacaoEvento {
        val ativacaoExistente = repository.findById(id)
            .orElseThrow { RuntimeException("Ativação não encontrada") }

        ativacaoExistente.estado = novoEstado
        return repository.save(ativacaoExistente)
    }
}
