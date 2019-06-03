package doctor.aysst.www.utils;

/**
 * 结果异常，会被 ExceptionHandler 捕捉并返回给前端
 *
 */
public class ResultException extends RuntimeException {

    private ResultCode resultCode;

    public ResultException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}