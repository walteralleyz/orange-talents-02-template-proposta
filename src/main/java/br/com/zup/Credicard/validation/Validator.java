package br.com.zup.Credicard.validation;

import br.com.zup.Credicard.exception.MultipleFieldsException;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private String field;
    private Object body;
    private final List<String> messages;
    private final Map<String, Object> errors;

    public Validator() {
        this.messages = new ArrayList<>();
        this.errors = new HashMap<>();
    }

    public Validator field(String field) {
        this.field = field;
        return this;
    }

    public Validator body(Object body) {
        this.body = body;
        return this;
    }

    public Validator notNull() {
        if(body == null)
            addFormattedMessage("%s cannot be null");

        return this;
    }

    public Validator notBlank() {
        if(body == null || body.toString().trim().equals(""))
            addFormattedMessage("%s cannot be blank");

        return this;
    }

    public Validator email() {
        if(body == null) {
            addFormattedMessage("%s is not a valid email");
            return this;
        }

        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(body.toString());

        if(!matcher.find())
            addFormattedMessage("%s is not a valid email");

        return this;
    }

    public Validator cnpj() {
        if(body == null) {
            addFormattedMessage("%s is not a valid CNPJ");
            return this;
        }

        Pattern pattern = Pattern.compile("([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[/]?[0-9]{4}[-]?[0-9]{2})");
        Matcher matcher = pattern.matcher(body.toString());

        if(!matcher.find())
            addFormattedMessage("%s is not a valid CNPJ");

        return this;
    }

    public Validator cpf() {
        if(body == null) {
            addFormattedMessage("%s is not a valid CPF");
            return this;
        }

        Pattern pattern = Pattern.compile("([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}-[0-9]{2})|([0-9]{11})");
        Matcher matcher = pattern.matcher(body.toString());

        if(!matcher.find())
            addFormattedMessage("%s is not a valid CPF");

        return this;
    }

    public Validator CPFOrCNPJ() {
        if(body == null) {
            addFormattedMessage("%s is not a valid CNPJ or a valid CPF");
            return this;
        }

        Pattern cnpjPattern = Pattern.compile("([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[/]?[0-9]{4}[-]?[0-9]{2})");
        Pattern cpfPattern = Pattern.compile("([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}-[0-9]{2})|([0-9]{11})");

        Matcher cnpjMatcher = cnpjPattern.matcher(body.toString());
        Matcher cpfMatcher = cpfPattern.matcher(body.toString());

        if(!cnpjMatcher.find() && !cpfMatcher.find())
            addFormattedMessage("%s is not a valid CNPJ or a valid CPF");

        return this;
    }

    public Validator min(BigDecimal value) {
        if(body == null || new BigDecimal(body.toString()).doubleValue() < value.doubleValue())
            addFormattedMessage("%s is invalid. Minimum required is " + value);

        return this;
    }

    public Validator min(Integer value) {
        if(body == null || Integer.parseInt(body.toString()) < value)
            addFormattedMessage("%s is invalid. Minimum required is " + value);

        return this;
    }

    public Validator min(Double value) {
        if(body == null || Double.parseDouble(body.toString()) < value)
            addFormattedMessage("%s is invalid. Minimum required is " + value);

        return this;
    }

    public Validator max(BigDecimal value) {
        if(body == null || new BigDecimal(body.toString()).doubleValue() > value.doubleValue())
            addFormattedMessage("%s is invalid. Maximum required is " + value);

        return this;
    }

    public Validator max(Integer value) {
        if(body == null || Integer.parseInt(body.toString()) > value)
            addFormattedMessage("%s is invalid. Maximum required is " + value);

        return this;
    }

    public Validator max(Double value) {
        if(body == null || Double.parseDouble(body.toString()) > value)
            addFormattedMessage("%s is invalid. Maximum required is " + value);

        return this;
    }

    public boolean validate() {
        if(messages.isEmpty()) {
            return true;
        }

        errors.putIfAbsent(field, List.copyOf(messages));
        messages.clear();

        return false;
    }

    public void compile() {
        if(!errors.isEmpty())
            throw new MultipleFieldsException(errors);
    }

    private void addFormattedMessage(String message) {
        messages.add(String.format(message, field));
    }
}
