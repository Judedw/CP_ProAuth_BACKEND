package com.clearpicture.platform.modelmapper.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * 
 * @author Virajith K
 *
 */
@Component
public class ZonedDateTimeToLocalDateConverter implements Converter<ZonedDateTime, LocalDate> {

	@Override
	public LocalDate convert(MappingContext<ZonedDateTime, LocalDate> context) {
		if (context.getSource() != null) {
			return context.getSource().toLocalDate();
		} else {
			return null;
		}
	}

}