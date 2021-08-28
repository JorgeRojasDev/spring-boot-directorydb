package io.github.jorgerojasdev.libraries.directorydb.jpa.component.writer.abstraction;

import java.util.List;

public interface DirDbWriter<T, ID> {

    T save(T entity);

    List<T> saveAll(List<T> entities);

}
