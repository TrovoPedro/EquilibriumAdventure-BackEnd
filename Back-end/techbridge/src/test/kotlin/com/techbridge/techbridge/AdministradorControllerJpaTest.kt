import com.techbridge.techbridge.controller.AdministradorControllerJpa
import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.enums.TipoUsuario
import com.techbridge.techbridge.repository.AdministradorRepository
import com.techbridge.techbridge.repository.AtivacaoEventoRepository
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.GuiaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import java.util.*

class AdministradorControllerJpaTest {

    private val repositorio = Mockito.mock(AdministradorRepository::class.java)
    private val repositorioGuia = Mockito.mock(GuiaRepository::class.java)
    private val repositorioEventoAtivo = Mockito.mock(AtivacaoEventoRepository::class.java)
    private val repositorioEvento = Mockito.mock(EventoRepository::class.java)

    private val controller = AdministradorControllerJpa(
        repositorio,
        repositorioGuia,
        repositorioEventoAtivo,
        repositorioEvento
    )

    @Test
    fun cadastrarEventoRetornaCreatedComEventoSalvo() {
        val evento = Evento()
        given(repositorioEvento.save(evento)).willReturn(evento)

        val response = controller.postEvento(evento)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(evento, response.body)
    }

    @Test
    fun cadastrarEventoAtivoRetornaCreatedComEventoAtivoSalvo() {
        val ativacaoEvento = AtivacaoEvento()
        given(repositorioEventoAtivo.save(ativacaoEvento)).willReturn(ativacaoEvento)

        val response = controller.postEventoAtivo(ativacaoEvento)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(ativacaoEvento, response.body)
    }

    @Test
    fun buscarTodosEventosBaseRetornaNoContentQuandoVazio() {
        given(repositorioEvento.findAll()).willReturn(mutableListOf())

        val response = controller.getAllEventos()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun buscarTodosEventosBaseRetornaOkComListaQuandoNaoVazio() {
        val eventos = mutableListOf(Evento())
        given(repositorioEvento.findAll()).willReturn(eventos)

        val response = controller.getAllEventos()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventos, response.body)
    }

    @Test
    fun buscarEventoBaseEspecificoRetornaOkQuandoExiste() {
        val evento = Evento()
        given(repositorioEvento.findById(1L)).willReturn(Optional.of(evento))

        val response = controller.getEventoEspecifico(1L)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(evento, response.body)
    }

    @Test
    fun buscarEventoBaseEspecificoRetornaNotFoundQuandoNaoExiste() {
        given(repositorioEvento.findById(1L)).willReturn(Optional.empty())

        val response = controller.getEventoEspecifico(1L)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun editarEventoRetornaNotFoundSeEventoNaoExiste() {
        given(repositorioEvento.existsById(1L)).willReturn(false)
        val evento = Evento()

        val response = controller.putEvento(1L, evento)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun editarEventoRetornaBadRequestSeNomeVazio() {
        given(repositorioEvento.existsById(1L)).willReturn(true)
        val evento = Evento()
        evento.nome = ""

        val response = controller.putEvento(1L, evento)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun editarEventoRetornaOkComEventoAtualizado() {
        given(repositorioEvento.existsById(1L)).willReturn(true)
        val evento = Evento()
        evento.nome = "Nome"
        given(repositorioEvento.save(evento)).willReturn(evento)

        val response = controller.putEvento(1L, evento)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(evento, response.body)
    }

    @Test
    fun deletarEventoRetornaNoContentQuandoExiste() {
        given(repositorio.existsById(1)).willReturn(true)

        val response = controller.deleteEvento(1)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun deletarEventoRetornaNotFoundQuandoNaoExiste() {
        given(repositorio.existsById(1)).willReturn(false)

        val response = controller.deleteEvento(1)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun cadastrarGuiaRetornaCreatedComGuiaSalvo() {
        val usuario = Usuario()
        given(repositorioGuia.save(usuario)).willReturn(usuario)

        val response = controller.postGuia(usuario)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(usuario, response.body)
        assertEquals(TipoUsuario.GUIA, usuario.tipo_usuario)
    }

    @Test
    fun buscarGuiasRetornaNoContentQuandoVazio() {
        given(repositorioGuia.findByTipoUsuario(TipoUsuario.GUIA)).willReturn(listOf())

        val response = controller.getAllGuias()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun buscarGuiasRetornaOkComListaQuandoNaoVazio() {
        val guias = listOf(Usuario())
        given(repositorioGuia.findByTipoUsuario(TipoUsuario.GUIA)).willReturn(guias)

        val response = controller.getAllGuias()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(guias, response.body)
    }

    @Test
    fun buscarGuiaEspecificoRetornaOkQuandoExisteEGuia() {
        val guia = Usuario()
        guia.tipo_usuario = TipoUsuario.GUIA
        given(repositorioGuia.findByIdAndTipo(1, TipoUsuario.GUIA)).willReturn(guia)

        val response = controller.getGuiaEspecifico(1)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(guia, response.body)
    }

    @Test
    fun buscarGuiaEspecificoRetornaNotFoundQuandoNaoExiste() {
        given(repositorioGuia.findByIdAndTipo(1, TipoUsuario.GUIA)).willReturn(null)

        val response = controller.getGuiaEspecifico(1)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun editarGuiaRetornaOkComGuiaAtualizado() {
        val guiaExistente = Usuario()
        guiaExistente.tipo_usuario = TipoUsuario.GUIA
        val guiaAtualizado = Usuario()
        given(repositorioGuia.findByIdAndTipo(1, TipoUsuario.GUIA)).willReturn(guiaExistente)
        given(repositorioGuia.save(guiaAtualizado)).willReturn(guiaAtualizado)

        val response = controller.putGuia(1, guiaAtualizado)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(guiaAtualizado, response.body)
        assertEquals(TipoUsuario.GUIA, guiaAtualizado.tipo_usuario)
        assertEquals(1, guiaAtualizado.idUsuario)
    }

    @Test
    fun editarGuiaRetornaNotFoundQuandoNaoExiste() {
        val guiaAtualizado = Usuario()
        given(repositorioGuia.findByIdAndTipo(1, TipoUsuario.GUIA)).willReturn(null)

        val response = controller.putGuia(1, guiaAtualizado)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun deletarGuiaRetornaNoContentQuandoExisteEGuia() {
        val guiaExistente = Usuario()
        guiaExistente.tipo_usuario = TipoUsuario.GUIA
        given(repositorioGuia.findByIdAndTipo(1, TipoUsuario.GUIA)).willReturn(guiaExistente)

        val response = controller.deleteGuia(1)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun deletarGuiaRetornaNotFoundQuandoNaoExiste() {
        given(repositorioGuia.findByIdAndTipo(1, TipoUsuario.GUIA)).willReturn(null)

        val response = controller.deleteGuia(1)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }
}