package com.product.genuine.modelmapper.converter;


import com.product.genuine.service.CryptoService;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * LongToStringConverter
 * Created by nuwan on 7/21/18.
 */
@Component
public class LongToStringConverter implements Converter<Long,String> {

    @Autowired
    private CryptoService cryptoService;

    private static final String ATTR_ID = "id";

    @Override
    public String convert(MappingContext<Long, String> context) {
        if (context.getSource() != null) {
            String property = context.getMapping().getLastDestinationProperty().getName();
            if (ATTR_ID.equalsIgnoreCase(property)) {
                return cryptoService.encryptEntityId(context.getSource());
            } else {
                return context.getSource().toString();
            }
        } else {
            return null;
        }
    }
}
