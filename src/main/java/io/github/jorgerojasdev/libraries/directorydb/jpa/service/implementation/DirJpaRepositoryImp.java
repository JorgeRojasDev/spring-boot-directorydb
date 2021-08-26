package io.github.jorgerojasdev.libraries.directorydb.jpa.service.implementation;

import io.github.jorgerojasdev.libraries.directorydb.jpa.service.abstraction.DirJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirJpaRepositoryImp<T, K> implements DirJpaRepository<T, K> {

    private Logger log;
    private Class<T> entityClazz;
    private Class<K> idClazz;

    public DirJpaRepositoryImp(Class<?> clazz, Class<T> entityClazz, Class<K> idClazz) {
        this.log = LoggerFactory.getLogger(clazz);
        this.entityClazz = entityClazz;
        this.idClazz = idClazz;
    }

}
