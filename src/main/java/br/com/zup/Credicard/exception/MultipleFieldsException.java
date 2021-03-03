package br.com.zup.Credicard.exception;

import java.util.Map;

public class MultipleFieldsException extends RuntimeException {
    private final Map<String, Object> messages;

    public MultipleFieldsException(Map<String, Object> messages) {
        super("Validation error in multiple fields");

        this.messages = messages;
    }


    public Map<String, Object> getMessages() {
        return messages;
    }
}
