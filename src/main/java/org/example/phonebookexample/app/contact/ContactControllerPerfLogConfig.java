package org.example.phonebookexample.app.contact;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.phonebookexample.app.PerformanceMonitorInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * This configuration is to setup performance logging of the ContactController
 *
 * @author Nicholas Drone on 6/17/17.
 */
@Configuration
@EnableAspectJAutoProxy
@Aspect
public class ContactControllerPerfLogConfig
{
    @Bean
    public PerformanceMonitorInterceptor performanceMonitorInterceptor()
    {
        return new PerformanceMonitorInterceptor();
    }

    // Any public method on the ContactController
    @Pointcut("execution(public * org.example.phonebookexample.app.contact.ContactController.*(..))")
    public void contactControllerMonitor()
    {
    }

    @Bean
    public Advisor contactControllerMonitorAdvisor(
        PerformanceMonitorInterceptor performanceMonitorInterceptor)
    {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(
            "org.example.phonebookexample.app.contact.ContactControllerPerfLogConfig.contactControllerMonitor()");
        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor);
    }
}
