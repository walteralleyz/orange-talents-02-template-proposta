package br.com.zup.Credicard.card.blocking;

public class BlockingRequest {
    private String sistemaResponsavel;

    @Deprecated
    public BlockingRequest() {}

    public BlockingRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
