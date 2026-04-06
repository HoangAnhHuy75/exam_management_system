package edu.poly.exammanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T>{
    @Builder.Default
    private int code=1000;
    private String message;
    private T result;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
