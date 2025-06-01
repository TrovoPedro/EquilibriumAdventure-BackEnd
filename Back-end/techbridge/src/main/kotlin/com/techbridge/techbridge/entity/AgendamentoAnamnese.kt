package com.techbridge.techbridge.entity

import jakarta.persistence.*

@Entity
@Table(name = "agendamento_anamnese")
data class AgendamentoAnamnese(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anamnese")
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "fk_data", nullable = false)
    val agendaResponsavel: AgendaResponsavel? = null,

    @ManyToOne
    @JoinColumn(name = "fk_aventureiro", nullable = false)
    val aventureiro: Usuario? = null
) {
    constructor() : this(0, null, null)
}