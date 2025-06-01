import com.techbridge.techbridge.dto.ConviteRequestDTO
import com.techbridge.techbridge.dto.ConviteReqDTO
import com.techbridge.techbridge.dto.ConviteResponseDTO
import com.techbridge.techbridge.controller.ConviteController
import com.techbridge.techbridge.service.ConviteService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.ResponseEntity
import java.util.*

class ConviteControllerTests {

    private val conviteService = mock(ConviteService::class.java)
    private val controller = ConviteController(conviteService)

<<<<<<< HEAD
=======
    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun enviarConviteReturnsOkWhenConviteIsSaved() {
        val dto = ConviteRequestDTO(emailConvidado = "teste@dominio.com", aventureiro = 1, convidado = 2)
        `when`(conviteService.enviarConvite(dto)).thenReturn("Convite enviado com sucesso")

        val response = controller.enviarConvite(dto)

        assertEquals(200, response.statusCodeValue)
        assertEquals("Convite enviado com sucesso", response.body)
    }

<<<<<<< HEAD
=======
    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun enviarConviteReturnsNoContentWhenConviteIsNull() {
        val dto = ConviteRequestDTO(emailConvidado = "teste@dominio.com", aventureiro = 1, convidado = 2)
        `when`(conviteService.enviarConvite(dto)).thenReturn(null)

        val response = controller.enviarConvite(dto)

        assertEquals(204, response.statusCodeValue)
        assertNull(response.body)
    }

<<<<<<< HEAD
=======
    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun atualizarConviteReturnsOkWhenConviteIsUpdated() {
        val dto = ConviteReqDTO(conviteAceito = true)
        `when`(conviteService.atualizarConvite(1L, dto)).thenReturn("Convite atualizado com sucesso")

        val response = controller.atualizarConvite(1L, dto)

        assertEquals(200, response.statusCodeValue)
        assertEquals("Convite atualizado com sucesso", response.body)
    }

<<<<<<< HEAD
=======
    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun atualizarConviteReturnsNotFoundWhenConviteDoesNotExist() {
        val dto = ConviteReqDTO(conviteAceito = true)
        `when`(conviteService.atualizarConvite(999L, dto)).thenThrow(NoSuchElementException("Convite não encontrado"))

        val response = controller.atualizarConvite(999L, dto)

        assertEquals(404, response.statusCodeValue)
<<<<<<< HEAD
        assertEquals("Convite não encontrado", response.body)
    }

=======
        assertTrue(response.body?.contains("Convite não encontrado") == true)
    }

    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun listarConvitesReturnsOkWithConvites() {
        val mockConvites = listOf(
            ConviteResponseDTO(dataConvite= "2023-10-01T10:00:00", fkUsuario = 1, nomeAventureiro = "Aventureiro 1", conviteAceito = true),
        )
        `when`(conviteService.listarConvites(1L)).thenReturn(mockConvites)

        val response = controller.listarConvites(1L)

        assertEquals(200, response.statusCodeValue)
        assertEquals(mockConvites, response.body)
    }

<<<<<<< HEAD
=======
    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun listarConvitesReturnsNoContentWhenNoConvitesFound() {
        `when`(conviteService.listarConvites(1L)).thenReturn(emptyList())

        val response = controller.listarConvites(1L)

        assertEquals(204, response.statusCodeValue)
        assertNull(response.body)
    }
}