package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.CadastroPerfilDTO
import com.techbridge.techbridge.dto.EditarPerfilDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisRequestDTO
import com.techbridge.techbridge.service.InformacoesPessoaisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/informacoes-pessoais")
class InformacoesPessoaisController {

    @Autowired
    lateinit var informacaoService: InformacoesPessoaisService

    @PostMapping("/cadastrar/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun postInformacao(
        @PathVariable id: Long,
        @RequestPart("usuario") informacaoJson: String,
        @RequestPart("imagem", required = false) img_usuario: MultipartFile?
    ): ResponseEntity<Any> {
        return try {
            val informacaoSalva = informacaoService.postInformacoes(id, informacaoJson, img_usuario)
            ResponseEntity.status(201).body(informacaoSalva)
        } catch (e: RuntimeException) {
            ResponseEntity.status(400).body(mapOf("erro" to e.message))
        }
    }

    @GetMapping("/{id}/imagem")
    fun getImagem(@PathVariable id: Long): ResponseEntity<ByteArray> {
        val imagem = informacaoService.getImagemUsuario(id)

        return if (imagem != null) {
            ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imagem)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/perfil-info/{usuarioId}")
    fun getInformacaoPerfilPorUsuarioId(@PathVariable usuarioId: Long): ResponseEntity<Any> {
        return try {
            val informacaoEncontrada = informacaoService.getInformacoesPerfilPorUsuarioId(usuarioId)
            ResponseEntity.ok(informacaoEncontrada)
        } catch (e: RuntimeException) {
            ResponseEntity.status(404).body(e.message)
        }
    }

    @GetMapping("/perfil-nivel/{usuarioId}")
    fun getNivelPerfilPorUsuarioId(@PathVariable usuarioId: Long): ResponseEntity<Any> {
        return try {
            val informacaoEncontrada = informacaoService.getInformacoesPerfilPorUsuarioId(usuarioId)
            ResponseEntity.ok(informacaoEncontrada)
        } catch (e: RuntimeException) {
            ResponseEntity.status(404).body(e.message)
        }
    }

    @PutMapping("/editar-perfil/{id}")
    fun putInformacaoPerfil(
        @PathVariable id: Long,
        @RequestBody novaInformacao: InformacoesPessoaisRequestDTO
    ): ResponseEntity<Any> {
        return try {
            val informacaoSalva = informacaoService.putInformacoes(id, novaInformacao)
            ResponseEntity.status(200).body(informacaoSalva)
        } catch (e: RuntimeException) {
            ResponseEntity.status(404).body(e.message)
        }
    }

    @PutMapping("/atualizar-perfil/{usuarioId}")
    fun atualizarPerfilPorUsuario(
        @PathVariable usuarioId: Long,
        @RequestBody informacao: InformacoesPessoaisRequestDTO
    ): ResponseEntity<Any> {
        return try {
            val atualizado = informacaoService.atualizarPorUsuario(usuarioId, informacao)
            ResponseEntity.ok(atualizado)
        } catch (e: RuntimeException) {
            ResponseEntity.status(404).body(mapOf("erro" to e.message))
        }
    }
    @PostMapping("/cadastrar-perfil-completo/{usuarioId}")
    fun cadastrarPerfilCompleto(
        @PathVariable usuarioId: Long,
        @RequestBody dto: CadastroPerfilDTO
    ): ResponseEntity<Any> {
        return try {
            val perfil = informacaoService.cadastrarPerfilCompleto(dto, usuarioId)
            ResponseEntity.status(201).body(perfil)
        } catch (e: RuntimeException) {
            ResponseEntity.status(400).body(mapOf("erro" to e.message))
        }
    }

    @PutMapping("/editar-perfil-completo/{usuarioId}")
    fun editarPerfilCompleto(
        @PathVariable usuarioId: Long,
        @RequestBody dto: EditarPerfilDTO
    ): ResponseEntity<Any> {
        return try {
            val perfil = informacaoService.editarPerfilCompleto(usuarioId, dto)
            ResponseEntity.ok(perfil)
        } catch (e: RuntimeException) {
            ResponseEntity.status(400).body(mapOf("erro" to e.message))
        }
    }
}