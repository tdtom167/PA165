package cz.muni.fi.pa165.currency;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Named
@Aspect
public class LoggingAspect {
    @Around("execution(public * *(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();

        Object result = joinPoint.proceed();

        long seconds = TimeUnit.MILLISECONDS.
                convert(System.nanoTime() - startTime,
                        TimeUnit.NANOSECONDS);

        System.err.println("Method finished: "
                + joinPoint.getSignature() + "Duration of method call in miliseconds" + seconds);

        return result;
    }
}
