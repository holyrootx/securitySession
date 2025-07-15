package com.jsj.socialLoginSession.util;

import com.jsj.socialLoginSession.util.StatusCode;
public enum GlobalStatus implements StatusCode{

    // ✅ 성공 상태 코드
    OK("OK", "요청이 정상적으로 처리되었습니다"),
    CREATED("CR", "요청이 성공적으로 처리되어 리소스가 생성되었습니다"),
    ACCEPTED("AC", "요청이 접수되었으며 비동기적으로 처리됩니다"),
    NO_CONTENT("NC", "요청이 성공했으나 반환할 데이터가 없습니다"),
    SUCCESS_WITH_DATA("SWD", "데이터를 정상적으로 반환하였습니다"),

    VALIDATION_FAIL("VF", "입력값 검증 실패"),
    TYPE_MISMATCH("TM", "입력 타입 불일치"),

    ENTITY_NOT_FOUND("ENF", "요청한 데이터를 찾을 수 없습니다"),
    DATA_INTEGRITY_VIOLATION("DIV", "데이터 제약 조건 위반"),

    ACCESS_DENIED("AD", "권한이 없습니다"),
    AUTHENTICATION_FAIL("AF", "인증 실패"),
    JWT_VALIDATION_FAIL("JVF", "JWT 검증 실패"),

    NULL_POINTER("NP", "Null 참조 예외"),

    ILLEGAL_STATE("IS", "잘못된 상태 예외"),

    INPUT_OUPUT_ERROR("IOE", "입출력 오류"),

    S3_FILE_NOT_FOUND("SFNF", "스토리지 파일 미발견"),

    UNKNOWN_ERROR("NE", "알 수 없는 오류"),
    STATIC_RESOURCE_NOT_FOUND("SRNF","정적 리소스 탐색 실패"),
    SERVER_ERROR("SE","서비스 서버 이상"),
    FIREBASE_ERROR("FE","인증 서버 오류"),
    FIREBASE_ROLE_SAVE_FAIL("FRSE","인증 서버 인증 정보 저장 실패"),
    USERNAME_VALIDATION_FAILED("UVF","사용할 수 없는 아이디"),
    EMAIL_VALIDATION_FAILED("EVF", "사용할 수 없는 이메일"),
    PHONENUMBER_VALIDATION_FAILED("PVF", "사용할 수 없는 전화번호");



    private String code;
    private String message;

    GlobalStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }
    @Override
    public String getMessage() {
        return this.message;
    }
}
