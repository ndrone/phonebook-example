package org.example.phonebookexample.app;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;

/**
 * @author Nicholas Drone on 6/17/17.
 */
public class PerformanceMonitorInterceptor extends AbstractMonitoringInterceptor
{
    @Override
    protected Object invokeUnderTrace(MethodInvocation methodInvocation, Log log) throws Throwable
    {
        String name = createInvocationTraceName(methodInvocation);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.trace(String.format("Method %s execution start at %s", name, LocalDateTime.now()));

        try
        {
            return methodInvocation.proceed();
        }
        finally
        {
            stopWatch.stop();
            log.trace(String.format("Method %s execution took %dms (%s)", name,
                stopWatch.getTotalTimeMillis(), DurationFormatUtils
                    .formatDurationWords(stopWatch.getTotalTimeMillis(), true, true)));
        }
    }
}
