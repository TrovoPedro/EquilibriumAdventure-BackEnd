package com.techbridge.techbridge.entity

import jakarta.persistence.Entity

@Entity
class Guia : Usuario() {

    fun setTipoUsuario(novoTipo: String) {
        this.fkTipo_usuario = 1
    }

}
