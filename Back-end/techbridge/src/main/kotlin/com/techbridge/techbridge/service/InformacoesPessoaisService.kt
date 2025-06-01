package com.techbridge.techbridge.service;

import com.techbridge.techbridge.dto.InformacoesPessoaisGetPerfilDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisRequestDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisResponseDTO
import com.techbridge.techbridge.repository.EnderecoRepository
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository;
import com.techbridge.techbridge.repository.UsuarioRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping

@Service
@Transactional
class InformacoesPessoaisService {

    @Autowired
    lateinit var informacaoRepository: InformacoesPessoaisRepository;

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository;

    @Autowired
    lateinit var enderecoRepository: EnderecoRepository

    fun postInformacoes(id: Long, informacao: InformacoesPessoaisRequestDTO): InformacoesPessoaisResponseDTO {
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

        // Prepara a entidade para salvar, setando o usuário com o id do path
        val entidade = informacao.toEntity()
        entidade.usuario = id // garante que o usuário é o do path (e não do DTO)
        entidade.endereco = informacao.endereco

        val novaInformacao = informacaoRepository.save(entidade)

        return InformacoesPessoaisResponseDTO(
            contatoEmergencia = novaInformacao.contatoEmergencia,
            endereco = novaInformacao.endereco ?: 0,
            nivel = novaInformacao.nivel ?: throw RuntimeException("Nível não informado"),
            usuario = novaInformacao.usuario,
            relatorioAnamnese = novaInformacao.relatorioAnamnese,
            idioma = novaInformacao.idioma,
            questionarioRespondido = novaInformacao.questionarioRespondido
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
            nivel = informacao.nivel,
            usuario = informacao.usuario,
            relatorioAnamnese = informacao.relatorioAnamnese,
            idioma = informacao.idioma,
            questionarioRespondido = informacao.questionarioRespondido
        )
    }

}
