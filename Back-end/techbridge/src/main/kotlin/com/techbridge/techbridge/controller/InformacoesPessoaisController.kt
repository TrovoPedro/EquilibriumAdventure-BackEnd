package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.InformacoesPessoaisRequestDTO
import com.techbridge.techbridge.service.InformacoesPessoaisService
import com.techbridge.techbridge.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/informacoes-pessoais")
class InformacoesPessoaisController {

    @Autowired
    lateinit var informacaoService: InformacoesPessoaisService;

    @PostMapping("/cadastrar/{id}")
    fun postInformacao(@PathVariable id: Long, @RequestBody informacao: InformacoesPessoaisRequestDTO): ResponseEntity<Any>{
        return try {
            val informacaoSalva = informacaoService.postInformacoes(id, informacao)
            ResponseEntity.status(201).body(informacaoSalva)
        }catch (e: RuntimeException){
            ResponseEntity.status(400).body(mapOf("erro" to e.message))
        }
    }

}