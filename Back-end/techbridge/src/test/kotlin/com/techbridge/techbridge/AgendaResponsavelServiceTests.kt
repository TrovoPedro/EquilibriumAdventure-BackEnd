import com.techbridge.techbridge.dto.AgendaRequestDTO
import com.techbridge.techbridge.dto.AgendaResponseDTO
import com.techbridge.techbridge.entity.AgendaResponsavel
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.repository.AgendaResponsavelRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import com.techbridge.techbridge.service.AgendaResponsavelService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime
import java.util.*

class AgendaResponsavelServiceTests {

    private val agendaRepository = mock(AgendaResponsavelRepository::class.java)
    private val usuarioRepository = mock(UsuarioRepository::class.java)
    private val service = AgendaResponsavelService(agendaRepository, usuarioRepository)

<<<<<<< HEAD
=======
    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun listarDatasDisponiveisReturnsEmptyListWhenNoAgendasAvailable() {
        `when`(agendaRepository.findAllDisponiveis()).thenReturn(emptyList())

        val result = service.listarDatasDisponiveis()

        assertTrue(result.isEmpty())
    }

<<<<<<< HEAD
=======
    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun adicionarDisponibilidadeThrowsExceptionWhenFkGuiaIsNull() {
        val dto = AgendaRequestDTO(fkGuia = null, dataDisponivel = LocalDateTime.now().toString())

        val exception = assertThrows(RuntimeException::class.java) {
            service.adicionarDisponibilidade(dto)
        }

<<<<<<< HEAD
        assertEquals("Guia não encontrado", exception.message)
    }

=======
        assertTrue(exception is RuntimeException && (exception.message == null))
    }

    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun listarAgendaReturnsEmptyListWhenNoAgendasExist() {
        `when`(agendaRepository.findAll()).thenReturn(emptyList())

        val result = service.listarAgenda()

        assertTrue(result.isEmpty())
    }

<<<<<<< HEAD
=======
    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun listarDatasDisponiveisMapsAgendasCorrectly() {
        val mockAgenda = AgendaResponsavel(
            idAgenda = 1,
            dataDisponivel = LocalDateTime.now(),
            fkresponsavel = Usuario(idUsuario = 1, nome = "Guia Teste")
        )
        `when`(agendaRepository.findAllDisponiveis()).thenReturn(listOf(mockAgenda))

        val result = service.listarDatasDisponiveis()

        assertEquals(1, result.size)
        assertEquals(mockAgenda.dataDisponivel, result[0].dataDisponivel)
        assertEquals("Guia Teste", result[0].nomeGuia)
    }
}