package com.example.annotationdemo.aop;

import com.example.annotationdemo.dto.BaseResponse;
import com.example.annotationdemo.validator.BeanValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

@Aspect
@Component
public class FacadeAspect
{

    private static final Logger LOGGER = LoggerFactory.getLogger(FacadeAspect.class);

    @Around("@annotation(com.example.annotationdemo.aop.Facade)")
    public Object facade(ProceedingJoinPoint pjp) throws Exception
    {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();
        Class returnType = ((MethodSignature) pjp.getSignature()).getMethod().getReturnType();

        for (Object parameter : args)
        {
            try
            {
                BeanValidator.validateObject(parameter);
            }
            catch (ValidationException e)
            {
                return getFailedResponse(returnType, e);
            }
        }

        try
        {
            Object response = pjp.proceed();
            return response;
        }
        catch (Throwable throwable)
        {
            return getFailedResponse(returnType, throwable);
        }
    }


    private Object getFailedResponse(Class returnType, Throwable throwable) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if (returnType.getDeclaredConstructor().newInstance() instanceof BaseResponse)
        {
            BaseResponse response = (BaseResponse) returnType.getDeclaredConstructor().newInstance();
            response.setSuccess(false);
            response.setResponseMessage(throwable.toString());
            return response;
        }

        LOGGER.error("failed to getFailedResponse , returnType (" + returnType + ") is not instanceof BaseResponse");
        return null;
    }

}
