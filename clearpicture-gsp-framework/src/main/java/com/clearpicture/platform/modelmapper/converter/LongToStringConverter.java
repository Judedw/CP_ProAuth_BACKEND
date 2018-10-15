package com.clearpicture.platform.modelmapper.converter;


import com.clearpicture.platform.service.CryptoService;
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
    private static final String ATTR_VOTE_ID = "voteId";
    private static final String ATTR_PRODUCT_ID = "productId";
    private static final String ATTR_CLIENT_ID = "clientId";
    private static final String ATTR_SURVEY_ID = "surveyId";

    public LongToStringConverter() {}

    public String convert(MappingContext<Long, String> context)
    {
        if (context.getSource() != null) {
            String propertyName = context.getMapping().getLastDestinationProperty().getName();
            if (("id".equalsIgnoreCase(propertyName)) || ("voteId".equalsIgnoreCase(propertyName)) ||
                    ("productId".equalsIgnoreCase(propertyName)) || ("clientId".equalsIgnoreCase(propertyName)) ||
                    ("surveyId".equalsIgnoreCase(propertyName))) {
                return cryptoService.encryptEntityId((Long)context.getSource());
            }
            return ((Long)context.getSource()).toString();
        }

        return null;
    }
}
