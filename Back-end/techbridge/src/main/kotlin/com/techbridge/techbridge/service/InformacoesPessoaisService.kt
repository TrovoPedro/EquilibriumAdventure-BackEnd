package com.techbridge.techbridge.service;

import com.techbridge.techbridge.dto.InformacoesPessoaisRequestDTO
import com.techbridge.techbridge.dto.InformacoesPessoaisResponseDTO
import com.techbridge.techbridge.repository.InformacoesPessoasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class InformacoesPessoaisService {

    @Autowired
    lateinit var informacaoRepository: InformacoesPessoasRepository;

    fun postInformacoes(id: Long, informacao: InformacoesPessoaisRequestDTO): InformacoesPessoaisResponseDTO {
        if (informacao == null){
            throw RuntimeException("Informações não preenchidas");
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
}
