package com.common.feature.modelmapper;

import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ModelMapper
 * Created by nuwan on 7/21/18.
 */
@Component
public class ModelMapper {

    @SuppressWarnings("rawtypes")
    @Autowired
    private List<Converter> converters;

    public <T>  T map(Object source,Class<T> targetType) {
        org.modelmapper.ModelMapper mapper = createModelMapperInstance();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        return mapper.map(source,targetType);
    }

    public <T, S> List<T> map(List<S> sourceList, Class<T> targetType) {
        org.modelmapper.ModelMapper mapper = createModelMapperInstance();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        List<T> result = new ArrayList<T>();

        if (!CollectionUtils.isEmpty(sourceList)) {
            for (S item : sourceList) {
                result.add(mapper.map(item, targetType));
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private org.modelmapper.ModelMapper createModelMapperInstance() {
        org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();
        converters.forEach(converter -> mapper.addConverter(converter));
        return mapper;
    }
}
