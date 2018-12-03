package com.dynamic.mutidatasource.anno;

import com.dynamic.mutidatasource.config.DataType;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TargetDataSource {
    DataType type() default DataType.main;
}
