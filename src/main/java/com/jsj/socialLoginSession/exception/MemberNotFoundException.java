package com.jsj.socialLoginSession.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "유저를 찾을 수 없어요.")
public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException() {
        super("유저를 찾을 수 없어요.");
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
