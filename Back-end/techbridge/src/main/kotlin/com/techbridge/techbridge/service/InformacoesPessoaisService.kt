package com.techbridge.techbridge.service;

import com.techbridge.techbridge.dto.InformacoesPessoaisGetPerfilDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisRequestDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisResponseDTO
import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.repository.EnderecoRepository
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository;
import com.techbridge.techbridge.repository.UsuarioRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
@Transactional
class InformacoesPessoaisService {

    @Autowired
    lateinit var informacaoRepository: InformacoesPessoaisRepository;

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository;

    @Autowired
    lateinit var enderecoRepository: EnderecoRepository

    fun postInformacoes(id: Long, informacaoJson: String, img_usuario: MultipartFile?): InformacoesPessoaisResponseDTO {
        val objectMapper = com.fasterxml.jackson.databind.ObjectMapper()
        val informacao = objectMapper.readValue(informacaoJson, InformacoesPessoaisRequestDTO::class.java)

        if (informacao == null) {
            throw RuntimeException("Informações não preenchidas")
        }

        val usuarioEncontrado = usuarioRepository.findById(id)
        if (usuarioEncontrado.isEmpty) {
            throw RuntimeException("Usuário não encontrado")
        }

        val enderecoExiste = enderecoRepository.existsById(informacao.endereco)
        if (!enderecoExiste) {
            throw RuntimeException("Endereço não encontrado")
        }

        val entidade = informacao.toEntity()
        entidade.usuario = id
        entidade.endereco = informacao.endereco

        entidade.img_usuario = img_usuario?.bytes

        val novaInformacao = informacaoRepository.save(entidade)

        return InformacoesPessoaisResponseDTO(
            contatoEmergencia = novaInformacao.contatoEmergencia,
            endereco = novaInformacao.endereco ?: 0,
            nivel = novaInformacao.nivel ?: throw RuntimeException("Nível não informado"),
            usuario = novaInformacao.usuario ?: throw RuntimeException("id não informado"),
            relatorioAnamnese = novaInformacao.relatorioAnamnese,
            idioma = novaInformacao.idioma,
            questionarioRespondido = novaInformacao.questionarioRespondido,
        )
    }

    fun getInformacoesId(id: Long): Optional<InformacoesPessoais> {
        return informacaoRepository.findById(id)
    }

    fun getImagemUsuario(id: Long): ByteArray? {
        val informacao = informacaoRepository.findById(id)
            .orElseThrow { RuntimeException("Informações do usuário não encontradas") }

        return informacao.img_usuario
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

        informacaoEncontrada.contatoEmergencia = informacao.contatoEmergencia;
        informacaoEncontrada.endereco = informacao.endereco;
        informacaoEncontrada.nivel = informacao.nivel;
        informacaoEncontrada.usuario = informacao.usuario;
        informacaoEncontrada.relatorioAnamnese = informacao.relatorioAnamnese;
        informacaoEncontrada.idioma = informacao.idioma;
        informacaoEncontrada.questionarioRespondido = informacao.questionarioRespondido;

        informacaoRepository.save(informacaoEncontrada)

        return InformacoesPessoaisResponseDTO(
            contatoEmergencia = informacao.contatoEmergencia,
            endereco = informacao.endereco,
            nivel = informacao.nivel ?: throw RuntimeException("Nível não informado"),
            usuario = informacao.usuario ?: throw RuntimeException("id não informado"),
            relatorioAnamnese = informacao.relatorioAnamnese,
            idioma = informacao.idioma,
            questionarioRespondido = informacao.questionarioRespondido
        )
    }

}
