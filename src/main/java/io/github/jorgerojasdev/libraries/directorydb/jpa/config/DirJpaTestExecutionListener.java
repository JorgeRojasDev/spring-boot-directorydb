package io.github.jorgerojasdev.libraries.directorydb.jpa.config;

import io.github.jorgerojasdev.libraries.directorydb.jpa.handler.DirJpaRepositoriesHandler;
import io.github.jorgerojasdev.libraries.directorydb.jpa.service.abstraction.DirJpaRepository;
import io.github.jorgerojasdev.libraries.directorydb.jpa.service.implementation.DirJpaRepositoryImp;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public class DirJpaTestExecutionListener implements TestExecutionListener, Ordered {

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {
        injectDependencies(testContext);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    protected void injectDependencies(TestContext testContext) throws ClassNotFoundException {
        Object bean = testContext.getTestInstance();
        Class<?> clazz = testContext.getTestClass();
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) testContext.getApplicationContext().getAutowireCapableBeanFactory();

        for (Field declaredField : clazz.getDeclaredFields()) {
            Type[] interfaces = declaredField.getType().getGenericInterfaces();
            for (Type ifc : interfaces) {
                Class<?> fieldClass = Class.forName(declaredField.getType().getTypeName());
                ParameterizedType ifcType = ((ParameterizedType) ifc);
                Class<?> ifcClass = Class.forName(ifcType.getRawType().getTypeName());
                if (ifcClass.equals(DirJpaRepository.class) && ifcClass.isInterface()) {
                    Type[] arguments = ifcType.getActualTypeArguments();
                    assert arguments.length > 0 : "Argument of Notifier must be declared";
                    Object proxyFluxNotifier = Proxy.newProxyInstance(fieldClass.getClassLoader(),
                            new Class[]{fieldClass},
                            new DirJpaRepositoriesHandler<>(
                                    new DirJpaRepositoryImp<>(ifcClass, Class.forName(arguments[0].getTypeName()), Class.forName(arguments[1].getTypeName()))
                            ));
                    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(proxyFluxNotifier.getClass());
                    beanDefinitionBuilder.addConstructorArgValue(Proxy.getInvocationHandler(proxyFluxNotifier));
                    beanFactory.registerBeanDefinition(ifc.getTypeName(), beanDefinitionBuilder.getBeanDefinition());
                }
            }
        }
        beanFactory.autowireBeanProperties(bean, 0, false);
    }
}
