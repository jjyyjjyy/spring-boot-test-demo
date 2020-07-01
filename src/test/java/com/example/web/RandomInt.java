package com.example.web;

import java.lang.annotation.*;

/**
 * @author jy
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RandomInt {
}
