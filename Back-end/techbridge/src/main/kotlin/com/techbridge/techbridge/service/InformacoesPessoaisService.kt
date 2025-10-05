package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.*
import com.techbridge.techbridge.entity.Endereco
import com.techbridge.techbridge.entity.InformacoesPessoais
import com.techbridge.techbridge.entity.Usuario
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
        if (informacao == null) throw RuntimeException("Informações não preenchidas")

        val usuarioEncontrado = usuarioRepository.findById(id).orElseThrow { RuntimeException("Usuário não encontrado") }
        val enderecoEncontrado = enderecoRepository.findById(informacao.endereco).orElseThrow { RuntimeException("Endereço não encontrado") }

        // Verifica se já existe InformacoesPessoais para o usuário
        val existente = informacaoRepository.buscarPorUsuario(usuarioEncontrado.idUsuario!!)
        val entidade = if (existente != null) {
            // Atualiza os campos da entidade existente
            existente.dataNascimento = informacao.dataNascimento
            existente.cpf = informacao.cpf
            existente.rg = informacao.rg
            existente.contatoEmergencia = informacao.contatoEmergencia
            existente.endereco = enderecoEncontrado
            existente.nivel = informacao.nivel
            existente.relatorioAnamnese = informacao.relatorioAnamnese
            existente.idioma = informacao.idioma
            existente.questionarioRespondido = informacao.questionarioRespondido
            existente.img_usuario = img_usuario?.bytes
            existente
        } else {
            val novo = informacao.toEntity(enderecoEncontrado, usuarioEncontrado)
            novo.img_usuario = img_usuario?.bytes
            novo
        }

        val novaInformacao = informacaoRepository.save(entidade)

        return InformacoesPessoaisResponseDTO(
            contatoEmergencia = novaInformacao.contatoEmergencia,
            endereco = novaInformacao.endereco?.id_endereco ?: 0,
            nivel = novaInformacao.nivel ?: throw RuntimeException("Nível não informado"),
            usuario = novaInformacao.usuario?.idUsuario ?: throw RuntimeException("id não informado"),
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
        val informacaoEncontrada = informacaoRepository.findById(id).orElseThrow {
            RuntimeException("Informações não encontradas")
        }

        val usuarioEncontrado = usuarioRepository.findById(informacao.usuario ?: 0)
            .orElseThrow { RuntimeException("Usuário não encontrado") }
        val enderecoEncontrado = enderecoRepository.findById(informacao.endereco)
            .orElseThrow { RuntimeException("Endereço não encontrado") }

        informacaoEncontrada.dataNascimento = informacao.dataNascimento
        informacaoEncontrada.cpf = informacao.cpf
        informacaoEncontrada.rg = informacao.rg
        informacaoEncontrada.contatoEmergencia = informacao.contatoEmergencia
        informacaoEncontrada.endereco = enderecoEncontrado
        informacaoEncontrada.nivel = informacao.nivel
        informacaoEncontrada.usuario = usuarioEncontrado
        informacaoEncontrada.relatorioAnamnese = informacao.relatorioAnamnese
        informacaoEncontrada.idioma = informacao.idioma
        informacaoEncontrada.questionarioRespondido = informacao.questionarioRespondido

        informacaoRepository.save(informacaoEncontrada)

        return InformacoesPessoaisResponseDTO(
            contatoEmergencia = informacaoEncontrada.contatoEmergencia,
            endereco = informacaoEncontrada.endereco?.id_endereco ?: 0,
            nivel = informacaoEncontrada.nivel ?: throw RuntimeException("Nível não informado"),
            usuario = informacaoEncontrada.usuario?.idUsuario ?: throw RuntimeException("id não informado"),
            relatorioAnamnese = informacaoEncontrada.relatorioAnamnese,
            idioma = informacaoEncontrada.idioma,
            questionarioRespondido = informacaoEncontrada.questionarioRespondido
        )
    }

    fun atualizarPorUsuario(usuarioId: Long, informacao: InformacoesPessoaisRequestDTO): InformacoesPessoaisResponseDTO {
        if (informacao.endereco == 0L) throw RuntimeException("Endereço obrigatório")
        if (informacao.nivel == null) throw RuntimeException("Nível obrigatório")

        val usuarioEncontrado = usuarioRepository.findById(usuarioId)
            .orElseThrow { RuntimeException("Usuário não encontrado") }
        val enderecoEncontrado = enderecoRepository.findById(informacao.endereco)
            .orElseThrow { RuntimeException("Endereço não encontrado") }

        val entidade = informacaoRepository.buscarPorUsuario(usuarioId)
            ?: throw RuntimeException("Informações não encontradas para usuário $usuarioId")

        entidade.dataNascimento = informacao.dataNascimento
        entidade.cpf = informacao.cpf
        entidade.rg = informacao.rg
        entidade.contatoEmergencia = informacao.contatoEmergencia
        entidade.endereco = enderecoEncontrado
        entidade.nivel = informacao.nivel
        entidade.usuario = usuarioEncontrado
        entidade.relatorioAnamnese = informacao.relatorioAnamnese
        entidade.idioma = informacao.idioma
        entidade.questionarioRespondido = informacao.questionarioRespondido

        informacaoRepository.save(entidade)

        return InformacoesPessoaisResponseDTO(
            contatoEmergencia = entidade.contatoEmergencia,
            endereco = entidade.endereco?.id_endereco ?: 0,
            nivel = entidade.nivel ?: throw RuntimeException("Nível não informado"),
            usuario = entidade.usuario?.idUsuario ?: throw RuntimeException("id não informado"),
            relatorioAnamnese = entidade.relatorioAnamnese,
            idioma = entidade.idioma,
            questionarioRespondido = entidade.questionarioRespondido
        )
    }

    fun cadastrarPerfilCompleto(dto: CadastroPerfilDTO, usuarioId: Long): InformacoesPessoaisGetPerfilDTO {
        val enderecoEntity = Endereco(
            rua = dto.endereco.rua,
            numero = dto.endereco.numero,
            complemento = dto.endereco.complemento,
            bairro = dto.endereco.bairro,
            cidade = dto.endereco.cidade,
            estado = dto.endereco.estado,
            cep = dto.endereco.cep
        )
        val enderecoSalvo = enderecoRepository.save(enderecoEntity)
        val usuarioEncontrado = usuarioRepository.findById(usuarioId)
            .orElseThrow { RuntimeException("Usuário não encontrado") }

        val existente = informacaoRepository.buscarPorUsuario(usuarioId)
        val infoEntity = if (existente != null) {
            existente.dataNascimento = dto.informacoes.dataNascimento
            existente.cpf = dto.informacoes.cpf
            existente.rg = dto.informacoes.rg
            existente.contatoEmergencia = dto.informacoes.contatoEmergencia
            existente.endereco = enderecoSalvo
            // Mantém o nível já existente, não sobrescreve
            existente.relatorioAnamnese = dto.informacoes.relatorioAnamnese
            existente.idioma = dto.informacoes.idioma
            existente.questionarioRespondido = dto.informacoes.questionarioRespondido
            existente
        } else {
            val entidade = dto.informacoes.toEntity(enderecoSalvo, usuarioEncontrado)
            // Só atribui nível se vier no DTO
            if (dto.informacoes.nivel != null) {
                entidade.nivel = dto.informacoes.nivel
            }
            entidade
        }

        informacaoRepository.save(infoEntity)

        return informacaoRepository.buscarInformacoesPerfil(usuarioId)
            ?: throw RuntimeException("Perfil não encontrado")
    }

    fun editarPerfilCompleto(usuarioId: Long, dto: EditarPerfilDTO): InformacoesPessoaisGetPerfilDTO {
        val usuario = usuarioRepository.findById(usuarioId).orElseThrow { RuntimeException("Usuário não encontrado") }
        usuario.nome = dto.nome
        usuario.email = dto.email
        usuario.telefoneContato = dto.telefoneContato
        usuarioRepository.save(usuario)

        val info = informacaoRepository.buscarPorUsuario(usuarioId) ?: throw RuntimeException("Informações não encontradas")
        val endereco = info.endereco ?: throw RuntimeException("Endereço não encontrado")
        endereco.rua = dto.endereco.rua
        endereco.numero = dto.endereco.numero
        endereco.complemento = dto.endereco.complemento
        endereco.bairro = dto.endereco.bairro
        endereco.cidade = dto.endereco.cidade
        endereco.estado = dto.endereco.estado
        endereco.cep = dto.endereco.cep
        enderecoRepository.save(endereco)

        info.dataNascimento = dto.informacoes.dataNascimento
        info.cpf = dto.informacoes.cpf
        info.rg = dto.informacoes.rg
        info.contatoEmergencia = dto.informacoes.contatoEmergencia
        info.nivel = dto.informacoes.nivel
        info.relatorioAnamnese = dto.informacoes.relatorioAnamnese
        info.idioma = dto.informacoes.idioma
        info.questionarioRespondido = dto.informacoes.questionarioRespondido
        informacaoRepository.save(info)

        return informacaoRepository.buscarInformacoesPerfil(usuarioId)
            ?: throw RuntimeException("Perfil não encontrado")
    }
}