import com.techbridge.techbridge.dto.EventoAtivacaoDTO
import com.techbridge.techbridge.dto.EventoResponseDTO
import java.sql.Time
import java.time.LocalDate
import com.techbridge.techbridge.enums.EstadoEvento

data class AtivacaoEventoDTO(
    val idAtivacao: Long,
    val horaInicio: Time?,
    val horaFinal: Time?,
    val tempoEstimado: Double?,
    val limiteInscritos: Int?,
    val dataAtivacao: LocalDate?,
    val tipo: String?,
    val preco: Double?,
    val estado: EstadoEvento?,
    val evento: EventoAtivacaoDTO?
)
