package com.techbridge.techbridge.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class DashboardRepository(private val jdbcTemplate: JdbcTemplate) {

    fun getEventosAtivosFuturos(usuarioId: Long): Int? {
        val sql = """
        SELECT eventos_ativos_futuros
        FROM eventos_ativos_view_filtrada
        WHERE fk_responsavel = ?
    """
        return jdbcTemplate.query(sql, arrayOf(usuarioId)) { rs, _ -> rs.getInt("eventos_ativos_futuros") }
            .firstOrNull()
    }
    fun getUsuariosFrequentes(usuarioId: Long): List<Map<String, Any>> {
        val sql = """
        SELECT Tipo_Usuario, Quantidade_Usuarios
        FROM usuarios_frequentes_filtrada
        WHERE fk_responsavel = ?
    """
        return jdbcTemplate.queryForList(sql, usuarioId)
    }

    fun getUsuariosNovos(usuarioId: Long): List<Map<String, Any>> {
        val sql = """
        SELECT Tipo_Usuario, Quantidade_Usuarios
        FROM usuarios_novos
        WHERE fk_responsavel = ?
    """
        return jdbcTemplate.queryForList(sql, usuarioId)
    }
    fun getTopCidades(usuarioId: Long): List<Map<String, Any>> {
        val sql = """
            SELECT Cidade, Total_Participantes
            FROM top_5_cidades_aventureiros_filtrada
            WHERE fk_responsavel = ?
        """
        return jdbcTemplate.queryForList(sql, usuarioId)
    }

    fun getPalavrasComentarios(usuarioId: Long): List<Map<String, Any>> {
        val sql = """
            SELECT palavra, quantidade
            FROM vw_top_palavras_comentarios_filtrada
            WHERE fk_responsavel = ?
        """
        return jdbcTemplate.queryForList(sql, usuarioId)
    }

    fun getInscricaoLimite(usuarioId: Long): List<Map<String, Any>> {
        val sql = """
            SELECT Evento, Inscricoes, Capacidade_Maxima
            FROM inscricao_limite_filtrada
            WHERE fk_responsavel = ?
        """
        return jdbcTemplate.queryForList(sql, usuarioId)
    }

    fun getTaxaOcupacaoMedia(usuarioId: Long): List<Map<String, Any>> {
        val sql = """
            SELECT mes, taxa_ocupacao_percentual
            FROM taxa_ocupacao_media_filtrada
            WHERE fk_responsavel = ?
        """
        return jdbcTemplate.queryForList(sql, usuarioId)
    }

    fun getRankingEventos(usuarioId: Long): List<Map<String, Any>> {
        val sql = """
            SELECT nome, total_inscricoes, nota_media
            FROM ranking_eventos_filtrada
            WHERE fk_responsavel = ?
        """
        return jdbcTemplate.queryForList(sql, usuarioId)
    }

    fun getTendenciasAno(usuarioId: Long): List<Map<String, Any>> {
        val sql = """
            SELECT ano, total_inscricoes
            FROM vw_tendencias_ano_inscricoes_filtrada
            WHERE fk_responsavel = ?
        """
        return jdbcTemplate.queryForList(sql, usuarioId)
    }

    fun getTendenciasMes(usuarioId: Long): List<Map<String, Any>> {
        val sql = """
            SELECT ano, mes, total_inscricoes
            FROM vw_tendencias_mes_inscricoes_filtrada
            WHERE fk_responsavel = ?
        """
        return jdbcTemplate.queryForList(sql, usuarioId)
    }

    fun getTendenciasDia(usuarioId: Long): List<Map<String, Any>> {
        val sql = """
            SELECT dia, total_inscricoes
            FROM vw_tendencias_dia_inscricoes_filtrada
            WHERE fk_responsavel = ?
        """
        return jdbcTemplate.queryForList(sql, usuarioId)
    }
}