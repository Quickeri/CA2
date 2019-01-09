package exceptions;

public class ValidationErrorException extends Exception {
    public ValidationErrorException() {
    }
    public ValidationErrorException(String message) {
        super(message);
    }
}
