import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.ResponseEntity
import java.util.*

class ConviteControllerTests {

    private val conviteService = mock(ConviteService::class.java)
    private val controller = ConviteController(conviteService)

    @Test
    fun enviarConviteReturnsOkWhenConviteIsSaved() {
        val dto = ConviteRequestDTO(emailConvidado = "teste@dominio.com", aventureiro = 1, convidado = 2)
        `when`(conviteService.enviarConvite(dto)).thenReturn("Convite enviado com sucesso")

        val response = controller.enviarConvite(dto)

        assertEquals(200, response.statusCodeValue)
        assertEquals("Convite enviado com sucesso", response.body)
    }

    @Test
    fun enviarConviteReturnsNoContentWhenConviteIsNull() {
        val dto = ConviteRequestDTO(emailConvidado = "teste@dominio.com", aventureiro = 1, convidado = 2)
        `when`(conviteService.enviarConvite(dto)).thenReturn(null)

        val response = controller.enviarConvite(dto)

        assertEquals(204, response.statusCodeValue)
        assertNull(response.body)
    }

    @Test
    fun atualizarConviteReturnsOkWhenConviteIsUpdated() {
        val dto = ConviteReqDTO(conviteAceito = true)
        `when`(conviteService.atualizarConvite(1L, dto)).thenReturn("Convite atualizado com sucesso")

        val response = controller.atualizarConvite(1L, dto)

        assertEquals(200, response.statusCodeValue)
        assertEquals("Convite atualizado com sucesso", response.body)
    }

    @Test
    fun atualizarConviteReturnsNotFoundWhenConviteDoesNotExist() {
        val dto = ConviteReqDTO(conviteAceito = true)
        `when`(conviteService.atualizarConvite(999L, dto)).thenThrow(NoSuchElementException("Convite não encontrado"))

        val response = controller.atualizarConvite(999L, dto)

        assertEquals(404, response.statusCodeValue)
        assertTrue(response.body?.contains("Convite não encontrado") == true)
    }

    @Test
    fun listarConvitesReturnsOkWithConvites() {
        val mockConvites = listOf(
            ConviteResponseDTO(dataConvite= "2023-10-01T10:00:00", fkUsuario = 1, nomeAventureiro = "Aventureiro 1", conviteAceito = true),
        )
        `when`(conviteService.listarConvites(1L)).thenReturn(mockConvites)

        val response = controller.listarConvites(1L)

        assertEquals(200, response.statusCodeValue)
        assertEquals(mockConvites, response.body)
    }

    @Test
    fun listarConvitesReturnsNoContentWhenNoConvitesFound() {
        `when`(conviteService.listarConvites(1L)).thenReturn(emptyList())

        val response = controller.listarConvites(1L)

        assertEquals(204, response.statusCodeValue)
        assertNull(response.body)
    }
}