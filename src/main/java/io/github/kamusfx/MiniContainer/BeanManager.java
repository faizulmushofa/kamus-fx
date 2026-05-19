package io.github.kamusfx.MiniContainer;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.github.kamusfx.MiniContainer.Auto;
import io.github.kamusfx.MiniContainer.Run;

public class BeanManager {

    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final Set<Class<?>> creating = new HashSet<>();

    public BeanManager() {
        scan("io.github.kamusfx");
        runApplication();
    }

    public <T> void register(Class<T> type,T instance) {
        beans.put(type, instance);
    }

    public <T> T getBean(Class<T> type) {
        if (type.isInterface()) {
            
            for (Map.Entry<Class<?>, Object> entry : beans.entrySet()) {
                if (type.isAssignableFrom(entry.getKey())) {
                    return type.cast(entry.getValue());
                }
            }
            
            Class<?> implClass = findImplementation(type);
            if (implClass != null) {
                Object implInstance = getBean(implClass);
                beans.put(type, implInstance);
                return type.cast(implInstance);
            }
        }

        Object bean = beans.get(type);

        if (bean != null) {
            return type.cast(bean);
        }

        T created = createBean(type);

        beans.put(type, created);

        return created;
    }

    private Class<?> findImplementation(Class<?> interfaceType) {
        org.reflections.util.ConfigurationBuilder config = new org.reflections.util.ConfigurationBuilder()
                .setUrls(BeanManager.class.getProtectionDomain().getCodeSource().getLocation())
                .setScanners(org.reflections.scanners.Scanners.TypesAnnotated);

        Reflections reflections = new Reflections(config);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Auto.class);

        for (Class<?> clazz : classes) {
            if (interfaceType.isAssignableFrom(clazz) && !clazz.isInterface()) {
                return clazz;
            }
        }
        return null;
    }

    private <T> T createBean(Class<T> type){
        if (!type.isAnnotationPresent(Auto.class)) {
            throw new RuntimeException(
                    type.getName() + " is not annotated with @Auto"
            );
        }

        if (creating.contains(type)) {
            throw new RuntimeException(
                    "Circular dependency detected: " + type.getName()
            );
        }

        creating.add(type);
        try {

            Constructor<?> constructor = type.getDeclaredConstructors()[0];
            constructor.setAccessible(true);

            Class<?>[] parameterTypes = constructor.getParameterTypes();

            Object[] dependencies = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = getBean(parameterTypes[i]);
            }

            return (T) constructor.newInstance(dependencies);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            creating.remove(type);
        }
    }

    public void scan(String packageName) {
        org.reflections.util.ConfigurationBuilder config = new org.reflections.util.ConfigurationBuilder()
                .setUrls(BeanManager.class.getProtectionDomain().getCodeSource().getLocation())
                .setScanners(org.reflections.scanners.Scanners.TypesAnnotated);

        Reflections reflections = new Reflections(config);

        Set<Class<?>> classes =
                reflections.getTypesAnnotatedWith(Auto.class);

        for (Class<?> clazz : classes) {
            System.out.println("FOUND: " + clazz.getName());
            getBean(clazz);
        }
    }

    private void runApplication() {

        for (Class<?> type : beans.keySet()) {

            if (type.isAnnotationPresent(Run.class)) {

                Object bean = getBean(type);

                if (bean instanceof RunnableApp app) {
                    app.run();
                }

                return;
            }
        }

        throw new RuntimeException("No @Run bean found");
    }

}
