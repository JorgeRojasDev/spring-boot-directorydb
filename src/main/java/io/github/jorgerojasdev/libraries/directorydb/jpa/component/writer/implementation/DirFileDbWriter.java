package io.github.jorgerojasdev.libraries.directorydb.jpa.component.writer.implementation;

import io.github.jorgerojasdev.libraries.directorydb.jpa.component.writer.abstraction.DirDbAutoconfiguredWriter;

import java.util.List;

public class DirFileDbWriter<T, ID> extends DirDbAutoconfiguredWriter<T, ID> {

    private Class<T> entityClazz;

    private Class<ID> idClazz;

    public DirFileDbWriter(Class<T> entityClazz, Class<ID> idClazz, String path) {
        this.entityClazz = entityClazz;
        this.idClazz = idClazz;
        super.setUpWriter(entityClazz, path);
    }

    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        return null;
    }

}
