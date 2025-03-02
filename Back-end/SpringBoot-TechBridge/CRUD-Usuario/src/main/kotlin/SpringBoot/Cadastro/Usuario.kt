package SpringBoot.Cadastro

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate

/*
Para que yum atributo não seja exposto no JSON gerado
Podemos:
1 - marcar ele como priva
aate (desvantagem: nem no código teremos acesso público a ele)
2 - anotar o atributo com @JsonIgnore
 */
data class Usuario (
    var nomeUsuario: String = "",
    var dtNascUsuario: String = "",
    var telContatoUsuario: String = "",
/*    var cpf: String = "",
    var rg: String = "",b
    var endereco: String = "",
    var email: String = "",
    private var senha: String = ""
*/
) {

}
