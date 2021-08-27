package io.github.jorgerojasdev.libraries.directorydb.jpa.processor;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public interface ProxiedBeanProcessor {

    void process(BeanDefinitionRegistry beanDefinitionRegistry, String basePackage);

    default Type[] getClassGenericTypes(Type type) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return parameterizedType.getActualTypeArguments();
    }

    default void registerProxiedBean(Class clazz, Object bean, BeanDefinitionRegistry beanDefinitionRegistry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(bean.getClass());
        beanDefinitionBuilder.addConstructorArgValue(Proxy.getInvocationHandler(bean));
        beanDefinitionRegistry.registerBeanDefinition(clazz.getName(), beanDefinitionBuilder.getBeanDefinition());
    }

    default <T extends InvocationHandler> Object getProxiedBeanFromDirJpaRepository(Class<?> clazz, T handler) throws ClassNotFoundException {
        return Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                handler);
    }

    default Class<?> classFromType(Type type) throws ClassNotFoundException {
        return Class.forName(type.getTypeName());
    }
}
