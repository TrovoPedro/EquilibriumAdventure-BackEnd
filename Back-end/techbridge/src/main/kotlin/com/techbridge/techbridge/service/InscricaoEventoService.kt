package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.InscricaoEventoDTO
import com.techbridge.techbridge.entity.InscricaoEvento
import com.techbridge.techbridge.repository.EventoRepository
import com.techbridge.techbridge.repository.InscricoesEventoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class InscricaoEventoService {

    @Autowired
    lateinit var inscricoesEventoRepository: InscricoesEventoRepository

    fun postInscricao(evento: Long, usuario: Long): InscricaoEventoDTO {
        val evento = EventoRepository.findById(evento)
            .orElseThrow { RuntimeException("Evento não encontrado") }
        val usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow { RuntimeException("Usuário não encontrado") }

        val novaInscricao = InscricaoEvento(
            evento = evento,
            usuario = usuario,
            dataInscricao = LocalDateTime.now()
        )
        return inscricoesEventoRepository.save(novaInscricao)
    }

    fun getInscricao(id: Long): InscricaoEvento {
        return inscricoesEventoRepository.findById(id)
            .orElseThrow { RuntimeException("Inscrição não encontrada") }
    }

    fun deleteInscricao(id: Long) {
        if (!inscricoesEventoRepository.existsById(id)) {
            throw RuntimeException("Inscrição não encontrada")
        }
        inscricoesEventoRepository.deleteById(id)
    }
}