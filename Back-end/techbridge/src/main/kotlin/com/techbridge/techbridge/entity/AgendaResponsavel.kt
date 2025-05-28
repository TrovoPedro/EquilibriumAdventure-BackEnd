package com.techbridge.techbridge.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "agenda_responsavel")
data class AgendaResponsavel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agenda")
    var idAgenda: Int = 0,

    @Column(name = "data_disponivel", nullable = false)
    var dataDisponivel: LocalDateTime = LocalDateTime.now(),

    @JoinColumn(name = "fk_responsavel")
    @ManyToOne
    var fkresponsavel: Usuario? = null
)