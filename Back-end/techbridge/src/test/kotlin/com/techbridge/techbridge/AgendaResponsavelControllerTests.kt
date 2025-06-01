import com.techbridge.techbridge.dto.AgendaRequestDTO
import com.techbridge.techbridge.dto.AgendaResponseDTO
import com.techbridge.techbridge.entity.AgendaResponsavel
import com.techbridge.techbridge.controller.AgendaResponsavelController
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.repository.AgendaResponsavelRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime
import java.util.*

class AgendaResponsavelControllerTests {

    private val agendaRepository = mock(AgendaResponsavelRepository::class.java)
    private val usuarioRepository = mock(UsuarioRepository::class.java)
    private val controller = AgendaResponsavelController(agendaRepository, usuarioRepository)

<<<<<<< HEAD
=======
    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun listarDatasDisponiveisReturnsMappedAgendas() {
        val mockAgenda = AgendaResponsavel(
            idAgenda = 1,
            dataDisponivel = LocalDateTime.now(),
            fkresponsavel = Usuario(idUsuario = 1, nome = "Guia Teste")
        )
        `when`(agendaRepository.findAllDisponiveis()).thenReturn(listOf(mockAgenda))

        val result = controller.listarDatasDisponiveis()

<<<<<<< HEAD
        assertEquals(1, result.size)
        assertEquals(mockAgenda.dataDisponivel, result[0].dataDisponivel)
        assertEquals("Guia Teste", result[0].nomeGuia)
    }

=======
        assertEquals(1, result.body?.size)
        assertEquals(mockAgenda.dataDisponivel, result.body?.get(0)?.dataDisponivel)
        assertEquals("Guia Teste", result.body?.get(0)?.nomeGuia)
    }

    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun adicionarDisponibilidadeThrowsExceptionWhenGuiaNotFound() {
        val dto = AgendaRequestDTO(fkGuia = 999, dataDisponivel = LocalDateTime.now().toString())
        `when`(usuarioRepository.findById(dto.fkGuia?.toLong()!!)).thenReturn(Optional.empty())

<<<<<<< HEAD
        val exception = assertThrows(RuntimeException::class.java) {
            controller.adicionarDisponibilidade(dto)
        }

        assertEquals("Guia não encontrado", exception.message)
    }

=======
        val response = controller.adicionarDisponibilidade(dto)
        assertEquals(404, response.statusCode.value())
    }

    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun adicionarDisponibilidadeSavesAndReturnsAgenda() {
        val guia = Usuario(idUsuario = 1, nome = "Guia Teste")
        val dto = AgendaRequestDTO(fkGuia = 1, dataDisponivel = LocalDateTime.now().toString())
        val savedAgenda = AgendaResponsavel(
            idAgenda = 1,
            dataDisponivel = LocalDateTime.parse(dto.dataDisponivel),
            fkresponsavel = guia
        )
        `when`(usuarioRepository.findById(dto.fkGuia?.toLong()!!)).thenReturn(Optional.of(guia))
        `when`(agendaRepository.save(any(AgendaResponsavel::class.java))).thenReturn(savedAgenda)

        val result = controller.adicionarDisponibilidade(dto)
<<<<<<< HEAD

        assertEquals(savedAgenda.dataDisponivel, result.dataDisponivel)
        assertEquals("1", result.nomeGuia)
    }

=======
    }

    @Test
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    fun listarAgendaReturnsMappedAgendas() {
        val mockAgenda = AgendaResponsavel(
            idAgenda = 1,
            dataDisponivel = LocalDateTime.now(),
            fkresponsavel = Usuario(idUsuario = 1, nome = "Guia Teste")
        )
        `when`(agendaRepository.findAll()).thenReturn(listOf(mockAgenda))

        val result = controller.listarAgenda()

<<<<<<< HEAD
        assertEquals(1, result.size)
        assertEquals(mockAgenda.dataDisponivel, result[0].dataDisponivel)
        assertEquals("Guia Teste", result[0].nomeGuia)
=======
        assertEquals(1, result.body?.size)
        assertEquals(mockAgenda.dataDisponivel, result.body?.get(0)?.dataDisponivel)
        assertEquals("Guia Teste", result.body?.get(0)?.nomeGuia)
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
    }
}