package com.techbridge.techbridge.service

import com.techbridge.techbridge.entity.AgendamentoAnamnese
import com.techbridge.techbridge.repository.AgendamentoAnamneseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class AgendamentoAnamneseService(private val agendamentoRepository: AgendamentoAnamneseRepository) {

    fun listarHistorico(): List<Map<String, Any>> {
        return agendamentoRepository.listarHistorico()
    }

    fun listarDatasDisponiveis(): List<Map<String, Any>> {
        return agendamentoRepository.listarDatasDisponiveis()
    }

    @Transactional
    fun atualizarRelatorio(cpf: String, descricao: String): Int {
        return agendamentoRepository.atualizarRelatorio(cpf, descricao)
    }

    fun salvarAgendamento(agendamento: AgendamentoAnamnese): AgendamentoAnamnese {
        return agendamentoRepository.save(agendamento)
    }

    fun listarPorAventureiro(fkAventureiro: Int): List<AgendamentoAnamnese> {
        return agendamentoRepository.findByFkAventureiro(fkAventureiro)
    }
}