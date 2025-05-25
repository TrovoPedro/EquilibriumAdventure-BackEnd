package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.ConviteRequestDTO
import com.techbridge.techbridge.dto.ConviteResponseDTO
import com.techbridge.techbridge.entity.Convite
import com.techbridge.techbridge.service.ConviteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/convites")
class ConviteController(
    @Autowired open val conviteService: ConviteService
) {

    // POST /convites
    @PostMapping
    fun enviarConvite(@RequestBody conviteDTO: ConviteRequestDTO): ResponseEntity<ConviteRequestDTO> {
        val conviteSalvo = conviteService.enviarConvite(conviteDTO)
        return ResponseEntity.ok(conviteSalvo)
    }

    // PATCH /convites/{id}
    @PatchMapping("/{id}")
    fun atualizarConvite(
        @PathVariable id: Long,
        @RequestParam conviteAceito: Boolean
    ): ResponseEntity<Convite> {
        val conviteAtualizado = conviteService.atualizarConvite(id, conviteAceito)
        return ResponseEntity.ok(conviteAtualizado)
    }

    // GET /convites
    @GetMapping
    fun listarConvites(): ResponseEntity<List<ConviteResponseDTO>> {
        val convites = conviteService.listarConvite()
        return ResponseEntity.ok(convites)
    }
}
