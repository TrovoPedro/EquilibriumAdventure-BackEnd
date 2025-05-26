package com.techbridge.techbridge.controller

import com.techbridge.techbridge.entity.AgendamentoAnamnese
import com.techbridge.techbridge.service.AgendamentoAnamneseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/agendamentos")
class AgendamentoAnamneseControllerJpa(private val agendamentoService: AgendamentoAnamneseService) {

    @PostMapping("/agendar")
    fun agendarAnamnese(@RequestBody agendamento: AgendamentoAnamnese): ResponseEntity<AgendamentoAnamnese> {
        val novoAgendamento = agendamentoService.salvarAgendamento(agendamento)
        return ResponseEntity.status(201).body(novoAgendamento)
    }

    @GetMapping("/historico")
    fun visualizarHistorico(): ResponseEntity<List<Map<String, Any>>> {
        val resultados = agendamentoService.listarHistorico()
        return if (resultados.isNotEmpty()) {
            ResponseEntity.status(200).body(resultados)
        } else {
            ResponseEntity.status(204).build()
        }
    }

    @PatchMapping("/gerar-relatorio")
    fun gerarRelatorio(
        @RequestParam cpf: String,
        @RequestParam descricao: String
    ): ResponseEntity<String> {
        val linhasAfetadas = agendamentoService.atualizarRelatorio(cpf, descricao)
        return if (linhasAfetadas > 0) {
            ResponseEntity.status(200).body("Relatório atualizado com sucesso.")
        } else {
            ResponseEntity.status(404).body("CPF não encontrado.")
        }
    }

    @GetMapping("/datas-disponiveis")
    fun listarDatasDisponiveis(): ResponseEntity<List<Map<String, Any>>> {
        val resultados = agendamentoService.listarDatasDisponiveis()
        return if (resultados.isNotEmpty()) {
            ResponseEntity.status(200).body(resultados)
        } else {
            ResponseEntity.status(204).build()
        }
    }

    @GetMapping("/por-aventureiro/{fkAventureiro}")
    fun listarPorAventureiro(@PathVariable fkAventureiro: Int): ResponseEntity<List<AgendamentoAnamnese>> {
        val agendamentos = agendamentoService.listarPorAventureiro(fkAventureiro)
        return if (agendamentos.isNotEmpty()) {
            ResponseEntity.status(200).body(agendamentos)
        } else {
            ResponseEntity.status(204).build()
        }
    }
}