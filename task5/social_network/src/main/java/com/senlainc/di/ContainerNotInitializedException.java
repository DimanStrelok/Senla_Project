package com.senlainc.di;

public class ContainerNotInitializedException extends RuntimeException {
    public ContainerNotInitializedException() {
        super("before call get() necessary call build()");
    }
}
