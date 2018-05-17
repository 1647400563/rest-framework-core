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
    /**
     * 模块名称
     */
    String module() default "";

    /**
     * 操作内容
     */
    String operation() default "";

    /**
     * 扩展内容
     */
    String extension() default "";
}