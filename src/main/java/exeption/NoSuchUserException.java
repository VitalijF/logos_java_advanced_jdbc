package exeption;

public class NoSuchUserException extends Exception{
  public NoSuchUserException(String message) {
    super(message);
  }
}
