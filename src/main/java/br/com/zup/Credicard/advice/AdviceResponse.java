package br.com.zup.Credicard.advice;

public class AdviceResponse {
    private String resultado;

    @Deprecated
    public AdviceResponse() {}

    public AdviceResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public boolean isAdviced() {
        return resultado.equals("CRIADO");
    }
}
