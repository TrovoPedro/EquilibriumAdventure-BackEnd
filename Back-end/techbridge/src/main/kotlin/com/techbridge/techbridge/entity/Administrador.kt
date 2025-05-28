package com.techbridge.techbridge.entity

import com.techbridge.techbridge.enums.TipoUsuario

class Administrador: Usuario(
    fk_tipo_usuario = TipoUsuario.ADM
) {
}