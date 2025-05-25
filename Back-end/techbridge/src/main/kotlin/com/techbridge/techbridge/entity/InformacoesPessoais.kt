package com.techbridge.techbridge.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.techbridge.techbridge.enums.Nivel
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import java.util.*

@Entity
class InformacoesPessoais() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    var id: Long? = null

    var data_nascimento: Date? = null

    @field:Size(min = 11, max = 14)
    var cpf: String? = null

    @field:Size(min = 10, max = 12)
    var rg: String? = null

    @field:Size(min = 8, max = 13)
    var contato_emergencia: String? = null

    @Column(name = "endereco_id", nullable = false)
    var endereco: Long? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var nivel: Nivel

    @Column(name = "usuario_id", nullable = false)
    var usuario: Long = 0

    var relatorioAnamnese: String? = null
    var idioma: String? = null
    var questionario_respondido: Boolean? = null
}
