package com.example.webbinderbug;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@RestControllerAdvice
public class PaginationParametersBinder {

    @InitBinder
    public void bindCustomAttributeNames(WebDataBinder binder, WebRequest request) {
        Object objectToInspect = binder.getTarget();
        if (objectToInspect != null) {
            Class<?> classToInspect = objectToInspect.getClass();
            Map<String, String> paramAndInternalNameMap = buildRequestParamMapping(classToInspect);

            if (!paramAndInternalNameMap.isEmpty()) {
                TreeMap<String, String[]> requestParameterNamesAndValues = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                requestParameterNamesAndValues.putAll(request.getParameterMap());
                bindParameters(binder, paramAndInternalNameMap, requestParameterNamesAndValues);
            }
        }
    }

    private void bindParameters(WebDataBinder binder, Map<String, String> paramAndInternalNameMap, TreeMap<String, String[]> requestParameterNamesAndValues) {
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        paramAndInternalNameMap.forEach((parameterName, internalName) -> {
            if (requestParameterNamesAndValues.containsKey(parameterName)) {
                final String[] propertyValue = Arrays.stream(requestParameterNamesAndValues.get(parameterName))
                        .filter(s -> !s.isEmpty()).toArray(String[]::new);
                if (propertyValue.length > 0) {
                    propertyValues.add(internalName, propertyValue);
                    binder.bind(propertyValues);
                }
            }
        });
    }

    private Map<String, String> buildRequestParamMapping(Class<?> classToInspect) {
        Map<String, String> paramAndInternalNameMap = new HashMap<>();
        Field[] fields = classToInspect.getDeclaredFields();
        for (Field field : fields) {
            ParamName paramNameAnnotation = field.getAnnotation(ParamName.class);
            if (paramNameAnnotation != null && !paramNameAnnotation.value().isEmpty()) {
                paramAndInternalNameMap.put(paramNameAnnotation.value(), field.getName());
            }
        }
        return paramAndInternalNameMap;
    }

}
