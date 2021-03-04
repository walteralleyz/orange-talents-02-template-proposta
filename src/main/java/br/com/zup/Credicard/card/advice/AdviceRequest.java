package br.com.zup.Credicard.card.advice;

import br.com.zup.Credicard.user.UserRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AdviceRequest {
    @NotBlank
    private final String destino;


    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private final LocalDate validoAte;

    private UserRequest userRequest;

    public AdviceRequest(@NotBlank String destino, @NotNull LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public Advice toModel() {
        return new Advice(
            validoAte,
            destino,
            userRequest.toModel()
        );
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
