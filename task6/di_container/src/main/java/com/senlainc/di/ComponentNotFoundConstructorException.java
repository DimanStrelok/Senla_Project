package com.senlainc.di;

public class ComponentNotFoundConstructorException extends RuntimeException {
    public ComponentNotFoundConstructorException(Class<?> clazz) {
        super("class " + clazz.getCanonicalName() + " does not a have suitable constructor");
    }
}
