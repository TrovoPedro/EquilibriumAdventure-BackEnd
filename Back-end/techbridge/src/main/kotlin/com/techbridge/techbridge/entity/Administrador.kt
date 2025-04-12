package com.techbridge.techbridge.entity

import jakarta.persistence.Entity

@Entity
class Administrador : Usuario() {

    fun setTipoUsuario(novoTipo: String) {
        this.fkTipo_usuario = 1
    }

}
