package com.techbridge.techbridge.entity

import jakarta.persistence.*

@Entity
@Table(name = "resposta_aventureiro")
data class RespostaAventureiro(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resposta")
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "fk_aventureiro", nullable = false)
    val usuario: Usuario,

    @ManyToOne
    @JoinColumn(name = "fk_pergunta", nullable = false)
    val pergunta: PerguntaAlternativa,

    @Column(name = "alternativa_escolhida", nullable = false)
    val alternativaEscolhida: Int
) {
    constructor() : this(
        id = 0,
        usuario = Usuario(),
        pergunta = PerguntaAlternativa(),
        alternativaEscolhida = 0
    )
}
