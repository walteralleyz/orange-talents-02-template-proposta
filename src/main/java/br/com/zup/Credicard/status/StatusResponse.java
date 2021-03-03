package br.com.zup.Credicard.status;

public class StatusResponse {
    private final Long idProposta;
    private final String documento;
    private final String nome;
    private final StatusType resultadoSolicitacao;

    public StatusResponse(Long idProposta, String documento, String nome, StatusType resultadoSolicitacao) {
        this.idProposta = idProposta;
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
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

    public StatusType getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
