package com.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局exceptionHandler
 */
@Component
@Slf4j
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    private List<ExceptionHandlerProcessor> exceptionHandlerProcessors;


    @PostConstruct
    void _init(){
        _initExceptionHandlerProcessors();
    }

    /**
     * 初始化 exceptionHandlerProcessors ，processor的添加顺序就是processor的添加顺序，
     * 注意若需要添加其他的processor,需要将processor添加到LastExceptionHandlerProcessor之前的任意顺序
     */
    private void _initExceptionHandlerProcessors() {
        exceptionHandlerProcessors = new ArrayList<>();
        exceptionHandlerProcessors.add(new GeneralExceptionHandlerProcessor());
//        exceptionHandlerProcessors.add(new InterceptorExceptionHandlerProcessor());
        exceptionHandlerProcessors.add(new IllegalArgumentExceptionHandlerProcessor());
        exceptionHandlerProcessors.add(new ControllerExceptionHandlerProcessor());
        exceptionHandlerProcessors.add(new DataAccessExceptionHandlerProcessor());
        exceptionHandlerProcessors.add(new LastExceptionHandlerProcessor());
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        ExceptionHandlerProcessorChain exceptionChain = new ExceptionHandlerProcessorChain(exceptionHandlerProcessors);
        view.setAttributesMap(exceptionChain.process(ex));
        view.setPrettyPrint(true);
        mv.setView(view);
        log.error("resolve exception",ex);
        return mv;
    }

    /**
     * 定一个ExceptionHandlerProcessorChain
     */
    public class ExceptionHandlerProcessorChain{
        private List<ExceptionHandlerProcessor> exceptionHandlerProcessors = new ArrayList<>();

        private int index = 0;//index的值表示processor的执行顺序

        public ExceptionHandlerProcessorChain(List<ExceptionHandlerProcessor> processors) {
            this.exceptionHandlerProcessors = processors;
        }

        public Map<String, Object> process(Exception ex) {
            // 拿到链表中的handler
            if (index >= exceptionHandlerProcessors.size()) {
                log.warn("exception handler processor not found {},so return null",ex);
                return null;
            }
            //根据index的值，取到对应的processor
            ExceptionHandlerProcessor exceptionHandlerProcessor = exceptionHandlerProcessors.get(index++);
            //执行processor处理exception的方法
            return exceptionHandlerProcessor.handleException(ex, this);
        }
    }

    /**
     * 定义一个 ExceptionHandlerProcessor 接口
     */
    public interface ExceptionHandlerProcessor{
        Map<String, Object> handleException(Exception ex, ExceptionHandlerProcessorChain chain);
    }

    /**
     * 定义一个ExceptionHandlerProcessor抽象类
     */
    abstract class AbstractExceptionHandlerProcessor implements ExceptionHandlerProcessor {

        @Override
        public Map<String, Object> handleException(Exception ex, ExceptionHandlerProcessorChain chain) {
            if (belongCurrentProcessor(ex)) {
                return handleCurrentException(ex);
            }
            return chain.process(ex);
        }

        abstract boolean belongCurrentProcessor(Exception ex);

        abstract Map<String, Object> handleCurrentException(Exception ex);

    }

    /**
     * 定义一个处理 GeneralException的processor;
     */
    class GeneralExceptionHandlerProcessor extends  AbstractExceptionHandlerProcessor{

        @Override
        boolean belongCurrentProcessor(Exception ex) {
            return ex instanceof GeneralException;
        }

        @Override
        Map<String, Object> handleCurrentException(Exception ex) {
            Map<String, Object> attributes = new HashMap<>(2);
            GeneralException exception = (GeneralException)ex;
            attributes.put("code", exception.getCode().code);
            attributes.put("message", exception.getMessage());
            return attributes;
        }
    }

//
//    /**
//     * 定义一个处理  InterceptorExceptionHandler 的processor;
//     */
//    class InterceptorExceptionHandlerProcessor extends   AbstractExceptionHandlerProcessor{
//
//        @Override
//        boolean belongCurrentProcessor(Exception ex) {
//            return ex instanceof InterceptorException;
//        }
//
//        @Override
//        Map<String, Object> handleCurrentException(Exception ex) {
//            Map<String, Object> attributes = new HashMap<>();
//            InterceptorException exception = (InterceptorException)ex;
//            attributes.put("code", "500");
//            attributes.put("message", exception.getErrorMessage());
//            return attributes;
//        }
//    }

    /**
     * 定义一个IllegalArgumentExceptionHandlerProcessor 处理IllegalArgumentException
     */
    class IllegalArgumentExceptionHandlerProcessor extends  AbstractExceptionHandlerProcessor{

        @Override
        boolean belongCurrentProcessor(Exception ex) {
            return ex instanceof  IllegalArgumentException;
        }

        @Override
        Map<String, Object> handleCurrentException(Exception ex) {
            Map<String, Object> attributes = new HashMap<>();
            IllegalArgumentException exception = (IllegalArgumentException)ex;
            attributes.put("code", "500");
            attributes.put("message", exception.getMessage());
            return attributes;
        }
    }


    /**
     * 定义一个SQLExceptionHandlerProcessor 处理IllegalArgumentException
     */
    class DataAccessExceptionHandlerProcessor extends  AbstractExceptionHandlerProcessor{

        @Override
        boolean belongCurrentProcessor(Exception ex) {
            return ex instanceof DataAccessException;
        }

        @Override
        Map<String, Object> handleCurrentException(Exception ex) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("code", "500");
            attributes.put("message", ex.getMessage());
            return attributes;
        }
    }


    /**
     * 定义一个处理ControllerExceptionProcessor 用于处理来之ControllerExceptionHandler的异常
     */
    class ControllerExceptionHandlerProcessor extends  AbstractExceptionHandlerProcessor{

        @Override
        boolean belongCurrentProcessor(Exception ex) {
            return _isFromControllerException(ex);
        }


        @Override
        Map<String, Object> handleCurrentException(Exception ex) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("code", "500");
            attributes.put("message", ex.getMessage());
            return attributes;
        }
    }

    /**
     * 判断异常是否来之ControllerExceptionHandler
     * @param ex
     * @return
     */
    private boolean _isFromControllerException(Exception ex) {

             return ex instanceof HttpRequestMethodNotSupportedException || ex instanceof HttpMediaTypeNotSupportedException ||
                    ex instanceof HttpMediaTypeNotAcceptableException || ex instanceof MissingPathVariableException ||
                    ex instanceof MissingServletRequestParameterException || ex instanceof ServletRequestBindingException ||
                    ex instanceof ConversionNotSupportedException || ex instanceof TypeMismatchException ||
                    ex instanceof HttpMessageNotReadableException || ex instanceof HttpMessageNotWritableException ||
                    ex instanceof MethodArgumentNotValidException || ex instanceof MissingServletRequestPartException ||
                    ex instanceof BindException || ex instanceof NoHandlerFoundException ||
                    ex instanceof AsyncRequestTimeoutException ;
    }

    /**
     * 定义一个最后会执行的ExceptionHandlerProcessor
     */
    class LastExceptionHandlerProcessor extends   AbstractExceptionHandlerProcessor{

        @Override
        boolean belongCurrentProcessor(Exception ex) {
            return true;
        }
        @Override
        Map<String, Object> handleCurrentException(Exception ex) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("code", "500");
            attributes.put("message", "unknown error");

            return attributes;
        }
    }

}
