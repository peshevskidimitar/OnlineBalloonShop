package mk.ukim.finki.wp.lab.model.exceptions;

public class InvalidRequiredArgumentsException extends RuntimeException {
    public InvalidRequiredArgumentsException() {
        super("All fields are required.");
    }
}
