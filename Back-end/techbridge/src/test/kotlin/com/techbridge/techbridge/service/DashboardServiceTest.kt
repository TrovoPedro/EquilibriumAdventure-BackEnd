import com.techbridge.techbridge.dto.*
import com.techbridge.techbridge.repository.DashboardRepository
import com.techbridge.techbridge.service.DashboardService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal

class DashboardServiceTest {

    private val repository = mock(DashboardRepository::class.java)
    private val service = DashboardService(repository)

    @Test
    fun `getEventosAtivosFuturos deve retornar o valor correto`() {
        val usuarioId = 1L
        `when`(repository.getEventosAtivosFuturos(usuarioId)).thenReturn(5)

        val result = service.getEventosAtivosFuturos(usuarioId)

        assertEquals(5, result)
        verify(repository).getEventosAtivosFuturos(usuarioId)
    }

    @Test
    fun `getUsuariosNovosFrequentes deve retornar o mapa correto`() {
        val usuarioId = 1L
        val frequentes = listOf(mapOf("Tipo_Usuario" to "Frequente", "Quantidade_Usuarios" to 10))
        val novos = listOf(mapOf("Tipo_Usuario" to "Novo", "Quantidade_Usuarios" to 5))

        `when`(repository.getUsuariosFrequentes(usuarioId)).thenReturn(frequentes)
        `when`(repository.getUsuariosNovos()).thenReturn(novos)

        val result = service.getUsuariosNovosFrequentes(usuarioId)

        assertEquals(mapOf("Novo" to 5, "Frequente" to 10), result)
        verify(repository).getUsuariosFrequentes(usuarioId)
        verify(repository).getUsuariosNovos()
    }

    @Test
    fun `getTopCidades deve retornar a lista correta`() {
        val usuarioId = 1L
        val cidades = listOf(mapOf("cidade" to "São Paulo", "total_participantes" to 100L))

        `when`(repository.getTopCidades(usuarioId)).thenReturn(cidades)

        val result = service.getTopCidades(usuarioId)

        assertEquals(1, result.size)
        assertEquals("São Paulo", result[0].cidade)
        assertEquals(100, result[0].totalParticipantes)
        verify(repository).getTopCidades(usuarioId)
    }

    @Test
    fun `getPalavrasComentarios deve retornar a lista correta`() {
        val usuarioId = 1L
        val palavras = listOf(mapOf("palavra" to "ótimo", "quantidade" to 20L))

        `when`(repository.getPalavrasComentarios(usuarioId)).thenReturn(palavras)

        val result = service.getPalavrasComentarios(usuarioId)

        assertEquals(1, result.size)
        assertEquals("ótimo", result[0].palavra)
        assertEquals(20, result[0].quantidade)
        verify(repository).getPalavrasComentarios(usuarioId)
    }

    @Test
    fun `getInscricaoLimite deve retornar a lista correta`() {
        val usuarioId = 1L
        val inscricoes = listOf(mapOf("Evento" to "Evento A", "Inscricoes" to 50L, "Capacidade_Maxima" to 100))

        `when`(repository.getInscricaoLimite(usuarioId)).thenReturn(inscricoes)

        val result = service.getInscricaoLimite(usuarioId)

        assertEquals(1, result.size)
        assertEquals("Evento A", result[0].evento)
        assertEquals(50, result[0].inscricoes)
        assertEquals(100, result[0].capacidadeMaxima)
        verify(repository).getInscricaoLimite(usuarioId)
    }

    @Test
    fun `getTaxaOcupacaoMedia deve retornar a lista correta`() {
        val usuarioId = 1L
        val taxas = listOf(mapOf("mes" to "Janeiro", "taxa_ocupacao_percentual" to BigDecimal("75.5")))

        `when`(repository.getTaxaOcupacaoMedia(usuarioId)).thenReturn(taxas)

        val result = service.getTaxaOcupacaoMedia(usuarioId)

        assertEquals(1, result.size)
        assertEquals("Janeiro", result[0].mes)
        assertEquals(75.5, result[0].taxaOcupacaoPercentual)
        verify(repository).getTaxaOcupacaoMedia(usuarioId)
    }

    @Test
    fun `getRankingEventos deve retornar a lista correta`() {
        val usuarioId = 1L
        val ranking = listOf(mapOf("nome" to "Evento A", "total_inscricoes" to 50L, "nota_media" to BigDecimal("4.5")))

        `when`(repository.getRankingEventos(usuarioId)).thenReturn(ranking)

        val result = service.getRankingEventos(usuarioId)

        assertEquals(1, result.size)
        assertEquals("Evento A", result[0].nome)
        assertEquals(50, result[0].totalInscricoes)
        assertEquals(4.5, result[0].notaMedia)
        verify(repository).getRankingEventos(usuarioId)
    }

    @Test
    fun `getTendenciasAno deve retornar a lista correta`() {
        val usuarioId = 1L
        val tendencias = listOf(mapOf("ano" to 2023 as Int, "total_inscricoes" to 200L)) // "ano" como Int

        `when`(repository.getTendenciasAno(usuarioId)).thenReturn(tendencias)

        val result = service.getTendenciasAno(usuarioId)

        assertEquals(1, result.size)
        assertEquals(2023, result[0].ano)
        assertEquals(200, result[0].totalInscricoes)
        verify(repository).getTendenciasAno(usuarioId)
    }

    @Test
    fun `getTendenciasMes deve retornar a lista correta`() {
        val usuarioId = 1L
        val tendencias = listOf(mapOf("ano" to 2023 as Int, "mes" to 1 as Int, "total_inscricoes" to 50L)) // "ano" e "mes" como Int

        `when`(repository.getTendenciasMes(usuarioId)).thenReturn(tendencias)

        val result = service.getTendenciasMes(usuarioId)

        assertEquals(1, result.size)
        assertEquals(2023, result[0].ano)
        assertEquals(1, result[0].mes)
        assertEquals(50, result[0].totalInscricoes)
        verify(repository).getTendenciasMes(usuarioId)
    }

    @Test
    fun `getTendenciasDia deve retornar a lista correta`() {
        val usuarioId = 1L
        val tendencias = listOf(mapOf("dia" to java.sql.Date.valueOf("2023-01-01"), "total_inscricoes" to 10L))

        `when`(repository.getTendenciasDia(usuarioId)).thenReturn(tendencias)

        val result = service.getTendenciasDia(usuarioId)

        assertEquals(1, result.size)
        assertEquals("2023-01-01", result[0].dia)
        assertEquals(10, result[0].totalInscricoes)
        verify(repository).getTendenciasDia(usuarioId)
    }
}