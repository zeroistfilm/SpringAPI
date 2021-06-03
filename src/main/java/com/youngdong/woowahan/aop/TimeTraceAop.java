package com.youngdong.woowahan.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop{
    //*클래스명
    //(..) 파라미터
    // com.youngdong.woowahan.service..*(..) 서비스 하위 내용만 측정
    @Around("execution(* com.youngdong.woowahan..*(..))")
    public Object execute(ProceedingJoinPoint jointpoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("Start : " + jointpoint.toString());
        try{
            return jointpoint.proceed(); //프록시 방식임.
        }finally {
            long end = System.currentTimeMillis();
            long timeMS = end - start;
            System.out.println("End : "+ jointpoint.toString()+ " "+ timeMS +"ms");
        }
    }
}
