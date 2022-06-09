package io.github.vanessaeich.quarkussocial.rest.dto;

/**
 * @author Vanessa Eich
 * @created 08/06/2022
 */
public class FieldError {

    private String field;
    private String message;

    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
