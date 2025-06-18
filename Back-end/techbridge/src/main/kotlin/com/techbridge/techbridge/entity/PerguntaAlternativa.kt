package com.techbridge.techbridge.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "pergunta_alternativa")
class PerguntaAlternativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pergunta")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    var id: Int? = null

    @Column(name = "texto_pergunta", nullable = false)
    var textoPergunta: String? = null

    @Column(name = "alternativa_1", nullable = false)
    var alternativa1: String? = null

    @Column(name = "valor_1", nullable = false)
    var valor1: Int? = null

    @Column(name = "alternativa_2", nullable = false)
    var alternativa2: String? = null

    @Column(name = "valor_2", nullable = false)
    var valor2: Int? = null

    @Column(name = "alternativa_3", nullable = false)
    var alternativa3: String? = null

    @Column(name = "valor_3", nullable = false)
    var valor3: Int? = null

    @Column(name = "alternativa_4", nullable = false)
    var alternativa4: String? = null

    @Column(name = "valor_4", nullable = false)
    var valor4: Int? = null
}