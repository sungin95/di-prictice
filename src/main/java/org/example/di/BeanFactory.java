package org.example.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BeanFactory {
    private final Set<Class<?>> preInstantiatedClazz;
    private Map<Class<?>, Object> beans = new HashMap<>();

    public BeanFactory(Set<Class<?>> preInstantiatedClazz) {
        this.preInstantiatedClazz = preInstantiatedClazz;
        initialize();
    }

    private void initialize() {
        for (Class<?> clazz : preInstantiatedClazz) {
            Object instance = createInstance(clazz);
            beans.put(clazz, instance);
        }
    }

    // UserController
    // UserService
    private Object createInstance(Class<?> clazz) {
        // 생성자
        Constructor<?> constructor = findConstructor(clazz);

        // 파라미터(인자)
        List<Object> parameters = new ArrayList<>();
        for (Class<?> typeClass : constructor.getParameterTypes()) {
            // UserService
            parameters.add(getParameterByClass(typeClass));
        }

        // 인스턴스 생성
        try {
            return constructor.newInstance(parameters.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Constructor<?> findConstructor(Class<?> clazz) {
        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        if (Objects.nonNull(constructor)) {
            return constructor;
        }

        return clazz.getConstructors()[0];
    }

    private Object getParameterByClass(Class<?> typeClass) {
        Object instanceBean = getBean(typeClass);

        if (Objects.nonNull(instanceBean)) {
            return instanceBean;
        }

        return createInstance(typeClass);
    }


    public <T> T getBean(Class<T> requiredType) { // T 뭔지는 몰라도 제네릭 타입이라고 함.
        return (T) beans.get(requiredType); // (T)는 있어도 되고 없어도 되는거 같다. T와 ?은 다르다.
    }
}
