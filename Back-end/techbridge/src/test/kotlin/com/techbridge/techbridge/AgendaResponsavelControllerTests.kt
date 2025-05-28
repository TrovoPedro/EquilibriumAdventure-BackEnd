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

    fun listarDatasDisponiveisReturnsMappedAgendas() {
        val mockAgenda = AgendaResponsavel(
            idAgenda = 1,
            dataDisponivel = LocalDateTime.now(),
            fkresponsavel = Usuario(idUsuario = 1, nome = "Guia Teste")
        )
        `when`(agendaRepository.findAllDisponiveis()).thenReturn(listOf(mockAgenda))

        val result = controller.listarDatasDisponiveis()

        assertEquals(1, result.size)
        assertEquals(mockAgenda.dataDisponivel, result[0].dataDisponivel)
        assertEquals("Guia Teste", result[0].nomeGuia)
    }

    fun adicionarDisponibilidadeThrowsExceptionWhenGuiaNotFound() {
        val dto = AgendaRequestDTO(fkGuia = 999, dataDisponivel = LocalDateTime.now().toString())
        `when`(usuarioRepository.findById(dto.fkGuia?.toLong()!!)).thenReturn(Optional.empty())

        val exception = assertThrows(RuntimeException::class.java) {
            controller.adicionarDisponibilidade(dto)
        }

        assertEquals("Guia não encontrado", exception.message)
    }

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

        assertEquals(savedAgenda.dataDisponivel, result.dataDisponivel)
        assertEquals("1", result.nomeGuia)
    }

    fun listarAgendaReturnsMappedAgendas() {
        val mockAgenda = AgendaResponsavel(
            idAgenda = 1,
            dataDisponivel = LocalDateTime.now(),
            fkresponsavel = Usuario(idUsuario = 1, nome = "Guia Teste")
        )
        `when`(agendaRepository.findAll()).thenReturn(listOf(mockAgenda))

        val result = controller.listarAgenda()

        assertEquals(1, result.size)
        assertEquals(mockAgenda.dataDisponivel, result[0].dataDisponivel)
        assertEquals("Guia Teste", result[0].nomeGuia)
    }
}