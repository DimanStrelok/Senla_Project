package com.senlainc.service;

import com.senlainc.di.Component;
import com.senlainc.di.InjectConstructor;
import com.senlainc.di.PostConstruct;

@Component(interfaceClass = Service5.class)
public class Service5Impl implements Service5 {
    private final Service2 service2;
    private final Service4 service4;

    @InjectConstructor
    public Service5Impl(Service2 service2, Service4 service4) {
        this.service2 = service2;
        this.service4 = service4;
    }

    @PostConstruct
    void postConstruct() {
        System.out.println("Service5Impl::postConstruct");
    }

    @Override
    public void buz() {
        System.out.println("Service5Impl::buz");
        service2.foo();
        service4.bar();
    }
}
