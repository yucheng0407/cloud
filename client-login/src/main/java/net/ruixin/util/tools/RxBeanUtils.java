package net.ruixin.util.tools;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.Session;
import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * bean工具类
 */
public abstract class RxBeanUtils extends org.springframework.beans.BeanUtils {

    private static Object getValue(String propertyName, Object o) throws InvocationTargetException, IllegalAccessException {
        PropertyDescriptor sourcePd = getPropertyDescriptor(o.getClass(), propertyName);
        if (sourcePd != null && sourcePd.getReadMethod() != null) {
            Method readMethod = sourcePd.getReadMethod();
            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                readMethod.setAccessible(true);
            }
            return readMethod.invoke(o);
        } else {
            return null;
        }
    }

    private static void setValue(String propertyName, Object o, Object value) throws InvocationTargetException, IllegalAccessException {
        PropertyDescriptor sourcePd = getPropertyDescriptor(o.getClass(), propertyName);
        if (sourcePd != null && sourcePd.getWriteMethod() != null) {
            Method writeMethod = sourcePd.getWriteMethod();
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                writeMethod.setAccessible(true);
            }
            writeMethod.invoke(o, value);
        }
    }
}


