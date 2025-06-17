import com.techbridge.techbridge.controller.GuiaControllerJpa
import com.techbridge.techbridge.dto.EventoRequestDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisGetPerfilDTO
import com.techbridge.techbridge.entity.*
import com.techbridge.techbridge.enums.Nivel
import com.techbridge.techbridge.enums.TipoUsuario
import com.techbridge.techbridge.repository.*
import com.techbridge.techbridge.service.GuiaService
import org.mockito.BDDMockito.given
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito


import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.controller
import java.util.*
import kotlin.jvm.java
import kotlin.test.assertEquals

class GuiaControllerTest  {

    // Mocks dos repositórios
    val repositorioAventureiro: AventureiroRepository = mock(AventureiroRepository::class.java)
    val repositorioInformacoes: InformacoesPessoaisRepository = mock(InformacoesPessoaisRepository::class.java)
    val repositorioConvite: ConviteRepository = mock(ConviteRepository::class.java)
    val repositorioEvento: EventoRepository = mock(EventoRepository::class.java)
    val repositorioInscricao: InscricaoRepository = mock(InscricaoRepository::class.java)
    val repositorioAtivacaoEvento: AtivacaoEventoRepository = mock(AtivacaoEventoRepository::class.java)
    val repositorioUsuario: UsuarioRepository = mock(UsuarioRepository::class.java)
    val repositorioComentario: ComentarioRepository = mock(ComentarioRepository::class.java)
    val repositorioAgendaResponsavel: AgendaResponsavelRepository = mock(AgendaResponsavelRepository::class.java)
    val repositorioAgendaAnamnese: AgendamentoAnamneseRepository = mock(AgendamentoAnamneseRepository::class.java)

    // Mock do service
    val eventoService: GuiaService = mock(GuiaService::class.java)

    lateinit var controller: GuiaControllerJpa

    // Instância do controller com os mocks
    @BeforeEach
    fun setup() {
        controller = GuiaControllerJpa(
            repositorioAventureiro,
            repositorioInformacoes,
            repositorioConvite,
            repositorioEvento,
            repositorioInscricao,
            repositorioAtivacaoEvento,
            repositorioUsuario,
            repositorioComentario,
            repositorioAgendaResponsavel,
            repositorioAgendaAnamnese
        )
        controller.eventoService = eventoService
    }

    // DTOs de exemplo
    val eventoRequestDTO = mock(EventoRequestDTO::class.java)
    val agendaResponsavel = AgendaResponsavel()

    @Test
    fun postEvento_retornaStatusOk_quandoEventoSalvoComSucesso() {
        val evento = Evento()
        given(eventoService.postEvento(eventoRequestDTO)).willReturn(eventoRequestDTO)

        val response = controller.postEvento(eventoRequestDTO)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventoRequestDTO, response.body)
    }

    @Test
    fun postEvento_retornaStatusBadRequest_quandoOcorrerRuntimeException() {
        val errorMessage = "Erro ao salvar evento"
        given(eventoService.postEvento(eventoRequestDTO)).willThrow(RuntimeException(errorMessage))

        val response = controller.postEvento(eventoRequestDTO)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(errorMessage, response.body)
    }

    @Test
    fun getAllEventos_retornaStatusOkEListaDeEventos_quandoExistemEventos() {
        val eventos = listOf(mapOf("nome" to "Evento 1"), mapOf("nome" to "Evento 2"))
        given(eventoService.getEventos()).willReturn(eventos)

        val response = controller.getAllEventos()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventos, response.body)
    }

    @Test
    fun getAllEventos_retornaStatusNoContent_quandoNaoExistemEventos() {
        given(eventoService.getEventos()).willReturn(emptyList())

        val response = controller.getAllEventos()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun getEventoPorGuia_retornaStatusOkEListaDeEventos_quandoExistemEventosParaOGuia() {
        val nomeGuia = "Guia Teste"
        val eventos = listOf(mapOf("nome" to "Evento 1"), mapOf("nome" to "Evento 2"))
        given(eventoService.getEventoPorGuia(nomeGuia)).willReturn(eventos)

        val response = controller.getEventoPorGuia(nomeGuia)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventos, response.body)
    }

    @Test
    fun getEventoPorGuia_retornaStatusNoContent_quandoNaoExistemEventosParaOGuia() {
        val nomeGuia = "Guia Teste"
        given(eventoService.getEventoPorGuia(nomeGuia)).willReturn(emptyList())

        val response = controller.getEventoPorGuia(nomeGuia)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun postComentario_retornaStatusOk_quandoComentarioSalvoComSucesso() {
        val idEvento: Long = 1
        val idAventureiro = 1
        val comentarioTexto = "Ótimo evento!"
        val ativacaoEvento = mock(AtivacaoEvento::class.java)
        val aventureiro = mock(Usuario::class.java)

        given(repositorioAtivacaoEvento.findById(idEvento)).willReturn(Optional.of(ativacaoEvento))
        given(repositorioAventureiro.findByIdAndTipo(idAventureiro, TipoUsuario.AVENTUREIRO)).willReturn(aventureiro)

        val response = controller.postComentario(idEvento, idAventureiro, comentarioTexto)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Comentário adicionado com sucesso!", response.body)
    }

    @Test
    fun postComentario_retornaStatusNotFound_quandoEventoNaoEncontrado() {
        val idEvento: Long = 1
        val idAventureiro = 1
        val comentarioTexto = "Ótimo evento!"

        given(repositorioAtivacaoEvento.findById(idEvento)).willReturn(Optional.empty())

        val response = controller.postComentario(idEvento, idAventureiro, comentarioTexto)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Evento ou aventureiro não encontrado.", response.body)
    }

    @Test
    fun postComentario_retornaStatusNotFound_quandoAventureiroNaoEncontrado() {
        val idEvento: Long = 1
        val idAventureiro = 1
        val comentarioTexto = "Ótimo evento!"
        val ativacaoEvento = mock(AtivacaoEvento::class.java)

        given(repositorioAtivacaoEvento.findById(idEvento)).willReturn(Optional.of(ativacaoEvento))
        given(repositorioAventureiro.findByIdAndTipo(idAventureiro, TipoUsuario.AVENTUREIRO)).willReturn(null)

        val response = controller.postComentario(idEvento, idAventureiro, comentarioTexto)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Evento ou aventureiro não encontrado.", response.body)
    }

    @Test
    fun deleteComentario_retornaStatusNoContent_quandoComentarioExcluidoComSucesso() {
        val idComentario = 1
        val comentario = Comentario()
        given(repositorioComentario.findById(idComentario)).willReturn(Optional.of(comentario))

        val response = controller.deleteComentario(idComentario)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun deleteComentario_retornaStatusNotFound_quandoComentarioNaoEncontrado() {
        val idComentario = 1
        given(repositorioComentario.findById(idComentario)).willReturn(Optional.empty())

        val response = controller.deleteComentario(idComentario)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun putEvento_retornaStatusOk_quandoEventoAtualizadoComSucesso() {
        val id: Long = 1
        val eventoAtualizado = Evento()
        given(repositorioEvento.existsById(id)).willReturn(true)
        given(repositorioEvento.save(eventoAtualizado)).willReturn(eventoAtualizado)

        val response = controller.putEvento(id, eventoAtualizado)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventoAtualizado, response.body)
    }

    @Test
    fun putEvento_retornaStatusNotFound_quandoEventoNaoEncontrado() {
        val id: Long = 1
        val eventoAtualizado = Evento()
        given(repositorioEvento.existsById(id)).willReturn(false)

        val response = controller.putEvento(id, eventoAtualizado)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun deleteEvento_retornaStatusNoContent_quandoEventoExcluidoComSucesso() {
        val id: Long = 1
        given(repositorioEvento.existsById(id)).willReturn(true)

        val response = controller.deleteEvento(id)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun deleteEvento_retornaStatusNotFound_quandoEventoNaoEncontrado() {
        val id: Long = 1
        given(repositorioEvento.existsById(id)).willReturn(false)

        val response = controller.deleteEvento(id)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun getEventosAtivos_retornaStatusOkEListaDeEventos_quandoExistemEventosAtivos() {
        val eventosAtivos = mutableListOf(AtivacaoEvento())
        given(repositorioAtivacaoEvento.findAll()).willReturn(eventosAtivos)

        val response = controller.getEventosAtivos()

        assertEquals(HttpStatus.OK, response.statusCode)
        //assertEquals(eventosAtivos, response.body)
    }

    @Test
    fun getEventosAtivos_retornaStatusNoContent_quandoNaoExistemEventosAtivos() {
        given(repositorioAtivacaoEvento.findAll()).willReturn(mutableListOf())

        val response = controller.getEventosAtivos()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun getEventosBase_retornaStatusOkEListaDeEventos_quandoExistemEventosBase() {
        val eventosBase = mutableListOf(Evento(), Evento())
        given(repositorioEvento.findAll()).willReturn(eventosBase)

        val response = controller.getEventosBase()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventosBase, response.body)
    }

    @Test
    fun getEventosBase_retornaStatusNoContent_quandoNaoExistemEventosBase() {
        given(repositorioEvento.findAll()).willReturn(mutableListOf())

        val response = controller.getEventosBase()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun deleteInscricao_retornaStatusOK_quandoInscricaoDeletadaComSucesso() {
        val eventoSelecionado: Long = 1
        val usuarioSelecionado: Long = 1
        val inscricao = Inscricao()

        given(repositorioAtivacaoEvento.existsById(eventoSelecionado)).willReturn(true)
        given(repositorioInscricao.findByAventureiroAndEvento(usuarioSelecionado, eventoSelecionado)).willReturn(inscricao)

        val response = controller.deleteInscricao(eventoSelecionado, usuarioSelecionado)

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun deleteInscricao_retornaStatusAccepted_quandoInscricaoNaoExiste() {
        val eventoSelecionado: Long = 1
        val usuarioSelecionado: Long = 1

        given(repositorioAtivacaoEvento.existsById(eventoSelecionado)).willReturn(true)
        given(repositorioInscricao.findByAventureiroAndEvento(usuarioSelecionado, eventoSelecionado)).willReturn(null)

        val response = controller.deleteInscricao(eventoSelecionado, usuarioSelecionado)

        assertEquals(HttpStatus.ACCEPTED, response.statusCode)
    }

    @Test
    fun deleteInscricao_retornaStatusNotFound_quandoEventoNaoExiste() {
        val eventoSelecionado: Long = 1
        val usuarioSelecionado: Long = 1

        given(repositorioAtivacaoEvento.existsById(eventoSelecionado)).willReturn(false)

        val response = controller.deleteInscricao(eventoSelecionado, usuarioSelecionado)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun postAgenda_retornaStatusOk_quandoAgendaSalvaComSucesso() {
        val dataAgenda = AgendaResponsavel()
        given(repositorioAgendaResponsavel.save(dataAgenda)).willReturn(dataAgenda)

        val response = controller.postAgenda(dataAgenda)

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun postAgenda_retornaStatusBadRequest_quandoAgendaNula() {
        val response = controller.postAgenda(null)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun getHistoricoAgenda_retornaStatusOkEListaDeAgendamentos_quandoExistemAgendamentos() {
        val usuarioSelecionado = 1
        val agendamentos: List<Map<String, Any>> = listOf(
            mapOf("agendamento" to AgendamentoAnamnese()),
            mapOf("agendamento" to AgendamentoAnamnese())
        )
        given(repositorioAgendaAnamnese.listarHistoricoGuia(usuarioSelecionado)).willReturn(agendamentos)

        val response = controller.getHistoricoAgenda(usuarioSelecionado)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(agendamentos, response.body)
    }

    @Test
    fun getHistoricoAgenda_retornaStatusNoContent_quandoNaoExistemAgendamentos() {
        val usuarioSelecionado = 1
        given(repositorioAgendaAnamnese.listarHistoricoGuia(usuarioSelecionado)).willReturn(emptyList())

        val response = controller.getHistoricoAgenda(usuarioSelecionado)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun getDatasDisponiveis_retornaStatusOkEListaDeDatas_quandoExistemDatasDisponiveis() {
        val usuarioSelecionado: Long = 1
        val datasDisponiveis = listOf(AgendaResponsavel(), AgendaResponsavel())
        given(repositorioAgendaResponsavel.findByFkresponsavelAndDataDisponivel(usuarioSelecionado)).willReturn(datasDisponiveis)

        val response = controller.getDatasDisponiveis(usuarioSelecionado)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(datasDisponiveis, response.body)
    }

    @Test
    fun getDatasDisponiveis_retornaStatusNoContent_quandoNaoExistemDatasDisponiveis() {
        val usuarioSelecionado: Long = 1
        given(repositorioAgendaResponsavel.findByFkresponsavelAndDataDisponivel(usuarioSelecionado)).willReturn(emptyList())

        val response = controller.getDatasDisponiveis(usuarioSelecionado)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun getInformacoesPessoas_retornaStatusOkEInformacoes_quandoInformacoesEncontradas() {
        val usuarioSelecionado: Long = 1
        val informacoes = InformacoesPessoaisGetPerfilDTO(
            telefoneContato = "123456789",
            nome = "Teste",
            dataNascimento = Date(),
            nivel = Nivel.DESBRAVADOR,
            rua = "Rua Teste"
        )
        given(repositorioInformacoes.buscarInformacoes(usuarioSelecionado)).willReturn(informacoes)

        val response = controller.getInformacoesPessoas(usuarioSelecionado)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(informacoes, response.body)
    }

    @Test
    fun getInformacoesPessoas_retornaStatusNoContent_quandoInformacoesNaoEncontradas() {
        val usuarioSelecionado: Long = 1
        given(repositorioInformacoes.buscarInformacoes(usuarioSelecionado)).willReturn(null)

        val response = controller.getInformacoesPessoas(usuarioSelecionado)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }
}