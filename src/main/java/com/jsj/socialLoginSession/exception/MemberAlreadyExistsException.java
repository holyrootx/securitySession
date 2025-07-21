package com.jsj.socialLoginSession.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "이미 존재하는 유저에요.")
public class MemberAlreadyExistsException extends RuntimeException{
    public MemberAlreadyExistsException() {
        super("이미 존재하는 유저에요.");
    }

    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
