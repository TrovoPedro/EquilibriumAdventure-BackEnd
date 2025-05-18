package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.*
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.enums.TipoUsuario
import com.techbridge.techbridge.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService {

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository;

    fun getUsuario(id: Int): UsuarioResponseDTO {
        val usuario: Usuario = usuarioRepository.findById(id)
            .orElseThrow { RuntimeException("Usuário não encontrado") }

        return UsuarioResponseDTO(
            nome = usuario.nome,
            telefone_contato = usuario.telefone_contato,
            email = usuario.email,
            descricao_guia = usuario.descricao_guia
        )
    }

    fun postUsuario(novoUsuario: UsuarioRequestDTO): Any {
        if (
            novoUsuario.nome.isNullOrBlank() ||
            novoUsuario.telefone_contato.isNullOrBlank() ||
            novoUsuario.email.isNullOrBlank() ||
            novoUsuario.senha.isNullOrBlank() ||
            novoUsuario.fk_tipo_usuario == null
        ) {
            throw RuntimeException("Valores obrigatórios estão nulos ou vazios")
        }

        val usuarioSalvo = usuarioRepository.save(novoUsuario.toEntity())

        if(usuarioSalvo.fk_tipo_usuario == TipoUsuario.AVENTUREIRO){
            return AventureiroResponseDTO(
                nome = usuarioSalvo.nome,
                telefone_contato = usuarioSalvo.telefone_contato,
                email = usuarioSalvo.email,
            )
        }

        return UsuarioResponseDTO(
            nome = usuarioSalvo.nome,
            telefone_contato = usuarioSalvo.telefone_contato,
            email = usuarioSalvo.email,
            descricao_guia = usuarioSalvo.descricao_guia
        )
    }

    fun putUsuario(id: Int, informacoesNova: EditarInformacoesDTO): Usuario{
        val usuarioEncontrado = usuarioRepository.findById(id)
            .orElseThrow(){RuntimeException("Usuário não encontrado")}

        usuarioEncontrado.nome = informacoesNova.nome;
        usuarioEncontrado.email = informacoesNova.email;
        usuarioEncontrado.telefone_contato = informacoesNova.telefone_contato;

        return usuarioRepository.save(usuarioEncontrado);
    }

    fun deleteUsuario(id: Int){
        val usuarioEncontrado = usuarioRepository.findById(id);

        if(usuarioEncontrado == null){
            throw RuntimeException("Usuário não encontrado")
        }

        return usuarioRepository.deleteById(id);
    }

    fun patchSenha(novaSenha: AlterarSenhaDTO, id: Int): Usuario {
        val usuarioEncontrado = usuarioRepository.findById(id)
            .orElseThrow { RuntimeException("Usuário não encontrado") }

        usuarioEncontrado.senha = novaSenha.senha

        return usuarioRepository.save(usuarioEncontrado)
    }

}