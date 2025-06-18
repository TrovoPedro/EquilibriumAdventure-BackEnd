package com.techbridge.techbridge.entity

import com.techbridge.techbridge.enums.TipoUsuario
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue(value = "1")
class Aventureiro: Usuario(

    tipo_usuario = TipoUsuario.AVENTUREIRO
) {

}