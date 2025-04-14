package com.techbridge.techbridge.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuario")
open class Usuario(

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    open var id_usuario: Int? = null,

    @field:NotBlank
    @field:Size(min = 2, max = 100)
    open var nome: String? = null,

    @field:NotBlank
    @field:Size(min = 11, max = 100)
    open var email: String = "",

    @field:NotBlank
    @field:Size(min = 8, max = 100)
    open var senha: String? = null,

    @field:NotBlank
    @field:Size(min = 11, max = 15)
    open var telefone_contato: String? = null,

    open var fk_tipo_usuario: Int? = null,
    open var fk_nivel: Int? = null

) {
    constructor() : this(null, null, "", null, null, null)
}
