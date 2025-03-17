package com.techbridge.techbridge

class Administrador : Guia() {
}

fun setTipoUsuario(novoTipo: String) {
    val administrador = Guia()
    administrador.tipoUsuario = 3
}
