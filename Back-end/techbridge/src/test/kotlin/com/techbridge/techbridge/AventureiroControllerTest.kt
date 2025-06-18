import com.techbridge.techbridge.controller.AventureiroController
import com.techbridge.techbridge.dto.InscricaoDTO
import com.techbridge.techbridge.entity.*
import com.techbridge.techbridge.enums.TipoUsuario
import com.techbridge.techbridge.repository.*
import com.techbridge.techbridge.service.InscricaoService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime
import java.util.*

class AventureiroControllerTest {

    private val repositorioAventureiro = mock(AventureiroRepository::class.java)
    private val repositorioInformacoes = mock(InformacoesPessoaisRepository::class.java)
    private val repositorioConvite = mock(ConviteRepository::class.java)
    private val repositorioEvento = mock(EventoRepository::class.java)
    private val repositorioInscricao = mock(InscricaoRepository::class.java)
    private val repositorioAtivacaoEvento = mock(AtivacaoEventoRepository::class.java)
    private val repositorioUsuario = mock(UsuarioRepository::class.java)
    private val repositorioComentario = mock(ComentarioRepository::class.java)

    private val controller = AventureiroController(
        repositorioAventureiro,
        repositorioInformacoes,
        repositorioConvite,
        repositorioEvento,
        repositorioInscricao,
        repositorioAtivacaoEvento,
        repositorioUsuario,
        repositorioComentario
    )

    @Test
    fun preencherInformacoesRestantes_retorna200QuandoInformacoesExistem() {
        val id = 1L
        val informacoes = InformacoesPessoais()
        given(repositorioAventureiro.findByIdUsuario(id)).willReturn(Aventureiro())
        given(repositorioInformacoes.findById(id)).willReturn(Optional.of(informacoes))
        given(repositorioInformacoes.save(informacoes)).willReturn(informacoes)

        val response = controller.putInformacoesRestantes(id, informacoes)

        assertEquals(200, response.statusCodeValue)
        assertEquals(informacoes, response.body)
    }

    @Test
    fun preencherInformacoesRestantes_retorna404QuandoInformacoesNaoExistem() {
        val id = 1L
        val informacoes = InformacoesPessoais()
        given(repositorioAventureiro.findByIdUsuario(id)).willReturn(Aventureiro())
        given(repositorioInformacoes.findById(id)).willReturn(Optional.empty())

        val response = controller.putInformacoesRestantes(id, informacoes)

        assertEquals(404, response.statusCodeValue)
        assertNull(response.body)
    }

    @Test
    fun preencherInformacoesRestantes_retorna204QuandoAventureiroNaoExiste() {
        val id = 1L
        val informacoes = InformacoesPessoais()
        given(repositorioAventureiro.findByIdUsuario(id)).willReturn(null)

        val response = controller.putInformacoesRestantes(id, informacoes)

        assertEquals(204, response.statusCodeValue)
    }

    @Test
    fun enviarConvite_retorna200QuandoAventureiroExiste() {
        val id = 1
        val idConvidado = 2L
        val convite = Convite()
        val aventureiro = Aventureiro()
        val convidado = Usuario()
        given(repositorioAventureiro.findByIdAndTipo(id, TipoUsuario.AVENTUREIRO)).willReturn(aventureiro)
        given(repositorioUsuario.findById(idConvidado)).willReturn(Optional.of(convidado))
        given(repositorioConvite.save(convite)).willReturn(convite)

        val response = controller.postConvite(id, idConvidado, convite)

        assertEquals(200, response.statusCodeValue)
        assertEquals(convite, response.body)
    }


    @Test
    fun responderConvite_retorna200QuandoConviteExisteEAceita() {
        val id = 1L
        val convite = Convite()
        given(repositorioConvite.findById(id)).willReturn(Optional.of(convite))
        given(repositorioConvite.save(convite)).willReturn(convite)

        val response = controller.patchConvite(id, 1)

        assertEquals(200, response.statusCodeValue)
        assertTrue(response.body!!)
    }

    @Test
    fun responderConvite_retorna200QuandoConviteExisteENaoAceita() {
        val id = 1L
        val convite = Convite()
        given(repositorioConvite.findById(id)).willReturn(Optional.of(convite))
        given(repositorioConvite.save(convite)).willReturn(convite)

        val response = controller.patchConvite(id, 0)

        assertEquals(200, response.statusCodeValue)
        assertFalse(response.body!!)
    }

    @Test
    fun responderConvite_retorna404QuandoConviteNaoExiste() {
        val id = 1L
        given(repositorioConvite.findById(id)).willReturn(Optional.empty())

        val response = controller.patchConvite(id, 1)

        assertEquals(404, response.statusCodeValue)
    }


    @Test
    fun cancelarInscricao_retorna204QuandoInscricaoExiste() {
        val idInscricao = 1L
        given(repositorioInscricao.existsById(idInscricao)).willReturn(true)

        val response = controller.deleteInscricao(idInscricao)

        assertEquals(204, response.statusCodeValue)
    }

    @Test
    fun cancelarInscricao_retorna404QuandoInscricaoNaoExiste() {
        val idInscricao = 1L
        given(repositorioInscricao.existsById(idInscricao)).willReturn(false)

        val response = controller.deleteInscricao(idInscricao)

        assertEquals(404, response.statusCodeValue)
    }

    @Test
    fun adicionarComentario_retorna200QuandoEventoEAventureiroExistem() {
        val idEvento = 1L
        val idAventureiro = 2
        val ativacaoEvento = AtivacaoEvento()
        val aventureiro = Aventureiro()
        given(repositorioAtivacaoEvento.findById(idEvento)).willReturn(Optional.of(ativacaoEvento))
        given(repositorioAventureiro.findByIdAndTipo(idAventureiro, TipoUsuario.AVENTUREIRO)).willReturn(aventureiro)
        given(repositorioComentario.save(Mockito.any(Comentario::class.java))).willAnswer { it.arguments[0] }

        val response = controller.postComentario(idEvento, idAventureiro, "Comentário de teste")

        assertEquals(200, response.statusCodeValue)
        assertEquals("Comentário adicionado com sucesso!", response.body)
    }

    @Test
    fun adicionarComentario_retorna404QuandoEventoOuAventureiroNaoExistem() {
        val idEvento = 1L
        val idAventureiro = 2
        given(repositorioAtivacaoEvento.findById(idEvento)).willReturn(Optional.empty())
        given(repositorioAventureiro.findByIdAndTipo(idAventureiro, TipoUsuario.AVENTUREIRO)).willReturn(null)

        val response = controller.postComentario(idEvento, idAventureiro, "Comentário de teste")

        assertEquals(404, response.statusCodeValue)
        assertEquals("Evento ou aventureiro não encontrado.", response.body)
    }

    @Test
    fun deletarComentario_retorna204QuandoComentarioExiste() {
        val idComentario = 1
        val comentario = Comentario()
        given(repositorioComentario.findById(idComentario)).willReturn(Optional.of(comentario))

        val response = controller.deleteComentario(idComentario)

        assertEquals(204, response.statusCodeValue)
    }

    @Test
    fun deletarComentario_retorna404QuandoComentarioNaoExiste() {
        val idComentario = 1
        given(repositorioComentario.findById(idComentario)).willReturn(Optional.empty())

        val response = controller.deleteComentario(idComentario)

        assertEquals(404, response.statusCodeValue)
    }
}