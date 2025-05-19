package com.techbridge.techbridge.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.techbridge.techbridge.enums.Nivel
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.Date

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class InformacoesPessoais(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    private var id: Long? = null,

    private var data_nascimento: Date?,

    @field:Size(min = 11, max = 14)
    private var cpf: String?,

    @field:Size(min = 10, max = 12)
    private var rg: String?,

    @field:Size(min = 8, max = 13)
    private var contato_emergencia: String?,

    @Column(name = "endereco_id", nullable = false)
    private var endereco: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private var nivel: Nivel,

    @Column(name = "usuario_id", nullable = false)
    var usuario: Long,

    private var relatorioAnamnese: String?,
    private var idioma: String?,
    private var questionario_respondido: Boolean?,
)
