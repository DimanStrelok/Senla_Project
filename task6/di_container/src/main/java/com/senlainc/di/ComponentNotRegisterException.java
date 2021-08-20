package com.senlainc.di;

public class ComponentNotRegisterException extends RuntimeException {
    public ComponentNotRegisterException(Class<?> clazz) {
        super("class " + clazz.getCanonicalName() + " not register in container");
    }
}
