package br.com.zup.Credicard.blocking;

public class BlockingResponse {
    private String resultado;

    public BlockingResponse() {}

    public BlockingResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public boolean isBlocked() {
        return resultado.equals("BLOQUEADO");
    }
}
