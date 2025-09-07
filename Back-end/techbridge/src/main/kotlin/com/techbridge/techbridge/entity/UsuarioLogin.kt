package com.techbridge.techbridge.entity

data class UsuarioLogin(

    open var email: String,
    open var senha: String,
    open var autenticado: Boolean = false,
    open var tipoUsuario: String? = null,
    open var primeiraVez: Boolean = true,
)
