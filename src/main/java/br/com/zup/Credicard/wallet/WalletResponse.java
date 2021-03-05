package br.com.zup.Credicard.wallet;

public class WalletResponse {
    private final String resultado;
    private final String id;

    public WalletResponse(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }

    public boolean isAssociated() {
        return resultado.equals("ASSOCIADA");
    }
}
