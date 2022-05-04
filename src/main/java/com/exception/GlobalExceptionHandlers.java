package com.exception;

/**
 * @author alexzhengzzz
 * @date 5/4/22 09:46
 */

import com.utils.cache.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlers {

    @ExceptionHandler(value = GeneralException.class)
    @ResponseBody
    public Response generalExceptionHandler(HttpServletRequest req, GeneralException e){
        log.error("business system error details：{}",e.getMessage());
        return Response.error(e);
    }

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Response exceptionHandler(HttpServletRequest req, NullPointerException e){
        log.error("null pointer",e);
        return Response.error(ErrorCode.NULL_POINTER);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response exceptionHandler(HttpServletRequest req, Exception e){
        log.error("unknown exception:",e);
        return Response.error(ErrorCode.UNKNOWN_ERROR);
    }
}