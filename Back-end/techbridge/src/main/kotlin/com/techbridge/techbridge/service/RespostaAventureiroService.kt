package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.PerguntaComRespostaDTO
import com.techbridge.techbridge.dto.RespostaAventureiroDTO
import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.entity.RespostaAventureiro
import com.techbridge.techbridge.enums.Nivel
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository
import com.techbridge.techbridge.repository.PerguntaAlternativaRepository
import com.techbridge.techbridge.repository.RespostaAventureiroRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RespostaAventureiroService(
    private val respostaRepository: RespostaAventureiroRepository,
    private val usuarioRepository: UsuarioRepository,
    private val perguntaRepository: PerguntaAlternativaRepository,
    private val informacoesPessoaisRepository: InformacoesPessoaisRepository
) {

    fun salvarRespostas(respostasDTO: List<RespostaAventureiroDTO>) {
        if (respostasDTO.isEmpty()) return

        val usuarioId = respostasDTO.first().usuarioId

        // Verifica se já existem respostas para esse usuário
        val respostasExistentes = respostaRepository.existsByUsuarioIdUsuario(usuarioId)
        if (respostasExistentes) {
            return
        }

        val usuario = usuarioRepository.findById(usuarioId).orElseThrow {
            IllegalArgumentException("Usuário não encontrado")
        }

        val respostas = respostasDTO.map { respostaDTO ->
            val pergunta = perguntaRepository.findById(respostaDTO.perguntaId).orElseThrow {
                IllegalArgumentException("Pergunta não encontrada")
            }

            RespostaAventureiro(
                usuario = usuario,
                pergunta = pergunta,
                alternativaEscolhida = respostaDTO.alternativaEscolhida
            )
        }

        respostaRepository.saveAll(respostas)
    }


    fun listarRespostas(idUsuario: Long): List<RespostaAventureiro> {
        return respostaRepository.findByUsuarioIdUsuario(idUsuario)
    }

    @Transactional
    fun calcularEAtualizarNivelUsuario(idUsuario: Long): Map<String, Any> {
        val respostas = respostaRepository.findByUsuarioIdUsuario(idUsuario)
        val pontuacaoTotal = respostas.sumOf { resposta ->
            when (resposta.alternativaEscolhida) {
                1 -> resposta.pergunta.valor1
                2 -> resposta.pergunta.valor2
                3 -> resposta.pergunta.valor3
                4 -> resposta.pergunta.valor4
                else -> 0
            }
        }

        val respostaSaude = respostas.find { it.pergunta.textoPergunta.contains("condição de saúde", ignoreCase = true) }
        val valorSaude = respostaSaude?.alternativaEscolhida ?: 0

        val nivel = when {
            pontuacaoTotal < 7 && valorSaude == 1 -> Nivel.EXPLORADOR
            pontuacaoTotal <= 10 && valorSaude == 0 -> Nivel.EXPLORADOR
            pontuacaoTotal <= 10 -> Nivel.EXPLORADOR
            pontuacaoTotal in 11..15 -> Nivel.AVENTUREIRO
            pontuacaoTotal in 16..20 -> Nivel.DESBRAVADOR
            else -> Nivel.EXPLORADOR
        }

        val informacoesPessoais = informacoesPessoaisRepository.buscarPorUsuario(idUsuario) ?: run {
            val novasInformacoes = InformacoesPessoais(
                usuario = usuarioRepository.findById(idUsuario).get(),
                nivel = nivel,
                questionarioRespondido = true
            )
            informacoesPessoaisRepository.save(novasInformacoes)
        }

        informacoesPessoaisRepository.atualizarNivelPorUsuario(idUsuario, nivel)

        // Retorno diferenciado
        return mapOf(
            "nivel" to nivel.name,
            "pontuacaoTotal" to pontuacaoTotal,
            "encaminharParaAnamnese" to (nivel == Nivel.EXPLORADOR && pontuacaoTotal < 7)
        )
    }


    fun listarPerguntasComRespostas(idUsuario: Long): List<PerguntaComRespostaDTO> {
        val perguntas = perguntaRepository.findAll()
        val respostas = respostaRepository.findByUsuarioIdUsuario(idUsuario)
        val respostasMap = respostas.associateBy { it.pergunta.id }

        return perguntas.map { pergunta ->
            PerguntaComRespostaDTO(
                id = pergunta.id,
                textoPergunta = pergunta.textoPergunta,
                alternativas = listOf(
                    pergunta.alternativa1 to pergunta.valor1,
                    pergunta.alternativa2 to pergunta.valor2,
                    pergunta.alternativa3 to pergunta.valor3,
                    pergunta.alternativa4 to pergunta.valor4
                ),
                alternativaEscolhida = respostasMap[pergunta.id]?.alternativaEscolhida
            )
        }
    }
}