package com.techbridge.techbridge.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "agenda_responsavel")
data class AgendaResponsavel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agenda")
    val idAgenda: Int = 0,

    @Column(name = "data_disponivel", nullable = false)
    val dataDisponivel: LocalDateTime,

    @JoinColumn(name = "fk_responsavel")
    @ManyToOne
    val fkresponsavel: Usuario,
)
