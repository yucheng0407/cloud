package net.ruixin.util.assignClass;
import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceOperate {
    String name();
    ServiceOperateType type() default ServiceOperateType.Query;
}
