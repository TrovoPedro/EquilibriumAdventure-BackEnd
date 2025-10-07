package com.techbridge.techbridge.entity

data class UsuarioLogin(
    val id: Long? = null,
    open var email: String,
    open var nome: String? = null,
    open var senha: String,
    open var autenticado: Boolean = false,
    open var tipoUsuario: String? = null,
    open var primeiraVez: Boolean = true,
)