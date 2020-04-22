//package tian.pusen.err;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.NoHandlerFoundException;
//import tian.pusen.web.resp.RespHeader;
//import tian.pusen.web.resp.Response;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestControllerAdvice
//public class HttpExceptionHandler {
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public Response handle(HttpServletRequest request, NoHandlerFoundException e) {
//        return new Response(new RespHeader(ServiceCode.HTTP_404_ERROR));
//    }
//}
