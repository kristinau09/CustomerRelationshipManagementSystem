package com.example.crms.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Transactional
@Component 
public class PerformanceTimingAdvice {

	//@Pointcut("execution ( * com.example.crms.services.*.*(..) )")
	public void allServiceMethod() {}
	
	//@Around("allServiceMethod()")
	public Object recordTiming(ProceedingJoinPoint jp) throws Throwable {

		double timeNow = System.currentTimeMillis();
		
		try {
			Object returnValue = jp.proceed();
			return returnValue;
			
		}finally {
			double timeAfter = System.currentTimeMillis();
			double timeTaken = timeAfter - timeNow;

			System.out.println("Time taken for the method: " + jp.getSignature().getName()+ 
					" from the class " + jp.getTarget().getClass().getSimpleName() + " took " + timeTaken + " milliseconds");
		}
	}

}
