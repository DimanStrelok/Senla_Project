package com.senlainc.service;

import com.senlainc.di.Component;
import com.senlainc.di.InjectConstructor;
import com.senlainc.di.PostConstruct;

@Component
public class Service3 {
    private final Service2 service2;

    @InjectConstructor
    public Service3(Service2 service2) {
        this.service2 = service2;
    }

    @PostConstruct
    void postConstruct() {
        System.out.println("Service3::postConstruct");
    }

    public void foo() {
        System.out.println("Service3::foo");
        service2.foo();
    }
}
