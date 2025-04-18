package pers.lwb.exception;

public class AccountNotFoundException extends BaseException{
    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
