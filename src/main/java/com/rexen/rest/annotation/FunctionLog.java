package com.rexen.rest.annotation;

import java.lang.annotation.*;

/**
 * 功能日志注解
 *
 * @author: GavinHacker
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FunctionLog {

    String module() default "";

    String operation() default "";

    String extension() default "";
}