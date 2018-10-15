package com.clearpicture.platform.modelmapper.converter;

import com.clearpicture.platform.enums.DateTimePattern;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

@Component
public class StringToZonedDateTimeConverter
		implements Converter<String, ZonedDateTime>
{
	public StringToZonedDateTimeConverter() {}

	public ZonedDateTime convert(MappingContext<String, ZonedDateTime> context)
	{
		if (context.getSource() == null) {
			return null;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimePattern.STRICT_DATE_TIME.getPattern()).withZone(ZoneId.systemDefault()).withResolverStyle(ResolverStyle.STRICT);
		return ZonedDateTime.parse((CharSequence)context.getSource(), formatter);
	}
}