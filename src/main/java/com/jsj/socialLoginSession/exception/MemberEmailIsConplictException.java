package com.jsj.socialLoginSession.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "이메일이 .")
public class MemberEmailIsConplictException extends RuntimeException{

    public MemberEmailIsConplictException() {
        super();
    }

    public MemberEmailIsConplictException(String message) {
        super(message);
    }

}
