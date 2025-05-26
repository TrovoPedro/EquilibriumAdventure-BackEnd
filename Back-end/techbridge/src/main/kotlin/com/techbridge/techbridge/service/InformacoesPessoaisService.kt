package com.techbridge.techbridge.service;

import com.techbridge.techbridge.dto.InformacoesPessoaisGetPerfilDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisRequestDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisResponseDTO
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository;
import com.techbridge.techbridge.repository.UsuarioRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping

@Service
class InformacoesPessoaisService {

    @Autowired
    lateinit var informacaoRepository: InformacoesPessoaisRepository;

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository;

    fun postInformacoes(id: Long, informacao: InformacoesPessoaisRequestDTO): InformacoesPessoaisResponseDTO {
        if (informacao == null) {
            throw RuntimeException("Informações não preenchidas");
        }

        val usuarioEncontado = usuarioRepository.findById(id)

        if (usuarioEncontado.isEmpty) {
            throw RuntimeException("Usuário não encontrado");
        }

        val novaInformacao = informacaoRepository.save(informacao.toEntity());

        return InformacoesPessoaisResponseDTO(
            contato_emergencia = informacao.contato_emergencia,
            endereco = informacao.endereco,
            nivel = informacao.nivel,
            usuario = informacao.usuario,
            relatorioAnamnese = informacao.relatorioAnamnese,
            idioma = informacao.idioma,
            questionario_respondido = informacao.questionario_respondido
        )
    }

    fun getInformacoesPerfil(id: Long): InformacoesPessoaisGetPerfilDTO? {
        var usuarioEncotrado = usuarioRepository.findById(id)

        if (usuarioEncotrado.isEmpty) {
            throw RuntimeException("Usuário não encotrado");
        }

        return informacaoRepository.buscarInformacoes(id);
    }

    @Transactional
    fun putInformacoes(id: Long, informacao: InformacoesPessoaisRequestDTO): InformacoesPessoaisResponseDTO {
       if(informacao == null){
           throw RuntimeException("Informações em branco")
       }

        val informacaoEncontrada = informacaoRepository.findById(id).orElseThrow {
            RuntimeException("Informações não encontradas")
        }

        informacaoEncontrada.contato_emergencia = informacao.contato_emergencia;
        informacaoEncontrada.endereco = informacao.endereco;
        informacaoEncontrada.nivel = informacao.nivel;
        informacaoEncontrada.usuario = informacao.usuario;
        informacaoEncontrada.relatorioAnamnese = informacao.relatorioAnamnese;
        informacaoEncontrada.idioma = informacao.idioma;
        informacaoEncontrada.questionario_respondido = informacao.questionario_respondido;

        informacaoRepository.save(informacaoEncontrada)

        return InformacoesPessoaisResponseDTO(
            contato_emergencia = informacao.contato_emergencia,
            endereco = informacao.endereco,
            nivel = informacao.nivel,
            usuario = informacao.usuario,
            relatorioAnamnese = informacao.relatorioAnamnese,
            idioma = informacao.idioma,
            questionario_respondido = informacao.questionario_respondido
        )
    }

}
