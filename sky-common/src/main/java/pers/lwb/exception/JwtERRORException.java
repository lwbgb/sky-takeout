package pers.lwb.exception;

import io.jsonwebtoken.JwtException;

public class JwtERRORException extends BaseException {

    public JwtERRORException(String message) {
        super(message);
    }
}
