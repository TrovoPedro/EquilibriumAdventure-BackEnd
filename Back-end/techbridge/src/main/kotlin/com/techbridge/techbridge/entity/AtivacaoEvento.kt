package com.techbridge.techbridge.entity

import com.techbridge.techbridge.enums.EstadoEvento
import jakarta.persistence.*
import java.sql.Time
import java.time.LocalDate

@Entity
@Table(name = "ativacao_evento")
class AtivacaoEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ativacao")
    var idAtivacao: Long = 0

    @Column(name = "hora_inicio")
    var horaInicio: Time? = null

    @Column(name = "hora_final")
    var horaFinal: Time? = null

    @Column(name = "tempo_estimado")
    var tempoEstimado: Double? = null

    @Column(name = "limite_inscritos")
    var limiteInscritos: Int? = null

    @Column(name = "data_ativacao")
    var dataAtivacao: LocalDate? = null

    @Column(name = "tipo", length = 45)
    var tipo: String? = null

    @Column(name = "preco")
    var preco: Double? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "log")
    var estado: EstadoEvento? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_evento")
    var evento: Evento? = null


    constructor()

    constructor(idAtivacao: Long, limiteInscritos: Int?) {
        this.idAtivacao = idAtivacao
        this.limiteInscritos = limiteInscritos
    }
}