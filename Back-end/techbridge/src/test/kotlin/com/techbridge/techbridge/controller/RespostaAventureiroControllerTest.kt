// Controller Tests
import com.techbridge.techbridge.dto.RespostaAventureiroDTO
import com.techbridge.techbridge.entity.RespostaAventureiro
import com.techbridge.techbridge.enums.Nivel
import com.techbridge.techbridge.service.RespostaAventureiroService
import com.techbridge.techbridge.controller.RespostaAventureiroController
import org.junit.jupiter.api.Assertions.*
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.entity.PerguntaAlternativa
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class RespostaAventureiroControllerTest {

    private val respostaService = mock(RespostaAventureiroService::class.java)
    private val respostaController = RespostaAventureiroController(respostaService)

    @Test
    fun salvarRespostas_returnsCreatedWhenValidRequest() {
        val respostas = listOf(
            RespostaAventureiroDTO(0, 1L, 1, 2),
            RespostaAventureiroDTO(0, 1L, 2, 3)
        )

        val response = respostaController.salvarRespostas(respostas)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        verify(respostaService).salvarRespostas(respostas) // Verifica se o método foi chamado
    }

    @Test
    fun salvarRespostas_returnsBadRequestWhenInvalidRequest() {
        val respostas = emptyList<RespostaAventureiroDTO>()

        doThrow(IllegalArgumentException("Lista de respostas vazia"))
            .`when`(respostaService).salvarRespostas(respostas)

        val response = respostaController.salvarRespostas(respostas)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        verify(respostaService).salvarRespostas(respostas)
    }

    @Test
    fun listarRespostas_returnsOkWhenUserHasResponses() {
        val respostas = listOf(
            RespostaAventureiro(1, mock(Usuario::class.java), mock(PerguntaAlternativa::class.java), 2)
        )
        `when`(respostaService.listarRespostas(1L)).thenReturn(respostas)

        val response = respostaController.listarRespostas(1L)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(1, response.body?.size)
    }

    @Test
    fun listarRespostas_returnsNotFoundWhenUserHasNoResponses() {
        `when`(respostaService.listarRespostas(999L)).thenThrow(NoSuchElementException())

        val response = respostaController.listarRespostas(999L)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun calcularNivel_returnsOkWhenNivelCalculatedSuccessfully() {
        `when`(respostaService.calcularEAtualizarNivelUsuario(1L)).thenReturn(Nivel.AVENTUREIRO)

        val response = respostaController.calcularNivel(1L)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(Nivel.AVENTUREIRO, response.body)
    }

    @Test
    fun calcularNivel_returnsNotFoundWhenUserNotFound() {
        `when`(respostaService.calcularEAtualizarNivelUsuario(999L)).thenThrow(NoSuchElementException())

        val response = respostaController.calcularNivel(999L)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }
}