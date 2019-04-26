package com.rexen.rest.util;

import com.google.common.cache.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CacheUtils {

    static Logger logger = Logger.getLogger(CacheUtils.class);

    public static final String special_prefix = "[__";

    public static final String special_suffix = "__]";

    private volatile static Cache<String, Object> cache = null;

    public static Cache<String, Object> instance(int initialCapacity, int concurrencyLevel, int expireDuration){
        if(cache == null) {
            cache = CacheBuilder.newBuilder()
                    .initialCapacity(initialCapacity)
                    .concurrencyLevel(concurrencyLevel)
                    .expireAfterWrite(expireDuration, TimeUnit.SECONDS)
                    .build();
        }
        return cache;
    }

    public static Cache<String, Object> instance(int expireDuration){
        if(cache == null) {
            cache = CacheBuilder.newBuilder()
                    .initialCapacity(20)
                    .concurrencyLevel(10)
                    .expireAfterWrite(expireDuration, TimeUnit.SECONDS)
                    .build();
        }
        return cache;
    }

    public static Cache<String, Object> instance(){
        if(cache == null) {
            cache = CacheBuilder.newBuilder()
                    .initialCapacity(20)
                    .concurrencyLevel(10)
                    .expireAfterWrite(20, TimeUnit.SECONDS)
                    .build();
        }
        return cache;
    }

    public static Cache<String, Object> instance(CacheBuilder<String, Object> cacheBuilder){
        if(cache == null){
            cache = cacheBuilder.build();
        }
        return cache;
    }

    public static String wrapKey(String key, String prefix, String suffix){
        return prefix.concat(key).concat(suffix);
    }

    public static String wrapKey(String key){
        return wrapKey(key, special_prefix, special_suffix);
    }

    public static String unwrapKey(String wrappedKey, String prefix, String suffix){
        if(wrappedKey.length() <= prefix.length() + suffix.length()){
            return StringUtils.EMPTY;
        }
        return wrappedKey.substring(prefix.length(), wrappedKey.length() - suffix.length());
    }

    public static String unwrapKey(String wrappedKey){
        return unwrapKey(wrappedKey, special_prefix, special_prefix);
    }

    public static void main(String[] args) throws ExecutionException {
        String x = wrapKey("ibm");
        String y = unwrapKey(x);
        System.out.println(y);
        String y1 = unwrapKey("abc");
        System.out.println(y1);
        CacheUtils.instance().put(x, "KKK");
        System.out.println(CacheUtils.instance().getIfPresent(x));
        System.out.println(CacheUtils.instance().get(x, () -> "true"));

        try {
            Thread.sleep(20*1000);
        } catch (InterruptedException e) {
            logger.debug(e);
        }
        System.out.println(CacheUtils.instance().get(x, () -> "false"));
        try {
            Thread.sleep(20*1000);
        } catch (InterruptedException e) {
            logger.debug(e);
        }
        System.out.println(CacheUtils.instance().get(x, () -> "true"));
        System.out.println(CacheUtils.instance().getIfPresent(x));
    }
}
