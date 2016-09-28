package com.github.blovemaple.hura.xmlutil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by GongXunyao on 15/12/28.<br>
 * from: https://my.oschina.net/jarvan4dev/blog/649555
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface XStreamCDATA {
}