package exeption;

public class DuplicateBlogException extends Exception {

    public DuplicateBlogException(String message) {
        super(message);
    }
}
