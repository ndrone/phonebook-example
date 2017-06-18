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
 * This configuration is to setup performance logging of the ContactRepository
 *
 * @author Nicholas Drone on 6/17/17.
 */
@Configuration
@EnableAspectJAutoProxy
@Aspect
public class ContactRepositoryPerfLogConfig
{
    // Any method on the ContactRepository
    @Pointcut("execution(* org.example.phonebookexample.app.contact.ContactRepository.*(..))")
    public void contactRepositoryMonitor()
    {
    }

    @Bean
    public Advisor contactRepositoryMonitorAdvisor(
        PerformanceMonitorInterceptor performanceMonitorInterceptor)
    {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(
            "org.example.phonebookexample.app.contact.ContactRepositoryPerfLogConfig.contactRepositoryMonitor()");
        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor);
    }
}
