package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.AnamneseRequestDTO
import com.techbridge.techbridge.dto.AnamneseResponseDTO
import com.techbridge.techbridge.service.AgendamentoAnamneseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/agendamentos")
class AgendamentoAnamneseControllerJpa(private val agendamentoService: AgendamentoAnamneseService) {

    @PostMapping("/agendar")
    fun agendarAnamnese(@RequestBody agendamento: AnamneseRequestDTO): ResponseEntity<AnamneseResponseDTO> {
        return try {
            val novoAgendamento = agendamentoService.salvarAgendamento(agendamento)
            ResponseEntity.status(201).body(novoAgendamento)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(null)
        } catch (e: Exception) {
            ResponseEntity.status(500).body(null)
        }
    }

    @GetMapping("/historico")
    fun visualizarHistorico(): ResponseEntity<List<Map<String, Any>>> {
        return try {
            val resultados = agendamentoService.listarHistorico()
            if (resultados.isNotEmpty()) {
                ResponseEntity.status(200).body(resultados)
            } else {
                ResponseEntity.status(204).build()
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).build()
        }
    }

    @PatchMapping("/gerar-relatorio")
    fun gerarRelatorio(
        @RequestParam cpf: String,
        @RequestParam descricao: String
    ): ResponseEntity<String> {
        return try {
            val linhasAfetadas = agendamentoService.atualizarRelatorio(cpf, descricao)
            if (linhasAfetadas > 0) {
                ResponseEntity.status(200).body("Relatório atualizado com sucesso.")
            } else {
                ResponseEntity.status(404).body("CPF não encontrado.")
            }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body("Parâmetros inválidos.")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Erro interno no servidor.")
        }
    }

    @GetMapping("/datas-disponiveis")
    fun listarDatasDisponiveis(): ResponseEntity<List<Map<String, Any>>> {
        return try {
            val resultados = agendamentoService.listarDatasDisponiveis()
            if (resultados.isNotEmpty()) {
                ResponseEntity.status(200).body(resultados)
            } else {
                ResponseEntity.status(204).build()
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).build()
        }
    }

    @GetMapping("/por-aventureiro/{fkAventureiro}")
    fun listarPorAventureiro(@PathVariable fkAventureiro: Int): ResponseEntity<List<AnamneseResponseDTO>> {
        return try {
            val agendamentos = agendamentoService.listarPorAventureiro(fkAventureiro)
            if (agendamentos.isNotEmpty()) {
                ResponseEntity.status(200).body(agendamentos)
            } else {
                ResponseEntity.status(204).build()
            }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(null)
        } catch (e: Exception) {
            ResponseEntity.status(500).body(null)
        }
    }
}