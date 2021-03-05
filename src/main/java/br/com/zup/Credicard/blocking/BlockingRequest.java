package br.com.zup.Credicard.blocking;

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
