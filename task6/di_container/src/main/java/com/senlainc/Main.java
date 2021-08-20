package com.senlainc;

import com.senlainc.di.DIContainer;
import com.senlainc.service.*;

public class Main {
    public static void main(String[] args) {
        DIContainer.build(Main.class);
        Service5 service5 = DIContainer.get(Service5.class);
        service5.buz();
    }
}
