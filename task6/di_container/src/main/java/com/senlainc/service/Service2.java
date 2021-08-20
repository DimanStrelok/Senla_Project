package com.senlainc.service;

import com.senlainc.di.Component;
import com.senlainc.di.InjectConstructor;

@Component
public class Service2 {
    private final Service1 service1;

    @InjectConstructor
    public Service2(Service1 service1) {
        this.service1 = service1;
    }

    public void foo() {
        System.out.println("Service2::foo");
        service1.foo();
    }
}
