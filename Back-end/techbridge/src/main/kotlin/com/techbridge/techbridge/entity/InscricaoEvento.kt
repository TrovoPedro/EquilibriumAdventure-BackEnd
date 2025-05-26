package com.techbridge.techbridge.entity

import jakarta.persistence.*

@Entity
@Table(name = "inscricoes_evento")
class InscricaoEvento(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    val evento: Evento,

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    val usuario: Usuario,

    @Column(name = "data_inscricao", nullable = false)
    val dataInscricao: java.time.LocalDateTime = java.time.LocalDateTime.now()
)