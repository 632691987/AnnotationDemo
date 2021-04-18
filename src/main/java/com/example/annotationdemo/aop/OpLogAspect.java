package com.example.annotationdemo.aop;

import com.google.common.base.CaseFormat;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class OpLogAspect
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OpLogAspect.class);

    @Autowired
    HttpServletRequest request;

    @Around("@annotation(com.example.annotationdemo.aop.OpLog)")
    public Object log(ProceedingJoinPoint pjp) throws Exception
    {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        OpLog opLog = method.getAnnotation(OpLog.class);
        Object response = null;
        try
        {
            response = pjp.proceed();
        }
        catch (Throwable throwable)
        {
            throw new Exception(throwable);
        }

        if (opLog.opItemIdExpression() != null && !opLog.opItemIdExpression().isEmpty())
        {
            SpelExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(opLog.opItemIdExpression());
            EvaluationContext context = new StandardEvaluationContext();

            Object[] args = pjp.getArgs();
            LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);

            if (parameterNames != null)
            {
                for (int i = 0; i < parameterNames.length; i++)
                {
                    context.setVariable(parameterNames[i], args[i]);
                }
            }

            if (response != null)
            {
                context.setVariable(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, response.getClass().getSimpleName()), response);
            }

            String itemId = String.valueOf(expression.getValue(context));
            handle(opLog.opType(), opLog.opItem(), itemId);
        }
        return response;
    }


    private void handle(OpType opType, String opItem, String opItemId)
    {
        LOGGER.info("opType = " + opType.name() + ",opItem = " + opItem + ",opItemId = " + opItemId);
    }
}
