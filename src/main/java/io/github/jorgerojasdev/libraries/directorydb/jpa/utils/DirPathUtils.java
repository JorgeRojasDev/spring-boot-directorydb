package io.github.jorgerojasdev.libraries.directorydb.jpa.utils;

import io.github.jorgerojasdev.libraries.directorydb.jpa.commons.enumeration.filetype.FileType;

import java.io.File;
import java.lang.reflect.Field;

public class DirPathUtils {

    private final static String extension = ".dirdb";
    private final static String indexPrefix = "idx";

    public static <T extends Object> String resolvePath(Class<T> entityClazz, String path) {
        return String.format("%s%s%s", path, File.pathSeparator, entityClazz.getSimpleName());
    }

    public static <T extends Object> String resolveFilePath(Class<T> entityClazz, String path, FileType fileType) {
        return String.format("%s%s%s-%s%s", resolvePath(entityClazz, path), File.pathSeparator, fileType.getPrefix(), entityClazz.getSimpleName(), extension);
    }

    public static <T extends Object> String resolveIndexFilePath(Class<T> entityClazz, String path, Field field) {
        return String.format("%s%s%s-%s%s", resolvePath(entityClazz, path), File.pathSeparator, indexPrefix, field.getName().toLowerCase(), extension);
    }
}
