package deveducate.library.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceLoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {
    }

    @Pointcut("@annotation(deveducate.library.aspect.Benchmark)")
    public void benchmarkPointcut() {
    }

    @Around("benchmarkPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        log.info("[+] method [{}] took [{}ms]", joinPoint.getSignature().getName(), elapsed);
        return proceed;
    }
}
