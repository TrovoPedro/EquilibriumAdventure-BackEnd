package com.techbridge.techbridge

import com.techbridge.techbridge.entity.Guia

class Administrador : Guia() {
}

fun setTipoUsuario(novoTipo: String) {
    val administrador = Guia()
    administrador.tipoUsuario = 3
}
