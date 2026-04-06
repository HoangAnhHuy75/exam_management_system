package edu.poly.exammanagement.exception;

public class AppException extends RuntimeException{
    private ErrorCode err;
    public AppException(ErrorCode err){
        super(err.getMessage());
        this.err=err;
    }

    public ErrorCode getErr() {
        return err;
    }
    public void setErr(ErrorCode err){
        this.err=err;
    }
}
