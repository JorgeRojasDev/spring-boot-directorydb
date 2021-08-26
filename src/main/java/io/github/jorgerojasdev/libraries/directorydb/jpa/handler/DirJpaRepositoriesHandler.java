package io.github.jorgerojasdev.libraries.directorydb.jpa.handler;

import io.github.jorgerojasdev.libraries.directorydb.jpa.service.implementation.DirJpaRepositoryImp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DirJpaRepositoriesHandler<T, K> implements InvocationHandler {

    private DirJpaRepositoryImp<T, K> dirJpaRepositoryImp;

    public DirJpaRepositoriesHandler(DirJpaRepositoryImp<T, K> dirJpaRepositoryImp) {
        this.dirJpaRepositoryImp = dirJpaRepositoryImp;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(this.dirJpaRepositoryImp, args);
    }
}
