import com.fasterxml.jackson.annotation.JsonIgnore

data class Usuario (
    var nomeUsuario: String = "",
    var dtNascUsuario: String = "",
    var telContatoUsuario: String = "",
    var cpf: String = "",
    var rg: String = "",
    var endereco: String = "",
    var email: String = "",
    @JsonIgnore
    private var senha: String = ""
) {
    fun setSenha(novaSenha: String) {
        senha = novaSenha
    }

    fun getSenha(): String {
        return senha
    }
}
