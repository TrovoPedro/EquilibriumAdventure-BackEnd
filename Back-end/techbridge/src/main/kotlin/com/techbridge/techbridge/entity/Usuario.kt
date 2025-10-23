package com.techbridge.techbridge.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.techbridge.techbridge.enums.TipoUsuario
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
open class Usuario(

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    open var idUsuario: Long? = null,

    @field:NotBlank
    @field:Size(min = 2, max = 100)
    open var nome: String? = null,

    @field:Size(min = 11, max = 15)
    open var telefoneContato: String? = null,

    @field:NotBlank
    @field:Size(min = 11, max = 100)
    open var email: String? = null,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @field:NotBlank
    @field:Size(min = 8, max = 100)
    open var senha: String? = null,

    @field:Size(min = 8, max = 100)
    open var descricao_guia: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var tipo_usuario: TipoUsuario? = null,

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    var img_usuario: ByteArray? = null,

    @Column(name = "primeira_vez", nullable = false)
    open var primeiraVez: Boolean = true
)
