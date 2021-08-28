package io.github.jorgerojasdev.libraries.directorydb.jpa.component.writer.abstraction;

import io.github.jorgerojasdev.libraries.directorydb.jpa.commons.enumeration.filetype.FileType;
import io.github.jorgerojasdev.libraries.directorydb.jpa.utils.AnnotationUtils;
import io.github.jorgerojasdev.libraries.directorydb.jpa.utils.DirPathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Indexed;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class DirDbAutoconfiguredWriter<T, ID> implements DirDbWriter<T, ID> {

    private Logger logger = LoggerFactory.getLogger(DirDbAutoconfiguredWriter.class);

    protected Map<Field, File> indexedFields = new HashMap<>();

    protected void setUpWriter(Class<T> entityClazz, String path) {
        this.createFolderIfNotExists(entityClazz, path);
        this.createFilesIfNotExists(entityClazz, path);
        this.createIndexFilesIfNotExists(entityClazz, path);
    }

    private void createFolderIfNotExists(Class<T> entityClazz, String path) {
        File file = new File(DirPathUtils.resolvePath(entityClazz, path));
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void createFilesIfNotExists(Class<T> entityClazz, String path) {
        for (FileType value : FileType.values()) {
            createFileIfNotExists(entityClazz, path, value);
        }
    }

    private void createFileIfNotExists(Class<T> entityClazz, String path, FileType fileType) {
        File file = new File(DirPathUtils.resolveFilePath(entityClazz, path, fileType));
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                logger.debug("Created file: {}, status: {}", file.getName(), created ? "OK" : "KO");
            } catch (IOException e) {
                logger.warn(String.format("Error creating file: %s", file.getName()), e);
            }
        }
    }

    private void createIndexFilesIfNotExists(Class<T> entityClazz, String path) {
        Map<Field, Indexed> indexedFieldsFromClass = AnnotationUtils.getMappedFieldsAnnotatedFromClass(entityClazz, Indexed.class);

        indexedFieldsFromClass.forEach((field, indexed) -> {
            File file = new File(DirPathUtils.resolveIndexFilePath(entityClazz, path, field));
            this.indexedFields.put(field, file);
            if (!file.exists()) {
                try {
                    boolean created = file.createNewFile();
                    logger.debug("Created file: {}, status: {}", file.getName(), created ? "OK" : "KO");
                } catch (IOException e) {
                    logger.warn(String.format("Error creating file: %s", file.getName()), e);
                }
            }
        });
    }

}
