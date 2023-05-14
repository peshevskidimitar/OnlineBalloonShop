package mk.ukim.finki.wp.lab.model.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super(String.format("User with id %s already exists.", username));
    }
}
