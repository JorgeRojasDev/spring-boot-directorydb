package io.github.jorgerojasdev.libraries.directorydb.jpa.processor;

import io.github.jorgerojasdev.libraries.directorydb.jpa.handler.DirJpaRepositoriesHandler;
import io.github.jorgerojasdev.libraries.directorydb.jpa.service.abstraction.DirJpaRepository;
import io.github.jorgerojasdev.libraries.directorydb.jpa.service.implementation.DirJpaRepositoryImp;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.lang.reflect.Type;
import java.util.Set;

public class DirJpaProcessor implements ProxiedBeanProcessor {

    private static Logger logger = LoggerFactory.getLogger(DirJpaProcessor.class);

    private static DirJpaProcessor dirJpaProcessor = new DirJpaProcessor();

    private DirJpaProcessor() {
    }

    public static DirJpaProcessor processor() {
        return dirJpaProcessor;
    }

    @Override
    public void process(BeanDefinitionRegistry beanDefinitionRegistry, String basePackage) {
        Reflections ref = new Reflections(basePackage);
        Set<Class<? extends DirJpaRepository>> classes = ref.getSubTypesOf(DirJpaRepository.class);

        classes.forEach(clazz -> {
            Type[] types = clazz.getGenericInterfaces();
            if (clazz.isInterface() && types.length == 1) {
                Type[] classGenericTypes = getClassGenericTypes(types[0]);
                if (classGenericTypes.length > 0) {
                    try {
                        Object dirJpaRepositoryBean = getProxiedBeanFromDirJpaRepository(clazz, new DirJpaRepositoriesHandler<>(
                                new DirJpaRepositoryImp<>(clazz, classFromType(classGenericTypes[0]), classFromType(classGenericTypes[1])
                                )));
                        registerProxiedBean(clazz, dirJpaRepositoryBean, beanDefinitionRegistry);
                    } catch (Exception e) {
                        logger.error(String.format("Error Registering Bean From: %s", clazz.getCanonicalName()), e);
                    }
                }
            }
        });
    }
}
