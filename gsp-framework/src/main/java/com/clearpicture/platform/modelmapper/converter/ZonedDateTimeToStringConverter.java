package com.clearpicture.platform.modelmapper.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class ZonedDateTimeToStringConverter
		implements Converter<ZonedDateTime, String>
{
	private static final String PARAM_START_DATE = "startDate";
	private static final String PARAM_END_DATE = "endDate";

	public ZonedDateTimeToStringConverter() {}

	public String convert(MappingContext<ZonedDateTime, String> context)
	{
		return null;
	}
}