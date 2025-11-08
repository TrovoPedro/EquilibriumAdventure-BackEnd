package com.techbridge.techbridge.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.techbridge.techbridge.enums.Nivel
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import java.util.*

@Entity
@Table(
    name = "informacoes_pessoais",
    uniqueConstraints = [UniqueConstraint(columnNames = ["fk_aventureiro"])]
)
class InformacoesPessoais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informacao")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    var id: Long? = null

    @Column(name = "data_nascimento")
    var dataNascimento: Date? = null

    @Column(name = "cpf")
    @field:Size(min = 11, max = 14)
    var cpf: String? = null

    @Column(name = "rg")
    @field:Size(min = 10, max = 12)
    var rg: String? = null

    @Column(name = "contato_emergencia")
    @field:Size(min = 8, max = 14)
    var contatoEmergencia: String? = null

    @Column(name = "idioma")
    var idioma: String? = null

    @Column(name = "relatorio_anamnese")
    var relatorioAnamnese: String? = null

    @Column(name = "questionario_respondido")
    var questionarioRespondido: Boolean? = null

    @ManyToOne
    @JoinColumn(name = "fk_endereco")
    var endereco: Endereco? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel", nullable = false)
    var nivel: Nivel? = null

    @ManyToOne
    @JoinColumn(name = "fk_aventureiro", nullable = false)
    var usuario: Usuario? = null

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    var img_usuario: ByteArray? = null

    @Column(name = "pontuacao_total")
    var pontuacaoTotal: Int = 0

    constructor()

    constructor(usuario: Usuario, nivel: Nivel, questionarioRespondido: Boolean) {
        this.usuario = usuario
        this.nivel = nivel
        this.questionarioRespondido = questionarioRespondido
    }
}
