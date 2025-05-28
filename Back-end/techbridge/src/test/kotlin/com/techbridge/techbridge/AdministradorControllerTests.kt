package com.techbridge.techbridge
    import com.techbridge.techbridge.controller.AdministradorControllerJpa
    import com.techbridge.techbridge.entity.Evento
    import com.techbridge.techbridge.entity.Usuario
    import com.techbridge.techbridge.enums.TipoUsuario
    import com.techbridge.techbridge.repository.AdministradorRepository
    import com.techbridge.techbridge.repository.EventoRepository
    import com.techbridge.techbridge.repository.GuiaRepository
    import org.junit.jupiter.api.Assertions.*
    import org.junit.jupiter.api.Test
    import org.mockito.Mockito.*
    import org.springframework.http.HttpStatus
    import java.util.*
    import kotlin.jvm.java


class AdministradorControllerTests {

    private val controller = mock(AdministradorControllerJpa::class.java)
    private val repositorio = mock(AdministradorRepository::class.java)
    private val eventoRepository = mock(EventoRepository::class.java)
    private val eventoRepositoryAtivo = mock(EventoAtivoRepository::class.java)
    private val repositorioGuia = mock(GuiaRepository::class.java)

    @Test
    fun postEventoRetornaCreatedQuandoEventoSalvo() {
        val evento = Evento()
        `when`(eventoRepository.save(evento)).thenReturn(evento)

        val response = controller.postEvento(evento)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(evento, response.body)
    }

    @Test
    fun postEventoAtivoRetornaCreatedQuandoEventoAtivoSalvo() {
        val evento = Evento()
        `when`(eventoRepositoryAtivo.save(evento)).thenReturn(evento)

        val response = controller.postEventoAtivo(evento)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(evento, response.body)
    }

    @Test
    fun getAllEventosRetornaNoContentQuandoNaoHaEventos() {
        `when`(eventoRepository.findAll()).thenReturn(mutableListOf())

        val response = controller.getAllEventos()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun getAllEventosRetornaOkQuandoHaEventos() {
        val eventos = mutableListOf(Evento())
        `when`(eventoRepository.findAll()).thenReturn(eventos)

        val response = controller.getAllEventos()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventos, response.body)
    }

    @Test
    fun getEventoEspecificoRetornaOkQuandoExiste() {
        val evento = Evento()
        val id = 1L
        `when`(eventoRepository.findById(id)).thenReturn(Optional.of(evento))

        val response = controller.getEventoEspecifico(id)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(evento, response.body)
    }

    @Test
    fun getEventoEspecificoRetornaNotFoundQuandoNaoExiste() {
        val id = 1L
        `when`(eventoRepository.findById(id)).thenReturn(Optional.empty())

        val response = controller.getEventoEspecifico(id)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun getAllEventosAtivoRetornaNoContentQuandoNaoHaEventos() {
        `when`(eventoRepositoryAtivo.findAll()).thenReturn(mutableListOf())

        val response = controller.getAllEventosAtivo()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun getAllEventosAtivoRetornaOkQuandoHaEventos() {
        val eventos = mutableListOf(Evento())
        `when`(eventoRepositoryAtivo.findAll()).thenReturn(eventos)

        val response = controller.getAllEventosAtivo()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventos, response.body)
    }

    @Test
    fun getEventoEspecificoAtivoRetornaOkQuandoExiste() {
        val evento = Evento()
        val id = 2L
        `when`(eventoRepositoryAtivo.findById(id)).thenReturn(Optional.of(evento))

        val response = controller.getEventoEspecificoAtivo(id)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(evento, response.body)
    }

    @Test
    fun getEventoEspecificoAtivoRetornaNotFoundQuandoNaoExiste() {
        val id = 2L
        `when`(eventoRepositoryAtivo.findById(id)).thenReturn(Optional.empty())

        val response = controller.getEventoEspecificoAtivo(id)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun putEventoRetornaOkQuandoAtualizado() {
        val id = 3L
        val eventoAtualizado = Evento()
        eventoAtualizado.nome = "Novo Nome"
        `when`(eventoRepository.existsById(id)).thenReturn(true)
        `when`(eventoRepository.save(eventoAtualizado)).thenReturn(eventoAtualizado)

        val response = controller.putEvento(id, eventoAtualizado)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(eventoAtualizado, response.body)
    }

    @Test
    fun putEventoRetornaNotFoundQuandoNaoExiste() {
        val id = 3L
        val eventoAtualizado = Evento()
        eventoAtualizado.nome = "Novo Nome"
        `when`(eventoRepository.existsById(id)).thenReturn(false)

        val response = controller.putEvento(id, eventoAtualizado)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun putEventoRetornaBadRequestQuandoNomeVazio() {
        val id = 3L
        val eventoAtualizado = Evento()
        eventoAtualizado.nome = ""
        `when`(eventoRepository.existsById(id)).thenReturn(true)

        val response = controller.putEvento(id, eventoAtualizado)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun deleteEventoRetornaNoContentQuandoDeletado() {
        val id = 4
        `when`(repositorio.existsById(id)).thenReturn(true)

        val response = controller.deleteEvento(id)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify(repositorio).deleteById(id)
    }

    @Test
    fun deleteEventoRetornaNotFoundQuandoNaoExiste() {
        val id = 4
        `when`(repositorio.existsById(id)).thenReturn(false)

        val response = controller.deleteEvento(id)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun postGuiaRetornaCreatedQuandoGuiaSalvo() {
        val guia = Usuario()
        val guiaSalvo = Usuario()
        guiaSalvo.fk_tipo_usuario = TipoUsuario.GUIA
        `when`(repositorioGuia.save(guia)).thenReturn(guiaSalvo)

        val response = controller.postGuia(guia)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(guiaSalvo, response.body)
    }

    @Test
    fun getAllGuiasRetornaNoContentQuandoNaoHaGuias() {
        `when`(repositorioGuia.findByFk_tipo_usuario(2)).thenReturn(listOf())

        val response = controller.getAllGuias()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun getAllGuiasRetornaOkQuandoHaGuias() {
        val guias = listOf(Usuario())
        `when`(repositorioGuia.findByFk_tipo_usuario(2)).thenReturn(guias)

        val response = controller.getAllGuias()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(guias, response.body)
    }

    @Test
    fun getGuiaEspecificoRetornaOkQuandoGuiaExisteEValido() {
        val guia = Usuario()
        guia.fk_tipo_usuario = TipoUsuario.GUIA
        val id = 5
        `when`(repositorioGuia.findById(id)).thenReturn(Optional.of(guia))

        val response = controller.getGuiaEspecifico(id)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(guia, response.body)
    }

    @Test
    fun getGuiaEspecificoRetornaForbiddenQuandoNaoEhGuia() {
        val usuario = Usuario()
        usuario.fk_tipo_usuario = TipoUsuario.ADM
        val id = 5L
        `when`(repositorioGuia.findById(id)).thenReturn(Optional.of(usuario))

        val response = controller.getGuiaEspecifico(id)

        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
    }

    @Test
    fun getGuiaEspecificoRetornaNotFoundQuandoNaoExiste() {
        val id = 5
        `when`(repositorioGuia.findById(id)).thenReturn(Optional.empty())

        val response = controller.getGuiaEspecifico(id)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun putGuiaRetornaOkQuandoGuiaAtualizado() {
        val id = 6
        val guiaAtualizado = Usuario()
        val guiaExistente = Usuario()
        guiaExistente.fk_tipo_usuario = TipoUsuario.GUIA
        `when`(repositorioGuia.findById(id)).thenReturn(guiaExistente)
        `when`(repositorioGuia.save(guiaAtualizado)).thenReturn(guiaAtualizado)

        val response = controller.putGuia(id, guiaAtualizado)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(guiaAtualizado, response.body)
    }

    @Test
    fun putGuiaRetornaForbiddenQuandoNaoEhGuia() {
        val id = 6
        val guiaAtualizado = Usuario()
        val usuario = Usuario()
        usuario.fk_tipo_usuario = TipoUsuario.ADM
        `when`(repositorioGuia.findById(id)).thenReturn(usuario)

        val response = controller.putGuia(id, guiaAtualizado)

        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
    }

    @Test
    fun putGuiaRetornaNotFoundQuandoNaoExiste() {
        val id = 6
        val guiaAtualizado = Usuario()
        `when`(repositorioGuia.findById(id)).thenReturn(null)

        val response = controller.putGuia(id, guiaAtualizado)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun deleteGuiaRetornaNoContentQuandoGuiaDeletado() {
        val id = 7
        val guia = Usuario()
        guia.fk_tipo_usuario = TipoUsuario.GUIA
        `when`(repositorioGuia.findById(id)).thenReturn(Optional.of(guia))

        val response = controller.deleteGuia(id)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify(repositorioGuia).deleteById(id)
    }

    @Test
    fun deleteGuiaRetornaForbiddenQuandoNaoEhGuia() {
        val id = 7
        val usuario = Usuario()
        usuario.fk_tipo_usuario = TipoUsuario.ADM
        `when`(repositorioGuia.findById(id)).thenReturn(Optional.of(usuario))

        val response = controller.deleteGuia(id)

        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
    }

    @Test
    fun deleteGuiaRetornaNotFoundQuandoNaoExiste() {
        val id = 7L
        `when`(repositorioGuia.findById(id)).thenReturn(Optional.empty())

        val response = controller.deleteGuia(id)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }
}