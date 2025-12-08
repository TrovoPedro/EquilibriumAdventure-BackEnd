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

    fun enviarEmailCancelamentoEvento(
        emails: List<String>,
        nomeTrilha: String?,
        dataEvento: String,
        motivo: String = "Evento cancelado pelo administrador"
    ) {
        emails.forEach { email ->

            val body = mapOf(
                "to" to email,
                "tipo" to "EVENTO_CANCELADO",
                "nomeUsuario" to email.substringBefore("@"), // ajuste se tiver nome real
                "nomeTrilha" to nomeTrilha,
                "dataEvento" to dataEvento,
                "motivo" to motivo
            )

            restTemplate.postForEntity(
                "http://localhost:8081/emails/send",
                body,
                String::class.java
            )
        }
    }

}
