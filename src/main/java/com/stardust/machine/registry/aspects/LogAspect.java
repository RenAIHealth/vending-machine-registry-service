package com.stardust.machine.registry.aspects;

import com.stardust.machine.registry.exceptions.InvalidSNException;
import com.stardust.machine.registry.exceptions.InvalidStatusException;
import com.stardust.machine.registry.exceptions.InvalidTokenException;
import com.stardust.machine.registry.exceptions.RecordNotFoundException;
import com.stardust.machine.registry.models.Message;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
@Order(1)
public class LogAspect {

    private static Logger logger = Logger.getLogger(LogAspect.class);

    private static final int SC_OK_MESSAGE = 210;

    public LogAspect() {

    }

    @Pointcut("execution(* com.stardust.machine.registry.controllers.*Controller.*(..))")
    public void controllerRequest() {
    }

    @Pointcut("execution(* com.stardust.machine.registry.services.*Service.*(..))")
    public void executeService() {
    }

    @Pointcut("execution(* com.stardust.machine.registry.dao.repositories.*Repository.*(..))")
    public void accessRepository() {
    }

    @Before("executeService() || accessRepository()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        StringBuilder params = new StringBuilder();
        Object[] arguments = joinPoint.getArgs();

        for (Object p : arguments) {
            params.append(p.toString()).append(",");
        }

        logger.info(joinPoint.getTarget().getClass().getName() + "::" + joinPoint.getSignature().getName() + "(" + params.toString() + ")");
    }

    @Around("controllerRequest()")
    public Object aroundControllerRequest(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        HttpServletResponse response = sra.getResponse();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();

        logger.info("Requesting with method: [" + method + "], uri: [" + uri + "], params: [" + queryString + "]");
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (RecordNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (InvalidSNException | InvalidTokenException | InvalidStatusException e) {
            Message message = new Message();
            message.setType(Message.MessageType.ERROR);
            message.setContent(e.getMessage());
            result = message;
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        logger.info("Requesting completed");
        return result;
    }

    @AfterReturning(pointcut = "controllerRequest()", argNames = "joinPoint,retVal", returning = "retVal")
    public void doAfterReturning(JoinPoint joinPoint, Object retVal) throws Throwable {

    }

    @AfterThrowing(pointcut = "executeService() || accessRepository()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
        logger.error(e.getMessage());
    }
}