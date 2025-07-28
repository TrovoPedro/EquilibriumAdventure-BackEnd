package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.PerguntaAlternativaResponseDTO
import com.techbridge.techbridge.entity.PerguntaAlternativa
import com.techbridge.techbridge.repository.PerguntaAlternativaRepository
import org.springframework.stereotype.Service

@Service
class PerguntaAlternativaService(private val repository: PerguntaAlternativaRepository) {

    fun verificarEInserirPerguntas() {
        if (repository.count() == 0L) {
            val perguntas = listOf(
                PerguntaAlternativa(
                    textoPergunta = "Com que frequência você participa de atividades ao ar livre (trilhas, acampamentos, escaladas, etc.)?",
                    alternativa1 = "Nunca participei", valor1 = 0,
                    alternativa2 = "Raramente (1–2 vezes por ano)", valor2 = 1,
                    alternativa3 = "Frequentemente (mensalmente)", valor3 = 2,
                    alternativa4 = "Sempre que posso (semanalmente)", valor4 = 3
                ),
                PerguntaAlternativa(
                    textoPergunta = "Você já fez alguma atividade em ambiente de risco controlado (rapel, rafting, escalada, etc.)?",
                    alternativa1 = "Nunca", valor1 = 0,
                    alternativa2 = "Sim, uma ou duas vezes", valor2 = 1,
                    alternativa3 = "Sim, com frequência", valor3 = 2,
                    alternativa4 = "Sim, e costumo liderar ou orientar outros", valor4 = 3
                ),
                PerguntaAlternativa(
                    textoPergunta = "Como você se sente em relação a imprevistos durante uma aventura?",
                    alternativa1 = "Me deixo abalar facilmente", valor1 = 0,
                    alternativa2 = "Fico nervoso, mas tento resolver", valor2 = 1,
                    alternativa3 = "Consigo manter a calma e pensar em soluções", valor3 = 2,
                    alternativa4 = "Adoro desafios e sei como agir", valor4 = 3
                ),
                PerguntaAlternativa(
                    textoPergunta = "Você possui alguma condição de saúde que possa impactar sua participação em atividades físicas intensas (ex: hipertensão, problemas respiratórios, lesões, etc.)?",
                    alternativa1 = "Sim, e preciso de acompanhamento constante", valor1 = 0,
                    alternativa2 = "Sim, mas está sob controle com medicação ou cuidados", valor2 = 1,
                    alternativa3 = "Tive no passado, mas atualmente estou liberado", valor3 = 2,
                    alternativa4 = "Não tenho nenhum problema de saúde relevante", valor4 = 3
                ),
                PerguntaAlternativa(
                    textoPergunta = "Como você avalia sua resistência física para trilhas com mais de 3 horas de duração?",
                    alternativa1 = "Nunca fiz", valor1 = 0,
                    alternativa2 = "Fico muito cansado", valor2 = 1,
                    alternativa3 = "Aguento bem", valor3 = 2,
                    alternativa4 = "Me sinto energizado mesmo depois", valor4 = 3
                ),
                PerguntaAlternativa(
                    textoPergunta = "Você já se perdeu em uma trilha ou se afastou do grupo? Como reagiu?",
                    alternativa1 = "Nunca vivi isso e não saberia como agir", valor1 = 0,
                    alternativa2 = "Já aconteceu e fiquei desesperado", valor2 = 1,
                    alternativa3 = "Já aconteceu, mas consegui me orientar", valor3 = 2,
                    alternativa4 = "Costumo orientar os outros quando isso ocorre", valor4 = 3
                ),
                PerguntaAlternativa(
                    textoPergunta = "Você já acampou em locais sem estrutura (sem banheiro, energia, etc.)?",
                    alternativa1 = "Nunca", valor1 = 0,
                    alternativa2 = "Sim, mas passei perrengue", valor2 = 1,
                    alternativa3 = "Sim, e me saí bem", valor3 = 2,
                    alternativa4 = "Sim, e gosto desse tipo de experiência", valor4 = 3
                ),
                PerguntaAlternativa(
                    textoPergunta = "Você costuma pesquisar a previsão do tempo, rotas e equipamentos antes de uma aventura?",
                    alternativa1 = "Não costumo me preparar", valor1 = 0,
                    alternativa2 = "Às vezes vejo a previsão", valor2 = 1,
                    alternativa3 = "Faço pesquisas básicas", valor3 = 2,
                    alternativa4 = "Planejo tudo com cuidado", valor4 = 3
                ),
                PerguntaAlternativa(
                    textoPergunta = "Você sente mais empolgação ou medo ao encarar algo desconhecido em uma aventura?",
                    alternativa1 = "Medo", valor1 = 0,
                    alternativa2 = "Um pouco dos dois", valor2 = 1,
                    alternativa3 = "Mais empolgação do que medo", valor3 = 2,
                    alternativa4 = "Pura empolgação", valor4 = 3
                ),
                PerguntaAlternativa(
                    textoPergunta = "Quando você participa de uma aventura em grupo com um guia, qual é o seu comportamento?",
                    alternativa1 = "Me sinto seguro ao guia e fico sempre perto dele", valor1 = 0,
                    alternativa2 = "Gosto de seguir as instruções, mas fico atento a tudo", valor2 = 1,
                    alternativa3 = "Ajudo o grupo quando posso e me oriento bem", valor3 = 2,
                    alternativa4 = "Sou proativo, observo, oriento outros e tomo iniciativa", valor4 = 3
                )
            )
            repository.saveAll(perguntas)
        }
    }

    fun listarPerguntas(): List<PerguntaAlternativaResponseDTO> {
        return repository.findAll().map {
            PerguntaAlternativaResponseDTO(
                id = it.id,
                textoPergunta = it.textoPergunta,
                alternativas = listOf(
                    it.alternativa1 to it.valor1,
                    it.alternativa2 to it.valor2,
                    it.alternativa3 to it.valor3,
                    it.alternativa4 to it.valor4
                )
            )
        }
    }
}