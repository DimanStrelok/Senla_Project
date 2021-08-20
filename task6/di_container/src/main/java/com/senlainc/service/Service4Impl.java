package com.senlainc.service;

import com.senlainc.di.Component;
import com.senlainc.di.InjectConstructor;
import com.senlainc.di.PostConstruct;

@Component(interfaceClass = Service4.class)
public class Service4Impl implements Service4 {
    private final Service2 service2;

    @InjectConstructor
    public Service4Impl(Service2 service2) {
        this.service2 = service2;
    }

    @PostConstruct
    void postConstruct() {
        System.out.println("Service4Impl::postConstruct");
    }

    @Override
    public void bar() {
        System.out.println("Service4Impl::bar");
        service2.foo();
    }
}
