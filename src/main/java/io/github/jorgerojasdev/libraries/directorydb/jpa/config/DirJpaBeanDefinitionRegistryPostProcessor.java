package io.github.jorgerojasdev.libraries.directorydb.jpa.config;

import io.github.jorgerojasdev.libraries.directorydb.jpa.model.annotation.EnableDirJpaRepositories;
import io.github.jorgerojasdev.libraries.directorydb.jpa.processor.DirJpaProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class DirJpaBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    private static Logger logger = LoggerFactory.getLogger(DirJpaBeanDefinitionRegistryPostProcessor.class);

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        configurableListableBeanFactory.getBeansWithAnnotation(EnableDirJpaRepositories.class).forEach((name, beanObject) -> {
            try {
                for (String basePackage : configurableListableBeanFactory.findAnnotationOnBean(name, EnableDirJpaRepositories.class).basePackages()) {
                    DirJpaProcessor.processor().process(beanDefinitionRegistry, basePackage);
                }
            } catch (NoSuchBeanDefinitionException e) {
                logger.error("Error processing bean", e);
            }
        });
    }
}
