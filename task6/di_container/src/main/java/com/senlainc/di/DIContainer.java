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
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Component.class);
        annotated.forEach(this::registryByClass);
    }

    private void createComponents() {
        componentRegistry.forEach((clazz, componentInfo) -> {
            if (!classToInstance.containsKey(clazz)) {
                classToInstance.put(clazz, createInstance(clazz));
            }
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

    private void registryByClass(Class<?> clazz) {
        Constructor<?> constructor = findConstructor(clazz);
        constructor.setAccessible(true);
        List<Class<?>> dependencies = Arrays.stream(constructor.getParameterTypes()).collect(Collectors.toList());
        Method postConstruct = findPostConstruct(clazz);
        Class<?>[] interfaces = clazz.getAnnotation(Component.class).interfaceClass();
        List<Class<?>> interfacesList = Arrays.stream(interfaces).collect(Collectors.toList());
        componentRegistry.put(clazz, new ComponentInfo(constructor, dependencies, postConstruct, interfacesList));
        interfacesList.forEach(interfaceClass -> interfaceToComponent.put(interfaceClass, clazz));
    }

    private Constructor<?> findConstructor(Class<?> clazz) {
        Optional<Constructor<?>> constructor = Arrays.stream(clazz.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findFirst();
        if (!constructor.isPresent()) {
            constructor = Arrays.stream(clazz.getDeclaredConstructors())
                    .filter(c -> c.isAnnotationPresent(InjectConstructor.class))
                    .findFirst();
        }
        constructor.map(c -> {
            c.setAccessible(true);
            return c;
        });
        return constructor.orElseThrow(() -> new ComponentNotFoundConstructorException(clazz));
    }

    private Method findPostConstruct(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(PostConstruct.class))
                .findFirst()
                .map(method -> {
                    method.setAccessible(true);
                    return method;
                })
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    private <T> T createInstance(Class<T> componentClass) {
        ComponentInfo componentInfo = componentRegistry.get(componentClass);
        Object[] dependencies = componentInfo.dependencies.stream()
                .map(dependencyClass -> {
                    if (interfaceToComponent.containsKey(dependencyClass)) {
                        dependencyClass = interfaceToComponent.get(dependencyClass);
                    }
                    if (!classToInstance.containsKey(dependencyClass)) {
                        classToInstance.put(dependencyClass, createInstance(dependencyClass));
                    }
                    return classToInstance.get(dependencyClass);
                })
                .toArray();
        try {
            T instance = (T) componentInfo.constructor.newInstance(dependencies);
            if (componentInfo.postConstruct != null) {
                componentInfo.postConstruct.invoke(instance);
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
