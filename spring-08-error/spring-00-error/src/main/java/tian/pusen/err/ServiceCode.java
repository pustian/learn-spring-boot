package tian.pusen.err;

public enum ServiceCode {
    OK("000000", "Success"),
    HTTP_404_ERROR("990404", "无法找到访问路径"),
    DATEBASE_CONNECT_ERROR("999901", "数据库连接异常"),
    ERROR("999999","System Error")
    ;
    private String code;
    private String message;
    /*private */
    ServiceCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ServiceCode getByCode(String code) {
        for(ServiceCode serviceCode: ServiceCode.values() ) {
            if(serviceCode.code.equals(code)) {
                return serviceCode;
            }
        }
        return null;
    }
}
