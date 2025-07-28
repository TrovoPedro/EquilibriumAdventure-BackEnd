package com.techbridge.techbridge.entity

import jakarta.persistence.*

@Entity
@Table(name = "pergunta_alternativa")
data class PerguntaAlternativa(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pergunta")
    val id: Int = 0,

    @Column(name = "texto_pergunta", nullable = false)
    val textoPergunta: String = "",

    @Column(name = "alternativa_1", nullable = false)
    val alternativa1: String = "",

    @Column(name = "valor_1", nullable = false)
    val valor1: Int = 0,

    @Column(name = "alternativa_2", nullable = false)
    val alternativa2: String = "",

    @Column(name = "valor_2", nullable = false)
    val valor2: Int = 0,

    @Column(name = "alternativa_3", nullable = false)
    val alternativa3: String = "",

    @Column(name = "valor_3", nullable = false)
    val valor3: Int = 0,

    @Column(name = "alternativa_4", nullable = false)
    val alternativa4: String = "",

    @Column(name = "valor_4", nullable = false)
    val valor4: Int = 0
) {
    constructor() : this(
        id = 0,
        textoPergunta = "",
        alternativa1 = "",
        valor1 = 0,
        alternativa2 = "",
        valor2 = 0,
        alternativa3 = "",
        valor3 = 0,
        alternativa4 = "",
        valor4 = 0
    )
}
