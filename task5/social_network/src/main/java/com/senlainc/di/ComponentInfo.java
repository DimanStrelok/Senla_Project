package com.senlainc.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

class ComponentInfo {
    Constructor<?> constructor;
    List<Class<?>> dependencies;
    Method postConstruct;
    List<Class<?>> interfaces;

    ComponentInfo(Constructor<?> constructor, List<Class<?>> dependencies, Method postConstruct, List<Class<?>> interfaces) {
        this.constructor = constructor;
        this.dependencies = dependencies;
        this.postConstruct = postConstruct;
        this.interfaces = interfaces;
    }
}
