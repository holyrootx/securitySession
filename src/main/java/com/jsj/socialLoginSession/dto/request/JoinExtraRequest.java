package com.jsj.socialLoginSession.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Valid
@Data
public class JoinExtraRequest {

    @NotBlank(message = "이메일은 필수 입력 값이에요")
    @Pattern(
            // 가장 많이 쓰는 실무용 이메일 정규식 예시
            // _, ., %, +, - 만 허용
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "이메일 형식을 지켜서 작성해주세요."
    )
    private String email;

    @NotBlank(message = "이름은 필수 입력 값이에요")
    @Size(min = 2,max = 8,message = "2~10자로 작성해주세요")
    @Pattern(
            regexp = "^[가-힣]{2,10}$",
            message = "이름은 한글로 2~10자로 작성해주세요."
    )
    private String name;

    @NotBlank(message = "닉네임은 필수 입력 값이에요")
    @Pattern(
            regexp = "^[a-zA-Z가-힣0-9]{2,12}$",
            message = "닉네임은 한글/영어/숫자로 2~12자로 작성해주세요."
    )
    private String nickname;

    @NotBlank(message = "전화번호는 필수 입력 값이에요")
    @Pattern(
            regexp = "^\\d{3}-\\d{4}-\\d{4}$",
            message = "전화번호는 010-1234-5678 형식으로 입력해주세요."
    )
    private String phoneNumber;
}
