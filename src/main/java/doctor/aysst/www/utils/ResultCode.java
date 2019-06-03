package doctor.aysst.www.utils;


public enum  ResultCode {

    SUCCESS(0, "success"),
    WARN(-1, "fail");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}