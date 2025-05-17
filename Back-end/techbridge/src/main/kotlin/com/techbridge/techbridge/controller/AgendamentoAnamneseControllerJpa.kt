package com.techbridge.techbridge.controller

import com.techbridge.techbridge.entity.AgendamentoAnamnese
import com.techbridge.techbridge.repository.AgendamentoAnamneseRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/agendamentos")
class AgendamentoAnamneseControllerJpa(val agendamentoRepository: AgendamentoAnamneseRepository) {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun setEntityManager(entityManager: EntityManager) {
        this.entityManager = entityManager
    }

    @PostMapping("/agendar")
    fun agendarAnamnese(@RequestBody agendamento: AgendamentoAnamnese): ResponseEntity<AgendamentoAnamnese> {
        val novoAgendamento = agendamentoRepository.save(agendamento)
        return ResponseEntity.status(201).body(novoAgendamento)
    }

    @GetMapping("/historico")
    fun visualizarHistorico(): ResponseEntity<List<Map<String, Any>>> {
        val query = entityManager.createNativeQuery(
            """
        SELECT
            aa.id_anamnese,
            ar.data_disponivel,
            u.nome AS nome_usuario
        FROM
            agendamento_anamnese aa
        JOIN
            agenda_responsavel ar ON aa.fk_data = ar.id_agenda
        JOIN
            usuario u ON aa.fk_aventureiro = u.id_usuario
        WHERE
            ar.data_disponivel > NOW()
        """
        )

        val resultados = query.resultList.map { resultado ->
            val linha = resultado as Array<*>
            mapOf(
                "idAnamnese" to (linha[0] ?: 0),
                "dataDisponivel" to (linha[1] ?: ""),
                "nomeUsuario" to (linha[2] ?: "")
            )
        }

        return if (resultados.isNotEmpty()) {
            ResponseEntity.status(200).body(resultados)
        } else {
            ResponseEntity.status(204).build()
        }
    }

    @PatchMapping("/gerar-relatorio")
    @Transactional
    fun gerarRelatorio(
        @RequestParam rg: String,
        @RequestParam descricao: String
    ): ResponseEntity<String> {
        val query = entityManager.createNativeQuery(
            """
        UPDATE informacoes_pessoais
        SET relatorio_anamnese = :descricao
        WHERE rg = :rg
        """
        )
        query.setParameter("descricao", descricao)
        query.setParameter("rg", rg)

        val linhasAfetadas = query.executeUpdate()

        return if (linhasAfetadas > 0) {
            ResponseEntity.status(200).body("Relatório atualizado com sucesso.")
        } else {
            ResponseEntity.status(404).body("RG não encontrado.")
        }
    }

    @GetMapping("/datas-disponiveis")
    fun listarDatasDisponiveis(): ResponseEntity<List<Map<String, Any>>> {
        val query = entityManager.createNativeQuery("SELECT id_agenda, data_disponivel FROM agenda_responsavel")
        val resultados = query.resultList.map { resultado ->
            val linha = resultado as Array<*>
            mapOf<String, Any>(
                "idAgenda" to (linha[0] ?: 0),
                "dataDisponivel" to (linha[1] ?: "")
            )
        }
        return if (resultados.isNotEmpty()) {
            ResponseEntity.status(200).body(resultados)
        } else {
            ResponseEntity.status(204).build()
        }
    }
}