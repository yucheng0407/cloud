package net.ruixin.util.tools;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gai
 */
@Component
public class EHCacheTool {

    public static final String DEFAULT="defaultCache";

    @Autowired
    private CacheManager cacheManager;

    public Cache getCache(String conf){
        return cacheManager.getCache(conf);
    }

    /**
     * 设置缓存对象
     * @param conf
     * @param key
     * @param object
     */
    public void setCacheValue(String conf,String key, Object object){
        Cache cache = cacheManager.getCache(conf);
        Element element = new Element(key,object);
        cache.put(element);
    }
    /**
     * 设置缓存对象
     * @param key
     * @param object
     */
    public void setCacheValue(String key, Object object){
        setCacheValue(DEFAULT,key,object);
    }
    /**
     * 从缓存中取出对象
     * @param conf
     * @param key
     * @return
     */
    public Object getCacheValue(String conf,String key){
        Object object = null;
        Cache cache = cacheManager.getCache(conf);
        Element element=cache.get(key);
        if(element!=null && !element.equals("")){
            object = element.getObjectValue();
        }
        return object;
    }
    /**
     * 从缓存中取出对象
     * @param key
     * @return
     */
    public Object getCacheValue(String key){
        return getCacheValue(DEFAULT,key);
    }

    public Cache getCache() {
        return getCache(DEFAULT);
    }
}
