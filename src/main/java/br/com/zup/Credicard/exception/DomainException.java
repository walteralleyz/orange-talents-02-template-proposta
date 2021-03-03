package br.com.zup.Credicard.exception;

public class DomainException extends RuntimeException {
    private final String field;

    public DomainException(String field, String entity) {
        super(String.format("Can't modify %s state", entity));

        this.field = field;
    }

    public String getField() {
        return field;
    }
}
