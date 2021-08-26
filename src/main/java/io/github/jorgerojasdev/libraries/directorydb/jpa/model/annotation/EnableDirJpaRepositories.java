package io.github.jorgerojasdev.libraries.directorydb.jpa.model.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableDirJpaRepositories {
    String[] basePackages();
}
