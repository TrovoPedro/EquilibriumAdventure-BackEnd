package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.InformacoesPessoaisGetPerfilDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisRequestDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisResponseDTO
import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.repository.EnderecoRepository
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
@Transactional
class InformacoesPessoaisService {

    @Autowired
    lateinit var informacaoRepository: InformacoesPessoaisRepository

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

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

    fun getInformacoesPerfilPorUsuarioId(usuarioId: Long): InformacoesPessoaisGetPerfilDTO? {
        return informacaoRepository.buscarInformacoesPerfil(usuarioId)
    }

    @Transactional
    fun putInformacoes(id: Long, informacao: InformacoesPessoaisRequestDTO): InformacoesPessoaisResponseDTO {
        if (informacao == null) {
            throw RuntimeException("Informações em branco")
        }

        val informacaoEncontrada = informacaoRepository.findById(id).orElseThrow {
            RuntimeException("Informações não encontradas")
        }

        // Atualize todos os campos
        informacaoEncontrada.dataNascimento = informacao.dataNascimento
        informacaoEncontrada.cpf = informacao.cpf
        informacaoEncontrada.rg = informacao.rg
        informacaoEncontrada.contatoEmergencia = informacao.contatoEmergencia
        informacaoEncontrada.endereco = informacao.endereco
        informacaoEncontrada.nivel = informacao.nivel
        informacaoEncontrada.usuario = informacao.usuario
        informacaoEncontrada.relatorioAnamnese = informacao.relatorioAnamnese
        informacaoEncontrada.idioma = informacao.idioma
        informacaoEncontrada.questionarioRespondido = informacao.questionarioRespondido

        informacaoRepository.save(informacaoEncontrada)

        return InformacoesPessoaisResponseDTO(
            contatoEmergencia = informacaoEncontrada.contatoEmergencia,
            endereco = informacaoEncontrada.endereco ?: 0,
            nivel = informacaoEncontrada.nivel ?: throw RuntimeException("Nível não informado"),
            usuario = informacaoEncontrada.usuario ?: throw RuntimeException("id não informado"),
            relatorioAnamnese = informacaoEncontrada.relatorioAnamnese,
            idioma = informacaoEncontrada.idioma,
            questionarioRespondido = informacaoEncontrada.questionarioRespondido
        )
    }

    fun atualizarPorUsuario(usuarioId: Long, informacao: InformacoesPessoaisRequestDTO): InformacoesPessoaisResponseDTO {
        // Validação de campos obrigatórios
        if (informacao.endereco == 0L) throw RuntimeException("Endereço obrigatório")
        if (informacao.nivel == null) throw RuntimeException("Nível obrigatório")

        // Verifica se o usuário existe
        val usuario = usuarioRepository.findById(usuarioId)
        if (usuario.isEmpty) throw RuntimeException("Usuário não encontrado")

        // Verifica se o endereço existe
        val enderecoExiste = enderecoRepository.existsById(informacao.endereco)
        if (!enderecoExiste) throw RuntimeException("Endereço não encontrado")

        // Busca as informações pessoais pelo usuário
        val entidade = informacaoRepository.buscarPorUsuario(usuarioId)
            ?: throw RuntimeException("Informações não encontradas para usuário $usuarioId")

        // Atualiza os campos
        entidade.dataNascimento = informacao.dataNascimento
        entidade.cpf = informacao.cpf
        entidade.rg = informacao.rg
        entidade.contatoEmergencia = informacao.contatoEmergencia
        entidade.endereco = informacao.endereco
        entidade.nivel = informacao.nivel
        entidade.usuario = usuarioId
        entidade.relatorioAnamnese = informacao.relatorioAnamnese
        entidade.idioma = informacao.idioma
        entidade.questionarioRespondido = informacao.questionarioRespondido

        informacaoRepository.save(entidade)

        return InformacoesPessoaisResponseDTO(
            contatoEmergencia = entidade.contatoEmergencia,
            endereco = entidade.endereco ?: 0,
            nivel = entidade.nivel ?: throw RuntimeException("Nível não informado"),
            usuario = entidade.usuario ?: throw RuntimeException("id não informado"),
            relatorioAnamnese = entidade.relatorioAnamnese,
            idioma = entidade.idioma,
            questionarioRespondido = entidade.questionarioRespondido
        )
    }
}
