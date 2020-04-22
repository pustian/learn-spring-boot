package tian.pusen.err;

import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tian.pusen.web.resp.RespHeader;
import tian.pusen.web.resp.Response;

import javax.validation.ValidationException;

/**
 * 全局异常处理
 * 规范：流程跳转尽量避免使用抛 BizException 来做控制。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public Response handleBizException(BusinessException ex) {
        Response result = new Response();
        result.setRespHeader(new RespHeader(ex.getServiceCode() ) );
        return result;
    }
//
//    /**
//     * 参数校验错误
//     */
//    @ExceptionHandler(ValidationException.class)
//    public Result handleValidationException(ValidationException ex) {
//        return Result.renderErr(CodeEnum.REQUEST_ERR).withRemark(ex.getCause().getMessage());
//    }
//
//    /**
//     * 字段校验不通过异常
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        StringJoiner sj = new StringJoiner(";");
//        ex.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
//        return Result.renderErr(CodeEnum.REQUEST_ERR).withRemark(sj.toString());
//    }
//
//    /**
//     * Controller参数绑定错误
//     */
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
//        return Result.renderErr(CodeEnum.REQUEST_ERR).withRemark(ex.getMessage());
//    }
//
//    /**
//     * 处理方法不支持异常
//     */
//    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
//    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
//        return Result.renderErr(CodeEnum.METHOD_NOT_ALLOWED);
//    }

    /**
     * 其他未知异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Response handleException(Exception ex) {
        Response result = new Response();
        RespHeader respHeader = new RespHeader(ServiceCode.ERROR);
        result.setRespHeader(respHeader);
        return result;
    }
}
