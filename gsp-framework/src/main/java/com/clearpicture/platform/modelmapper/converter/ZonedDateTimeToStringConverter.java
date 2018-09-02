package com.clearpicture.platform.modelmapper.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * 
 * @author Virajith K
 *
 */
@Component
public class ZonedDateTimeToStringConverter implements Converter<ZonedDateTime, String> {

	private static final String PARAM_START_DATE = "startDate";

	private static final String PARAM_END_DATE = "endDate";

	@Override
	public String convert(MappingContext<ZonedDateTime, String> context) {
		/*if (context.getSource() != null) {
			DateTimeFormatter formatter = null;
			if (context.getParent().getSource() instanceof Pool && (PARAM_START_DATE
					.equalsIgnoreCase(context.getMapping().getLastDestinationProperty().getName())
					|| PARAM_END_DATE.equalsIgnoreCase(context.getMapping().getLastDestinationProperty().getName()))) {
				formatter = DateTimeFormatter.ofPattern(DateTimePattern.STRICT_DATE_TIME.getPattern())
						.withResolverStyle(ResolverStyle.STRICT);
			} else {
				formatter = DateTimeFormatter.ofPattern(DateTimePattern.STRICT_DATE.getPattern())
						.withResolverStyle(ResolverStyle.STRICT);
			}

			return formatter.format(context.getSource());
		} else {
			return null;
		}*/
        return null;
	}

}
