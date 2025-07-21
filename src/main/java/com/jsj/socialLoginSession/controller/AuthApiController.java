package com.jsj.socialLoginSession.controller;

import com.jsj.socialLoginSession.dto.ErrorDetail;
import com.jsj.socialLoginSession.dto.request.JoinExtraRequest;
import com.jsj.socialLoginSession.dto.response.ApiResponse;
import com.jsj.socialLoginSession.util.GlobalStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.jsj.socialLoginSession.service.AuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/auth/joinExtra")
    public ResponseEntity<ApiResponse> joinExtra(@RequestBody JoinExtraRequest request){
        authService.joinMemberBySocial(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(GlobalStatus.OK));
    }

    @PostMapping("/auth/validate/extra")
    public ResponseEntity<ApiResponse> validateExtra(@RequestBody @Valid JoinExtraRequest request,
                                                     BindingResult bindingResult){

        List<ErrorDetail> errors = new ArrayList<ErrorDetail>();

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            errors = fieldErrors.stream()
                    .map(fe -> new ErrorDetail(fe.getField(), fe.getDefaultMessage()))
                    .collect(Collectors.toList());

            // ⇒ 여러 개의 FieldError를 한꺼번에 가져옴 :contentReference[oaicite:2]{index=2}
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.responseError(GlobalStatus.VALIDATION_FAIL, errors));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(GlobalStatus.OK));
    }





}
