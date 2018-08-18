package com.clearpicture.platform.modelmapper.converter;


import com.clearpicture.platform.service.CryptoService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * StringToLongConverter
 * Created by nuwan on 7/21/18.
 */
@Component
public class StringToLongConverter implements Converter<String,Long> {

    @Autowired
    private CryptoService cryptoService;

    private static final String ATTR_ID = "id";


    @Override
    public Long convert(MappingContext<String, Long> mappingContext) {

        if (StringUtils.isNotBlank(mappingContext.getSource())) {
            // To Process String List
            if(mappingContext.getMapping() == null) {
                return cryptoService.decryptEntityId(mappingContext.getSource());
            }

            String propertyName = mappingContext.getMapping().getLastDestinationProperty().getName();
            if (ATTR_ID.equalsIgnoreCase(propertyName)) {
                return cryptoService.decryptEntityId(mappingContext.getSource());
            } else {
                return (mappingContext.getSource() == null ? null : Long.valueOf(mappingContext.getSource()));
            }
        } else {
            return null;
        }

    }
}
