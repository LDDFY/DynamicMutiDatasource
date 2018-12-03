package com.dynamic.mutidatasource.aspect;

import com.dynamic.mutidatasource.anno.TargetDataSource;
import com.dynamic.mutidatasource.config.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(-99)
@Component
public class DynamicDataSourceAspect {
    //改变数据源
    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        String id = targetDataSource.type().name();
        if (!DynamicDataSourceContextHolder.isContainsDataSource(id)) {
            //joinPoint.getSignature() ：获取连接点的方法签名对象
            log.error("数据源 " + id + " 不存在使用默认的数据源 -> " + joinPoint.getSignature());
        } else {
            log.debug("使用数据源：" + id);
            DynamicDataSourceContextHolder.setDataSourceType(id);
        }
    }

    @After("@annotation(targetDataSource)")
    public void clearDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        log.debug("清除数据源 " + targetDataSource.type().name() + " !");
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}
