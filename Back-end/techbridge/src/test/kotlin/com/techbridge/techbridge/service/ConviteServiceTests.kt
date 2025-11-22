import com.techbridge.techbridge.repository.UsuarioRepository
import com.techbridge.techbridge.entity.Usuario
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime
import java.util.*

class ConviteServiceTests {

    private val conviteRepository = mock(ConviteRepository::class.java)
    private val usuarioRepository = mock(UsuarioRepository::class.java)
    private val service = ConviteService().apply {
        conviteRepository = this@ConviteServiceTests.conviteRepository
        usuarioRepository = this@ConviteServiceTests.usuarioRepository
    }

    @Test
    fun enviarConviteThrowsExceptionWhenConvidadoNotFound() {
        val dto = ConviteRequestDTO(emailConvidado = "inexistente@dominio.com", aventureiro = 1, convidado = 1)
        `when`(usuarioRepository.findByEmail(dto.emailConvidado)).thenReturn(null)

        val exception = assertThrows(RuntimeException::class.java) {
            service.enviarConvite(dto)
        }

        assertEquals("Usuário convidado não encontrado", exception.message)
    }

    @Test
    fun enviarConviteThrowsExceptionWhenAventureiroNotFound() {
        val dto = ConviteRequestDTO(emailConvidado = "teste@dominio.com", aventureiro = 999, convidado = 1)
        `when`(usuarioRepository.findByEmail(dto.emailConvidado)).thenReturn(mock(Usuario::class.java))
        `when`(usuarioRepository.findById(dto.aventureiro)).thenReturn(Optional.empty())

        val exception = assertThrows(RuntimeException::class.java) {
            service.enviarConvite(dto)
        }

        assertEquals("Usuário aventureiro não encontrado", exception.message)
    }

    @Test
    fun listarConvitesReturnsEmptyListWhenNoConvitesFound() {
        `when`(conviteRepository.findByAventureiroIdUsuario(1L)).thenReturn(emptyList())

        val result = service.listarConvites(1L)

        assertTrue(result.isEmpty())
    }

    @Test
    fun atualizarConviteThrowsExceptionWhenConviteNotFound() {
        val dto = ConviteReqDTO(conviteAceito = true)
        `when`(conviteRepository.findById(999L)).thenReturn(Optional.empty())

        val exception = assertThrows(RuntimeException::class.java) {
            service.atualizarConvite(999L, dto)
        }

        assertEquals("Convite não encontrado ou não atualizado", exception.message)
    }
}