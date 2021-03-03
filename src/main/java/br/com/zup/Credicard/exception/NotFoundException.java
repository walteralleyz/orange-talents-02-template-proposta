package br.com.zup.Credicard.exception;

public class NotFoundException extends RuntimeException {
    private final String field;

    public NotFoundException(String entity) {
        super(String.format("%s not found", entity));

        this.field = "id";
    }

    public String getField() {
        return field;
    }
}
