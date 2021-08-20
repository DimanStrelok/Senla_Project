package com.senlainc.service;

import com.senlainc.di.Component;
import com.senlainc.di.PostConstruct;

@Component
public class Service1 {
    @PostConstruct
    void postConstruct() {
        System.out.println("Service1::postConstruct");
    }

    public void foo() {
        System.out.println("Service1::foo");
    }
}
