import com.techbridge.techbridge.controller.GuiaControllerJpa
import com.techbridge.techbridge.entity.Agenda
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.InscricaoEvento
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.repository.AgendamentoAnamneseRepository
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.GuiaRepository
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository
import com.techbridge.techbridge.repository.InscricoesEventoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class GuiaControllerTests {

    private val mockGuiaRepository = mock(GuiaRepository::class.java)
    private val mockEventoRepository = mock(EventoRepository::class.java)
    private val mockEventoAtivoRepository = mock(EventoAtivoRepository::class.java)
    private val mockInscricaoRepository = mock(InscricoesEventoRepository::class.java)
    private val mockAgendaRepository = mock(AgendamentoAnamneseRepository::class.java)
    private val mockInformacoesPessoasRepository = mock(InformacoesPessoaisRepository::class.java)

    private val controller = GuiaControllerJpa(
        mockGuiaRepository,
        mockEventoRepository,
        mockEventoAtivoRepository,
        mockInscricaoRepository,
        mockAgendaRepository,
        mockInformacoesPessoasRepository
    )

    @Test
    fun postEventoRetornaCreatedQuandoEventoSalvo() {
        val novoEvento = Evento()
        `when`(mockEventoRepository.save(novoEvento)).thenReturn(novoEvento)

        val response = controller.postEvento(novoEvento)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(novoEvento, response.body)
    }

    @Test
    fun getAllEventosRetornaNoContentQuandoNaoHaEventos() {
        `when`(mockEventoRepository.findAll()).thenReturn(mutableListOf())

        val response = controller.getAllEventos()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun getAllEventosRetornaOkQuandoHaEventos() {
        val eventos = mutableListOf(Evento())
        `when`(mockEventoRepository.findAll()).thenReturn(eventos)

        val response = controller.getAllEventos()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventos, response.body)
    }

    @Test
    fun getEventoEspecificoRetornaOkQuandoExiste() {
        val evento = Evento()
        val id = 1L
        `when`(mockEventoRepository.findById(id)).thenReturn(java.util.Optional.of(evento))

        val response = controller.getEventoEspecifico(id)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(evento, response.body)
    }

    @Test
    fun getEventoEspecificoRetornaNotFoundQuandoNaoExiste() {
        val id = 1L
        `when`(mockEventoRepository.findById(id)).thenReturn(java.util.Optional.empty())

        val response = controller.getEventoEspecifico(id)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun putEventoRetornaOkQuandoAtualizado() {
        val id = 2L
        val eventoAtualizado = Evento()
        eventoAtualizado.id_evento = id
        `when`(mockEventoRepository.existsById(id)).thenReturn(true)
        `when`(mockEventoRepository.save(eventoAtualizado)).thenReturn(eventoAtualizado)

        val response = controller.putEvento(id, eventoAtualizado)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventoAtualizado, response.body)
    }

    @Test
    fun putEventoRetornaNotFoundQuandoNaoExiste() {
        val id = 2L
        val eventoAtualizado = Evento()
        `when`(mockEventoRepository.existsById(id)).thenReturn(false)

        val response = controller.putEvento(id, eventoAtualizado)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun deleteEventoRetornaNoContentQuandoDeletado() {
        val id = 1L
        `when`(mockEventoRepository.existsById(id)).thenReturn(true)

        val response = controller.deleteEvento(id)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify(mockEventoRepository).deleteById(id)
    }

    @Test
    fun deleteEventoRetornaNotFoundQuandoNaoExiste() {
        val id = 99L
        `when`(mockEventoRepository.existsById(id)).thenReturn(false)

        val response = controller.deleteEvento(id)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun getEventosAtivosRetornaOkQuandoHaEventos() {
        val eventosAtivos = listOf(ativacaoEvento())
        `when`(mockEventoAtivoRepository.findAll()).thenReturn(eventosAtivos)

        val response = controller.getEventosAtivos()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventosAtivos, response.body)
    }

    @Test
    fun getEventosAtivosRetornaNoContentQuandoNaoHaEventos() {
        `when`(mockEventoAtivoRepository.findAll()).thenReturn(emptyList())

        val response = controller.getEventosAtivos()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun getEventosBaseRetornaOkQuandoHaEventos() {
        val eventosAtivos = listOf(Evento())
        `when`(mockEventoAtivoRepository.findAll()).thenReturn(eventosAtivos)

        val response = controller.getEventosBase()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventosAtivos, response.body)
    }

    @Test
    fun getEventosBaseRetornaNoContentQuandoNaoHaEventos() {
        `when`(mockEventoAtivoRepository.findAll()).thenReturn(emptyList())

        val response = controller.getEventosBase()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun deleteInscricaoRetornaOkQuandoInscricaoDeletada() {
        val eventoSelecionado = AtivacaoEvento()
        val usuarioSelecionado = Usuario(10)
        var inscricao: InscricaoEvento = InscricaoEvento(evento = eventoSelecionado, usuario = usuarioSelecionado)
        // inscricao.id = 123
        `when`(mockEventoAtivoRepository.existsById(eventoSelecionado.id_evento)).thenReturn(true)
        `when`(mockInscricaoRepository.findByFkUsuarioAndFkAtivacaoEvento(usuarioSelecionado.id_usuario, eventoSelecionado.id_evento)).thenReturn(inscricao)

        val response = controller.deleteInscricao(eventoSelecionado, usuarioSelecionado)

        assertEquals(HttpStatus.OK, response.statusCode)
        verify(mockInscricaoRepository).deleteById(inscricao.id_inscricao)
    }

    @Test
    fun deleteInscricaoRetornaAcceptedQuandoInscricaoNaoExiste() {
        val eventoSelecionado = 1
        val usuarioSelecionado = 1
        `when`(mockEventoAtivoRepository.existsById(eventoSelecionado)).thenReturn(true)
        `when`(mockInscricaoRepository.findByFkUsuarioAndFkAtivacaoEvento(usuarioSelecionado, eventoSelecionado)).thenReturn(null)

        val response = controller.deleteInscricao(eventoSelecionado, usuarioSelecionado)

        assertEquals(HttpStatus.ACCEPTED, response.statusCode)
    }

    @Test
    fun deleteInscricaoRetornaNotFoundQuandoEventoAtivoNaoExiste() {
        val eventoSelecionado = 1
        val usuarioSelecionado = 2
        `when`(mockEventoAtivoRepository.existsById(eventoSelecionado)).thenReturn(false)

        val response = controller.deleteInscricao(eventoSelecionado, usuarioSelecionado)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun postAgendaRetornaOkQuandoAgendaSalva() {
        val agenda: Agenda = Agenda()
        `when`(mockAgendaRepository.save(agenda)).thenReturn(agenda)

        val response = controller.postAgenda(agenda)

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun postAgendaRetornaBadRequestQuandoAgendaNull() {
        val response = controller.postAgenda(null)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun getHistoricoAgendaRetornaOkQuandoHaAgenda() {
        val agendas = listOf(Agenda())
        `when`(mockAgendaRepository.findByFkResponsavelOrderByDataDisponivelAsc()).thenReturn(agendas)

        val response = controller.getHistoricoAgenda()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(agendas, response.body)
    }

    @Test
    fun getHistoricoAgendaRetornaNoContentQuandoNaoHaAgenda() {
        `when`(mockAgendaRepository.findByFkResponsavelOrderByDataDisponivelAsc()).thenReturn(emptyList())

        val response = controller.getHistoricoAgenda()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun getHistoricoEventoAtivosRetornaOkQuandoHaEventos() {
        val usuarioSelecionado = 1
        val eventos = listOf(Evento())
        `when`(mockEventoAtivoRepository.findByFkUsuario(usuarioSelecionado)).thenReturn(eventos)

        val response = controller.getHistoricoEventoAtivos(usuarioSelecionado)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventos, response.body)
    }

    @Test
    fun getHistoricoEventoAtivosRetornaNoContentQuandoNaoHaEventos() {
        val usuarioSelecionado = 1
        `when`(mockEventoAtivoRepository.findByFkUsuario(usuarioSelecionado)).thenReturn(emptyList())

        val response = controller.getHistoricoEventoAtivos(usuarioSelecionado)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun getInformacoesPessoasRetornaOkQuandoHaInformacoes() {
        val usuarioSelecionado = 1
        val informacoes = listOf(InformacoesPessoais())
        `when`(mockInformacoesPessoasRepository.findByFkAventureiro(usuarioSelecionado)).thenReturn(informacoes)

        val response = controller.getInformacoesPessoas(usuarioSelecionado)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(informacoes, response.body)
    }

    @Test
    fun getInformacoesPessoasRetornaNoContentQuandoNaoHaInformacoes() {
        val usuarioSelecionado = 1
        `when`(mockInformacoesPessoasRepository.findByFkAventureiro(usuarioSelecionado)).thenReturn(emptyList())

        val response = controller.getInformacoesPessoas(usuarioSelecionado)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }
}