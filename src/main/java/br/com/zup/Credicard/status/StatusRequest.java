package br.com.zup.Credicard.status;

public class StatusRequest {
    private final Long idProposta;
    private final String documento;
    private final String nome;

    public StatusRequest(Long idProposta, String documento, String nome) {
        this.idProposta = idProposta;
        this.documento = documento;
        this.nome = nome;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }
}
