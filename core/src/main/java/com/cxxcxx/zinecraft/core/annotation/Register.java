package com.cxxcxx.zinecraft.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)          // 作用在字段上
@Retention(RetentionPolicy.SOURCE)  // 编译期注解
public @interface Register {
    String mod();
    String en_us() default "Empty";
    String zh_cn() default "空";
}
