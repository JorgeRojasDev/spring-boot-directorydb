package io.github.jorgerojasdev.libraries.directorydb.jpa.service.implementation;

import io.github.jorgerojasdev.libraries.directorydb.jpa.service.abstraction.DirJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class DirJpaRepositoryImp<T, ID> implements DirJpaRepository<T, ID> {

    private Logger logger;
    private Class<T> entityClazz;
    private Class<ID> idClazz;

    public DirJpaRepositoryImp(Class<?> clazz, Class<T> entityClazz, Class<ID> idClazz) {
        this.logger = LoggerFactory.getLogger(clazz);
        this.entityClazz = entityClazz;
        this.idClazz = idClazz;
    }

    @Override
    public void printTime() {
        logger.info(LocalDateTime.now().toString());
    }
}
