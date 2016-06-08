package com.clouway.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class DependencyManager {
    public static Map<Class,Object> dependencies=new HashMap<Class, Object>();

    private DependencyManager() {
    }

    public static <T> void addDependencies(Class<T> tClass, T impl){
        dependencies.put(tClass,impl);
    }

    public static <T> T getDependency(Class<? extends T> impl) {
        return (T) dependencies.get(impl);
    }
}
