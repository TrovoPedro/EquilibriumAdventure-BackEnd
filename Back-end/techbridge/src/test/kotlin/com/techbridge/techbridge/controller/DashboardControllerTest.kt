import com.techbridge.techbridge.dto.*
import com.techbridge.techbridge.service.DashboardService
import com.techbridge.techbridge.service.UsuarioService
import com.techbridge.techbridge.controller.DashboardController
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals

class DashboardControllerTest {

    private val dashboardService = mock(DashboardService::class.java)
    private val usuarioService = mock(UsuarioService::class.java)
    private val controller = DashboardController(dashboardService, usuarioService)

    @Test
    fun `getEventosAtivosFuturos deve retornar o valor correto`() {
        val usuarioId = 1L
        `when`(dashboardService.getEventosAtivosFuturos(usuarioId)).thenReturn(5)

        val response = controller.getEventosAtivosFuturos(usuarioId)

        assertEquals(ResponseEntity.ok(5), response)
        verify(usuarioService).getUsuario(usuarioId)
        verify(dashboardService).getEventosAtivosFuturos(usuarioId)
    }

    @Test
    fun `getTaxaOcupacaoMedia deve retornar a lista correta`() {
        val usuarioId = 1L
        val taxaOcupacao = listOf(TaxaOcupacaoDTO("Janeiro", 75.5))
        `when`(dashboardService.getTaxaOcupacaoMedia(usuarioId)).thenReturn(taxaOcupacao)

        val response = controller.getTaxaOcupacaoMedia(usuarioId)

        assertEquals(ResponseEntity.ok(taxaOcupacao), response)
        verify(usuarioService).getUsuario(usuarioId)
        verify(dashboardService).getTaxaOcupacaoMedia(usuarioId)
    }

    @Test
    fun `getUsuariosNovosFrequentes deve retornar o mapa correto`() {
        val usuarioId = 1L
        val usuarios = mapOf("Novo" to 5, "Frequente" to 10)
        `when`(dashboardService.getUsuariosNovosFrequentes(usuarioId)).thenReturn(usuarios)

        val response = controller.getUsuariosNovosFrequentes(usuarioId)

        assertEquals(ResponseEntity.ok(usuarios), response)
        verify(usuarioService).getUsuario(usuarioId)
        verify(dashboardService).getUsuariosNovosFrequentes(usuarioId)
    }

    @Test
    fun `getTopCidades deve retornar a lista correta`() {
        val usuarioId = 1L
        val cidades = listOf(CidadeDTO("São Paulo", 100))
        `when`(dashboardService.getTopCidades(usuarioId)).thenReturn(cidades)

        val response = controller.getTopCidades(usuarioId)

        assertEquals(ResponseEntity.ok(cidades), response)
        verify(usuarioService).getUsuario(usuarioId)
        verify(dashboardService).getTopCidades(usuarioId)
    }

    @Test
    fun `getRankingEventos deve retornar a lista correta`() {
        val usuarioId = 1L
        val ranking = listOf(EventoRankingDTO("Evento A", 50, 4.5))
        `when`(dashboardService.getRankingEventos(usuarioId)).thenReturn(ranking)

        val response = controller.getRankingEventos(usuarioId)

        assertEquals(ResponseEntity.ok(ranking), response)
        verify(usuarioService).getUsuario(usuarioId)
        verify(dashboardService).getRankingEventos(usuarioId)
    }

    @Test
    fun `getPalavrasComentarios deve retornar a lista correta`() {
        val usuarioId = 1L
        val palavras = listOf(PalavraDTO("ótimo", 20))
        `when`(dashboardService.getPalavrasComentarios(usuarioId)).thenReturn(palavras)

        val response = controller.getPalavrasComentarios(usuarioId)

        assertEquals(ResponseEntity.ok(palavras), response)
        verify(usuarioService).getUsuario(usuarioId)
        verify(dashboardService).getPalavrasComentarios(usuarioId)
    }

    @Test
    fun `getTendenciasAno deve retornar a lista correta`() {
        val usuarioId = 1L
        val tendencias = listOf(TendenciaAnoDTO(2023, 200))
        `when`(dashboardService.getTendenciasAno(usuarioId)).thenReturn(tendencias)

        val response = controller.getTendenciasAno(usuarioId)

        assertEquals(ResponseEntity.ok(tendencias), response)
        verify(usuarioService).getUsuario(usuarioId)
        verify(dashboardService).getTendenciasAno(usuarioId)
    }

    @Test
    fun `getTendenciasMes deve retornar a lista correta`() {
        val usuarioId = 1L
        val tendencias = listOf(TendenciaMesDTO(2023, 1, 50))
        `when`(dashboardService.getTendenciasMes(usuarioId)).thenReturn(tendencias)

        val response = controller.getTendenciasMes(usuarioId)

        assertEquals(ResponseEntity.ok(tendencias), response)
        verify(usuarioService).getUsuario(usuarioId)
        verify(dashboardService).getTendenciasMes(usuarioId)
    }

    @Test
    fun `getTendenciasDia deve retornar a lista correta`() {
        val usuarioId = 1L
        val tendencias = listOf(TendenciaDiaDTO("2023-01-01", 10))
        `when`(dashboardService.getTendenciasDia(usuarioId)).thenReturn(tendencias)

        val response = controller.getTendenciasDia(usuarioId)

        assertEquals(ResponseEntity.ok(tendencias), response)
        verify(usuarioService).getUsuario(usuarioId)
        verify(dashboardService).getTendenciasDia(usuarioId)
    }

    @Test
    fun `getInscricaoLimite deve retornar a lista correta`() {
        val usuarioId = 1L
        val inscricoes = listOf(InscricaoLimiteDTO("Evento A", 50, 100))
        `when`(dashboardService.getInscricaoLimite(usuarioId)).thenReturn(inscricoes)

        val response = controller.getInscricaoLimite(usuarioId)

        assertEquals(ResponseEntity.ok(inscricoes), response)
        verify(usuarioService).getUsuario(usuarioId)
        verify(dashboardService).getInscricaoLimite(usuarioId)
    }
}