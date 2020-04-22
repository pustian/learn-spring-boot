package tian.pusen.err;

public class BusinessException extends RuntimeException {
    private String code;
    private String message;
    private ServiceCode serviceCode;

    public BusinessException(String code) {
        this.code = code;
        serviceCode = ServiceCode.getByCode(code);
        message = serviceCode != null? serviceCode.getMessage():"Unknow code";
    }
    public BusinessException(ServiceCode serviceCode) {
        this.code = serviceCode.getCode();
        this.message = serviceCode.getMessage();
        this.serviceCode = serviceCode;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }
}
