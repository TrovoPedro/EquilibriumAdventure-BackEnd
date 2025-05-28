package com.techbridge.techbridge
import com.techbridge.techbridge.controller.AventureiroControllerJpa
import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.repository.AventureiroRepository
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import java.util.*

class AventureiroControllerTests {

    private val repositorioAventureiro = mock(AventureiroRepository::class.java)
    private val repositorioInformacoes = mock(InformacoesPessoaisRepository::class.java)
    private val controller = mock(AventureiroControllerJpa::class.java)


    @Test
    fun putInformacoesRestantesRetornaOkQuandoInformacoesAtualizadas() {
        val id = 1L
        val informacoes = InformacoesPessoais()
        val aventureiro = Optional.of(Any())
        val informacoesOptional = Optional.of(InformacoesPessoais())
        `when`(repositorioAventureiro.findById(id)).thenReturn(aventureiro)
        `when`(repositorioInformacoes.findById(id)).thenReturn(informacoesOptional)
        `when`(repositorioInformacoes.save(informacoes)).thenReturn(informacoes)

        val response = controller.putInformacoesRestantes(id, informacoes)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(informacoes, response.body)
    }

    @Test
    fun putInformacoesRestantesRetornaNotFoundQuandoInformacoesNaoExistem() {
        val id = 1L
        val informacoes = InformacoesPessoais()
        val aventureiro = Optional.of(Any())
        `when`(repositorioAventureiro.findById(id)).thenReturn(aventureiro)
        `when`(repositorioInformacoes.findById(id)).thenReturn(Optional.empty())

        val response = controller.putInformacoesRestantes(id, informacoes)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun putInformacoesRestantesRetornaNoContentQuandoAventureiroNaoExiste() {
        val id = 1L
        val informacoes = InformacoesPessoais()
        `when`(repositorioAventureiro.findById(id)).thenReturn(null)

        val response = controller.putInformacoesRestantes(id, informacoes)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun postConviteRetornaOkQuandoConviteEnviado() {
        val id = 1L
        val idConvidado = 2L
        val convite = Convite()
        val aventureiro = Optional.of(Any())
        val convidado = Optional.of(Any())
        `when`(repositorioAventureiro.findById(id)).thenReturn(aventureiro)
        `when`(repositorioAventureiro.findById(idConvidado)).thenReturn(convidado)
        `when`(repositorioConvite.save(convite)).thenReturn(convite)

        val response = controller.postConvite(id, idConvidado, convite)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(convite, response.body)
    }

    @Test
    fun postConviteRetornaNotFoundQuandoAventureiroNaoExiste() {
        val id = 1L
        val idConvidado = 2L
        val convite = Convite()
        `when`(repositorioAventureiro.findById(id)).thenReturn(null)

        val response = controller.postConvite(id, idConvidado, convite)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun patchConviteRetornaOkQuandoConviteRespondidoAceito() {
        val id = 1L
        val convite = Convite()
        val conviteOptional = Optional.of(convite)
        `when`(repositorioConvite.findById(id)).thenReturn(conviteOptional)
        `when`(repositorioConvite.save(convite)).thenReturn(convite)

        val response = controller.patchConvite(id, 1)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertTrue(response.body!!)
    }

    @Test
    fun patchConviteRetornaOkQuandoConviteRespondidoRecusado() {
        val id = 1L
        val convite = Convite()
        val conviteOptional = Optional.of(convite)
        `when`(repositorioConvite.findById(id)).thenReturn(conviteOptional)
        `when`(repositorioConvite.save(convite)).thenReturn(convite)

        val response = controller.patchConvite(id, 0)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertFalse(response.body!!)
    }

    @Test
    fun patchConviteRetornaNotFoundQuandoConviteNaoExiste() {
        val id = 1L
        `when`(repositorioConvite.findById(id)).thenReturn(Optional.empty())

        val response = controller.patchConvite(id, 1)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun inscreverEventoRetornaCreatedQuandoInscricaoRealizada() {
        val idEvento = 1L
        val idAventureiro = 2
        val aventureiro = Optional.of(Any())
        val evento = Optional.of(Any())
        val inscricao = Inscricao(evento = idEvento, aventureiro = aventureiro)
        `when`(repositorioAventureiro.findById(idAventureiro)).thenReturn(aventureiro)
        `when`(repositorioEvento.findById(idEvento)).thenReturn(evento)
        `when`(repositorioInscricao.save(any(Inscricao::class.java))).thenReturn(inscricao)

        val response = controller.inscreverEvento(idEvento, idAventureiro)

        assertEquals(HttpStatus.CREATED, response.statusCode)
    }

    @Test
    fun inscreverEventoRetornaNotFoundQuandoAventureiroNaoExiste() {
        val idEvento = 1L
        val idAventureiro = 2
        `when`(repositorioAventureiro.findById(idAventureiro)).thenReturn(Optional.empty())

        val response = controller.inscreverEvento(idEvento, idAventureiro)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun deleteInscricaoRetornaNoContentQuandoInscricaoCancelada() {
        val idInscricao = 1L
        `when`(repositorioInscricao.existsById(idInscricao)).thenReturn(true)

        val response = controller.deleteInscricao(idInscricao)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun deleteInscricaoRetornaNotFoundQuandoInscricaoNaoExiste() {
        val idInscricao = 1L
        `when`(repositorioInscricao.existsById(idInscricao)).thenReturn(false)

        val response = controller.deleteInscricao(idInscricao)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

}