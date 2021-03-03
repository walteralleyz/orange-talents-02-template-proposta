package br.com.zup.Credicard.card.blocking;

public class BlockingResponse {
    private String resultado;

    public BlockingResponse() {}

    public BlockingResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
