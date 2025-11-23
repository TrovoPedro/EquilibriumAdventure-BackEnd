package com.techbridge.techbridge.dto

data class DashboardDTO(
    val eventosAtivosFuturos: Int?,
    val taxaOcupacaoMedia: List<TaxaOcupacaoDTO>,
    val usuariosNovosFrequentes: Map<String, Int>,
    val topCidades: List<CidadeDTO>,
    val rankingEventos: List<EventoRankingDTO>,
    val palavrasComentarios: List<PalavraDTO>,
    val tendenciasAno: List<TendenciaAnoDTO>,
    val tendenciasMes: List<TendenciaMesDTO>,
    val tendenciasDia: List<TendenciaDiaDTO>
)

data class TaxaOcupacaoDTO(val mes: String, val taxaOcupacaoPercentual: Double)
data class CidadeDTO(val cidade: String, val totalParticipantes: Int)
data class EventoRankingDTO(val nome: String, val totalInscricoes: Int, val notaMedia: Double)
data class PalavraDTO(val palavra: String, val quantidade: Int)
data class TendenciaAnoDTO(val ano: Int, val totalInscricoes: Int)
data class TendenciaMesDTO(val ano: Int, val mes: Int, val totalInscricoes: Int)
data class TendenciaDiaDTO(val dia: String, val totalInscricoes: Int)
data class InscricaoLimiteDTO(val evento: String, val inscricoes: Int, val capacidadeMaxima: Int)
data class TendenciaPeriodoDTO(val dia: String, val totalInscricoes: Int)
