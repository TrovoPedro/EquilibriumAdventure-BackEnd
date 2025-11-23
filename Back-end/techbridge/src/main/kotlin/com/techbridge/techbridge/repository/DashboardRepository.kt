package com.techbridge.techbridge.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Date
import java.time.LocalDate

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
    fun getTopCidades(usuarioId: Long, startDate: String?, endDate: String?): List<Map<String, Any>> {
        val baseSql = StringBuilder()
        baseSql.append("""
            SELECT Cidade, fk_responsavel, COUNT(DISTINCT id_aventureiro) AS Total_Participantes
            FROM top_5_cidades_aventureiros_filtrada
            WHERE fk_responsavel = ?
        """)

        val params = mutableListOf<Any>(usuarioId)

        if (!startDate.isNullOrBlank() && !endDate.isNullOrBlank()) {
            baseSql.append(" AND dia BETWEEN ? AND ?")
            params.add(Date.valueOf(LocalDate.parse(startDate)))
            params.add(Date.valueOf(LocalDate.parse(endDate)))
        } else if (!startDate.isNullOrBlank()) {
            baseSql.append(" AND dia >= ?")
            params.add(Date.valueOf(LocalDate.parse(startDate)))
        } else if (!endDate.isNullOrBlank()) {
            baseSql.append(" AND dia <= ?")
            params.add(Date.valueOf(LocalDate.parse(endDate)))
        }

        baseSql.append(" GROUP BY Cidade, fk_responsavel ORDER BY Total_Participantes DESC LIMIT 7")

        return jdbcTemplate.queryForList(baseSql.toString(), *params.toTypedArray())
    }

    fun getPalavrasComentarios(usuarioId: Long, startDate: String?, endDate: String?): List<Map<String, Any>> {
        // Usar a view já existente para filtrar por período de forma simples e segura
        val baseSql = StringBuilder()
        baseSql.append("""
            SELECT palavra, COUNT(*) AS quantidade
            FROM vw_top_palavras_comentarios_filtrada
            WHERE fk_responsavel = ?
        """)

        val params = mutableListOf<Any>(usuarioId)

        if (!startDate.isNullOrBlank() && !endDate.isNullOrBlank()) {
            baseSql.append(" AND dia BETWEEN ? AND ?")
            params.add(Date.valueOf(LocalDate.parse(startDate)))
            params.add(Date.valueOf(LocalDate.parse(endDate)))
        }

        baseSql.append(" AND palavra NOT IN ('a','e','o','de','para','com','em','do','da','que','é','foi','um','uma','os','as')")
        baseSql.append(" AND palavra REGEXP '^[a-zA-ZÀ-ÿ]+$'")
        baseSql.append(" GROUP BY palavra ORDER BY quantidade DESC LIMIT 20")

        return jdbcTemplate.queryForList(baseSql.toString(), *params.toTypedArray())
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

    fun getRankingEventos(usuarioId: Long, startDate: String?, endDate: String?): List<Map<String, Any>> {
        // Usar a view ranking_eventos_filtrada para filtrar por período quando fornecido
        val baseSql = StringBuilder()
        baseSql.append("""
            SELECT nome, COUNT(DISTINCT id_inscricao) AS total_inscricoes, ROUND(AVG(avaliacao),2) AS nota_media, fk_responsavel
            FROM ranking_eventos_filtrada
            WHERE fk_responsavel = ?
        """)

        val params = mutableListOf<Any>(usuarioId)

        if (!startDate.isNullOrBlank() && !endDate.isNullOrBlank()) {
            baseSql.append(" AND dia BETWEEN ? AND ?")
            params.add(Date.valueOf(LocalDate.parse(startDate)))
            params.add(Date.valueOf(LocalDate.parse(endDate)))
        }

        baseSql.append(" GROUP BY id_evento, nome, fk_responsavel ORDER BY total_inscricoes DESC")

        return jdbcTemplate.queryForList(baseSql.toString(), *params.toTypedArray())
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

    // Novo método que usa a view unificada vw_tendencias_inscricoes
    fun getTendenciasPeriodo(usuarioId: Long, startDate: String?, endDate: String?): List<Map<String, Any>> {
        val baseSql = StringBuilder()
        baseSql.append("""
            SELECT dia, total_inscricoes
            FROM vw_tendencias_inscricoes
            WHERE fk_responsavel = ?
        """)

        val params = mutableListOf<Any>(usuarioId)

        if (!startDate.isNullOrBlank() && !endDate.isNullOrBlank()) {
            baseSql.append(" AND dia BETWEEN ? AND ?")
            // converte para java.sql.Date para segurança
            val sd = Date.valueOf(LocalDate.parse(startDate))
            val ed = Date.valueOf(LocalDate.parse(endDate))
            params.add(sd)
            params.add(ed)
        } else if (!startDate.isNullOrBlank()) {
            baseSql.append(" AND dia >= ?")
            params.add(Date.valueOf(LocalDate.parse(startDate)))
        } else if (!endDate.isNullOrBlank()) {
            baseSql.append(" AND dia <= ?")
            params.add(Date.valueOf(LocalDate.parse(endDate)))
        }

        baseSql.append(" ORDER BY dia")

        return jdbcTemplate.queryForList(baseSql.toString(), *params.toTypedArray())
    }
}