package org.example.phonebookexample.app;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Method;

/**
 * @author Nicholas Drone on 6/17/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class PerformanceMonitorInterceptorTests
{
    private PerformanceMonitorInterceptor performanceMonitorInterceptor;

    private MethodInvocation              methodInvocation;

    private Log                           log;

    @Before
    public void setUp()
    {
        performanceMonitorInterceptor = new PerformanceMonitorInterceptor();

        methodInvocation = Mockito.mock(MethodInvocation.class);
        log = Mockito.mock(Log.class);
        Mockito.reset(methodInvocation, log);
    }

    @Test
    public void performanceLogging() throws Throwable
    {
        // getting this current method and using it to pass for the test
        StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        Class<?> clazz = Class.forName(element.getClassName());
        Method method = clazz.getDeclaredMethod(element.getMethodName());

        Mockito.when(methodInvocation.getMethod()).thenReturn(method);
        Mockito.when(methodInvocation.proceed()).thenAnswer((invocation -> {
            Thread.sleep(1000);
            return new Object();
        }));

        performanceMonitorInterceptor.invokeUnderTrace(methodInvocation, log);

        Mockito.verify(methodInvocation, Mockito.times(1)).proceed();
        Mockito.verify(log, Mockito.times(2)).trace(Mockito.any(Object.class));

        // printing out the invocation on the log object
        Mockito.mockingDetails(log).getInvocations().forEach(System.out::println);
    }
}
