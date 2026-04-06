package edu.poly.exammanagement.exception;

import edu.poly.exammanagement.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse> handResponseEntity(RuntimeException e){
        ApiResponse apiRespone = new ApiResponse<>();
        apiRespone.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> appexception(AppException e){
        ApiResponse apiRespone = new ApiResponse();
        ErrorCode err = e.getErr();
        apiRespone.setCode(err.getCode());
        apiRespone.setMessage(err.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handValidation(MethodArgumentNotValidException e){
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode err = ErrorCode.valueOf(enumKey);
        ApiResponse apiRespone = new ApiResponse();
        apiRespone.setCode(err.getCode());
        apiRespone.setMessage(err.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }
}
