package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.*
import com.techbridge.techbridge.repository.DashboardRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class DashboardService(private val repository: DashboardRepository) {

    fun getEventosAtivosFuturos(usuarioId: Long): Int? {
        return repository.getEventosAtivosFuturos(usuarioId)
    }

    fun getUsuariosNovosFrequentes(usuarioId: Long): Map<String, Int> {
        val frequentes = repository.getUsuariosFrequentes(usuarioId)
        val novos = repository.getUsuariosNovos()

        val mapaUsuarios = mutableMapOf("Novo" to 0, "Frequente" to 0)

        frequentes.forEach {
            val tipoUsuario = it["Tipo_Usuario"] as? String ?: return@forEach
            val quantidade = (it["Quantidade_Usuarios"] as? Number)?.toInt() ?: 0
            mapaUsuarios[tipoUsuario] = quantidade
        }

        novos.forEach {
            val tipoUsuario = it["Tipo_Usuario"] as? String ?: return@forEach
            val quantidade = (it["Quantidade_Usuarios"] as? Number)?.toInt() ?: 0
            mapaUsuarios[tipoUsuario] = mapaUsuarios.getOrDefault(tipoUsuario, 0) + quantidade
        }

        return mapaUsuarios
    }

    fun getTopCidades(usuarioId: Long): List<CidadeDTO> {
        return repository.getTopCidades(usuarioId).map {
            CidadeDTO(
                cidade = it["cidade"] as String,
                totalParticipantes = (it["total_participantes"] as? Number)?.toInt() ?: 0
            )
        }
    }

    fun getPalavrasComentarios(usuarioId: Long): List<PalavraDTO> {
        return repository.getPalavrasComentarios(usuarioId).map {
            PalavraDTO(
                palavra = it["palavra"] as String,
                quantidade = (it["quantidade"] as? Number)?.toInt() ?: 0
            )
        }
    }

    fun getInscricaoLimite(usuarioId: Long): List<InscricaoLimiteDTO> {
        return repository.getInscricaoLimite(usuarioId).map {
            InscricaoLimiteDTO(
                it["Evento"] as String,
                (it["Inscricoes"] as? Number)?.toInt() ?: 0,
                (it["Capacidade_Maxima"] as? Number)?.toInt() ?: 0
            )
        }
    }

    fun getTaxaOcupacaoMedia(usuarioId: Long): List<TaxaOcupacaoDTO> {
        return repository.getTaxaOcupacaoMedia(usuarioId).map {
            TaxaOcupacaoDTO(
                it["mes"] as String,
                (it["taxa_ocupacao_percentual"] as? BigDecimal)?.toDouble() ?: 0.0
            )
        }
    }

    fun getRankingEventos(usuarioId: Long): List<EventoRankingDTO> {
        return repository.getRankingEventos(usuarioId).map {
            EventoRankingDTO(
                it["nome"] as String,
                (it["total_inscricoes"] as? Number)?.toInt() ?: 0,
                (it["nota_media"] as? BigDecimal)?.toDouble() ?: 0.0
            )
        }
    }

    fun getTendenciasAno(usuarioId: Long): List<TendenciaAnoDTO> {
        return repository.getTendenciasAno(usuarioId).map {
            TendenciaAnoDTO(
                (it["ano"] as? Number)?.toInt() ?: 0,
                (it["total_inscricoes"] as? Number)?.toInt() ?: 0
            )
        }
    }

    fun getTendenciasMes(usuarioId: Long): List<TendenciaMesDTO> {
        return repository.getTendenciasMes(usuarioId).map {
            TendenciaMesDTO(
                (it["ano"] as? Number)?.toInt() ?: 0,
                (it["mes"] as? Number)?.toInt() ?: 0,
                (it["total_inscricoes"] as? Number)?.toInt() ?: 0
            )
        }
    }

    fun getTendenciasDia(usuarioId: Long): List<TendenciaDiaDTO> {
        return repository.getTendenciasDia(usuarioId).map {
            TendenciaDiaDTO(
                it["dia"]?.toString() ?: "",
                (it["total_inscricoes"] as? Number)?.toInt() ?: 0
            )
        }
    }
}