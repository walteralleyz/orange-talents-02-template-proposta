package br.com.zup.Credicard.card.blocking;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

public class BlockingDTO {
    private final String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "America/Sao_Paulo")
    private final LocalDateTime bloqueadoEm;

    private final String sistemaResponsavel;
    private final boolean ativo;

    public BlockingDTO(
        String id,
        LocalDateTime bloqueadoEm,
        String sistemaResponsavel,
        boolean ativo
    ) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
    }

    public Blocking toModel() {
        return new Blocking(
            id,
            bloqueadoEm,
            sistemaResponsavel,
            ativo
        );
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getBloqueadoEm() {
        return bloqueadoEm;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
