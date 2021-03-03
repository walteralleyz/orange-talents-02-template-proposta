package br.com.zup.Credicard.card;

public class CardRequest {
    private final String documento;
    private final String nome;
    private final String idProposta;

    public CardRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
