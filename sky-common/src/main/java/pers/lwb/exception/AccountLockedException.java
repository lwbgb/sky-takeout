package pers.lwb.exception;

public class AccountLockedException extends BaseException{
    public AccountLockedException(String message) {
        super(message);
    }

    public AccountLockedException() {
        super();
    }
}
