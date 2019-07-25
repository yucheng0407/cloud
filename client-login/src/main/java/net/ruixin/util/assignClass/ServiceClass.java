package net.ruixin.util.assignClass;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceClass {
    String name();

    String id();

}
