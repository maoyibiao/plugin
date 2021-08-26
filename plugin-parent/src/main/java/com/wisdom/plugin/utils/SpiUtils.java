package com.wisdom.plugin.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class SpiUtils {
    public static <T> T load(Class<T> clazz){
        List<T> beans = new ArrayList<>();
        Iterator<T> it = ServiceLoader.load(clazz).iterator();
        while (it.hasNext()){
            beans.add(it.next());
        }
        if(beans.isEmpty()){
            return null;
        }
        if(beans.size() != 1){
            throw new RuntimeException("SPI实例不唯一");
        }
        return beans.get(0);
    }

    public static <T> T loadBean(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        List<T> beans = new ArrayList<>();
        Iterator<T> it = ServiceLoader.load(clazz).iterator();
        while (it.hasNext()){
            beans.add(it.next());
        }
        if(beans.isEmpty()){
            return clazz.newInstance();
        }
        if(beans.size() != 1){
            throw new RuntimeException("SPI实例不唯一");
        }
        return beans.get(0);
    }
}
