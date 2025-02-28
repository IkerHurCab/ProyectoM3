package exceptions;

public class UserInvalidDateOfBirth extends Exception {
    public UserInvalidDateOfBirth(String message) {
        super(message);
    }
}