package edu.gatech.cs6310.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;


@Aspect
@Component
public class LogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Pointcut("execution(public * edu.gatech.cs6310.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        LOGGER.info("url:{}", request.getRequestURL());
        LOGGER.info("method:{}", request.getMethod());
        LOGGER.info("ip:{}", request.getRemoteAddr());
        LOGGER.info("class_method:{}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("args:{}", Arrays.asList(joinPoint.getArgs()));

        String log = String.format("url: %s; method: %s; ip: %s; class_method: %s; args:%s; role: %s;",
                request.getRequestURL(), request.getMethod(), request.getRemoteAddr(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                Arrays.asList(joinPoint.getArgs()), authentication.getName());
        LOGGER.info(log);

        String sql = "INSERT INTO logs (info) VALUES (?)";
        int result = jdbcTemplate.update(sql, log);
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        LOGGER.info("response:{}", object.toString());
    }
}

