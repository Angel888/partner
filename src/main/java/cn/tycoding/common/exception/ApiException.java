package cn.tycoding.common.exception;

import org.springframework.http.HttpStatus;
// todo 仿照spring-boot-jwt写个exception吧
//public class ApiException extends RuntimeException {
//
//    private static final long serialVersionUID = 1L;  //todo 这个uid做什么？
//
//    private final String message;
//    private final HttpStatus httpStatus;
//
//    public CustomException(String message, HttpStatus httpStatus) {
//        this.message = message;
//        this.httpStatus = httpStatus;
//    }
//
//    @Override
//    public String getMessage() {
//        return message;
//    }
//
//    public HttpStatus getHttpStatus() {
//        return httpStatus;
//    }
//
//}