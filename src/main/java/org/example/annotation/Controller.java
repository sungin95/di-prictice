package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // class, interface, enum 에 붙을 수 있다는 걸 명시
@Retention(RetentionPolicy.RUNTIME) // 유지기간
public @interface Controller {

}
