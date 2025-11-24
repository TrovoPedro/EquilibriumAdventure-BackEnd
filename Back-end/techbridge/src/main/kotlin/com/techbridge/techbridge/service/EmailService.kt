package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.EmailRequest
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class EmailService(
    private val restTemplate: RestTemplate
) {

    fun enviarEmailCancelamento(
        to: String?,
        nomeUsuario: String?,
        nomeTrilha: String?,
        dataEvento: String,
        motivo: String
    ) {
        val emailRequest = EmailRequest(
            to = to,
            tipo = "USUARIO_REMOVIDO",
            nomeUsuario = nomeUsuario,
            nomeTrilha = nomeTrilha,
            dataEvento = dataEvento,
            motivo = motivo
        )

        restTemplate.postForEntity(
            "http://localhost:8081/emails/send",
            emailRequest,
            String::class.java
        )
    }
}
