package io.github.jorgerojasdev.libraries.directorydb.jpa.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AnnotationUtils {
    
    public static <T extends Annotation> Map<Field, T> getMappedFieldsAnnotatedFromClass(Class<?> entityClass, Class<T> annotationClazz) {
        Map<Field, T> map = new HashMap<>();
        for (Field field : entityClass.getFields()) {
            if (isAnnotationPresentOnField(field, annotationClazz)) {
                map.put(field, getAnnotationIfIsPresentOnField(field, annotationClazz));
            }
        }
        return map;
    }

    private static <T extends Annotation> T getAnnotationIfIsPresentOnField(Field field, Class<T> annotationClazz) {
        for (Annotation fieldAnnotation : field.getAnnotations()) {
            if (fieldAnnotation.getClass().equals(annotationClazz)) {
                return annotationClazz.cast(fieldAnnotation);
            }
        }
        return null;
    }

    private static boolean isAnnotationPresentOnField(Field field, Class<? extends Annotation> annotationClazz) {
        for (Annotation fieldAnnotation : field.getAnnotations()) {
            if (fieldAnnotation.getClass().equals(annotationClazz)) {
                return true;
            }
        }
        return false;
    }
}
