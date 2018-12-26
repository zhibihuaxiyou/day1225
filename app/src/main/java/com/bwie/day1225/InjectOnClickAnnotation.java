package com.bwie.day1225;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : 张腾
 * @date : 2018/12/25.
 * desc :
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectOnClickAnnotation {
    int value();// 控件id
    String onClick() default "";// 点击事件名称
}
