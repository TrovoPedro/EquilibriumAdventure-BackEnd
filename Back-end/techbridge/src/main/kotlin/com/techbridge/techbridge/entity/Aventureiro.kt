package com.techbridge.techbridge.entity

import com.techbridge.techbridge.enums.TipoUsuario

class Aventureiro: Usuario(

    fk_tipo_usuario = TipoUsuario.AVENTUREIRO
) {

}