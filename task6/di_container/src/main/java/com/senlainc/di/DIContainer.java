package com.senlainc.di;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class DIContainer {
    private static final DIContainer INSTANCE = new DIContainer();
    private final Map<Class<?>, ComponentInfo> componentRegistry = new HashMap<>();
    private final Map<Class<?>, Class<?>> interfaceToComponent = new HashMap<>();
    private final Map<Class<?>, Object> classToInstance = new HashMap<>();
    private boolean initialized = false;

    public static void build(Class<?> mainClass) {
        String packageToScan = mainClass.getPackage().getName();
        INSTANCE.containerBuild(packageToScan);
    }

    public static <T> T get(Class<T> clazz) {
        return INSTANCE.getComponent(clazz);
    }

    private void containerBuild(String packageToScan) {
        if (!initialized) {
            registryComponents(packageToScan);
            createComponents();
            initialized = true;
        }
    }

    private void registryComponents(String packageToScan) {
        Reflections reflections = new Reflections(packageToScan);
        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);
        componentClasses.forEach(this::registryComponent);
    }

    private void createComponents() {
        componentRegistry.forEach((componentClass, componentInfo) -> {
            classToInstance.computeIfAbsent(componentClass, key -> createInstance(componentClass));
        });
    }

    @SuppressWarnings("unchecked")
    private <T> T getComponent(Class<T> componentClass) {
        if (!initialized) {
            throw new ContainerNotInitializedException();
        }
        if (interfaceToComponent.containsKey(componentClass)) {
            componentClass = (Class<T>) interfaceToComponent.get(componentClass);
        }
        if (!classToInstance.containsKey(componentClass)) {
            throw new ComponentNotRegisterException(componentClass);
        }
        return (T) classToInstance.get(componentClass);
    }

    private void registryComponent(Class<?> componentClass) {
        Constructor<?> constructor = findConstructor(componentClass);
        constructor.setAccessible(true);
        List<Class<?>> dependencies = Arrays.stream(constructor.getParameterTypes()).collect(Collectors.toList());
        Method postConstruct = findPostConstruct(componentClass);
        Class<?>[] interfaces = componentClass.getAnnotation(Component.class).interfaceClass();
        List<Class<?>> interfaceList = Arrays.stream(interfaces).collect(Collectors.toList());
        componentRegistry.put(componentClass, new ComponentInfo(constructor, dependencies, postConstruct, interfaceList));
        interfaceList.forEach(interfaceClass -> interfaceToComponent.put(interfaceClass, componentClass));
    }

    private Constructor<?> findConstructor(Class<?> componentClass) {
        Optional<Constructor<?>> constructor = Arrays.stream(componentClass.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findFirst();
        if (!constructor.isPresent()) {
            constructor = Arrays.stream(componentClass.getDeclaredConstructors())
                    .filter(c -> c.isAnnotationPresent(InjectConstructor.class))
                    .findFirst();
        }
        constructor.map(c -> {
            c.setAccessible(true);
            return c;
        });
        return constructor.orElseThrow(() -> new ComponentNotFoundConstructorException(componentClass));
    }

    private Method findPostConstruct(Class<?> componentClass) {
        return Arrays.stream(componentClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(PostConstruct.class))
                .findFirst()
                .map(method -> {
                    method.setAccessible(true);
                    return method;
                })
                .orElse(null);
    }

    private Object createInstance(Class<?> componentClass) {
        ComponentInfo componentInfo = componentRegistry.get(componentClass);
        Object[] dependencies = componentInfo.dependencies.stream()
                .map(dependencyClass -> {
                    if (interfaceToComponent.containsKey(dependencyClass)) {
                        return interfaceToComponent.get(dependencyClass);
                    }
                    return dependencyClass;
                })
                .map(dependencyClass -> classToInstance.computeIfAbsent(dependencyClass, key -> createInstance(dependencyClass)))
                .toArray();
        try {
            Object instance = componentInfo.constructor.newInstance(dependencies);
            if (componentInfo.postConstruct != null) {
                componentInfo.postConstruct.invoke(instance);
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
