package com.techbridge.techbridge.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "inscricao")
class Inscricao(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id_inscricao: Long? = null,

    @ManyToOne
    @JoinColumn(name = "aventureiro", nullable = false)
    val aventureiro: Usuario,

    @ManyToOne
    @JoinColumn(name = "ativacaoEvento", nullable = false)
    val evento: Evento,

    @Column(name = "dataInscricao", nullable = false)
    val dataInscricao: java.time.LocalDateTime = java.time.LocalDateTime.now()

) {
    constructor() : this(
        id_inscricao = null,
        evento = Evento(),
        aventureiro = Usuario(),
        dataInscricao = LocalDateTime.now()
    )
}