package com.spring.account.entities.payment.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Converter class to map a field of {@link java.time.YearMonth} type to a {@link java.lang.String}, and vice versa,
 * in order to properly persist and query entities containing such fields.
 *
 * @author Alex Giazitzis
 */
@Converter
public class YearMonthConverter implements AttributeConverter<YearMonth, String> {

    @Override
    public String convertToDatabaseColumn(final YearMonth attribute) {

        if (attribute == null) {
            return null;
        }

        return attribute.format(DateTimeFormatter.ofPattern("MM-yyyy"));

    }

    @Override
    public YearMonth convertToEntityAttribute(final String dbData) {

        if (dbData == null) {
            return null;
        }

        return YearMonth.parse(dbData, DateTimeFormatter.ofPattern("MM-yyyy"));

    }
}
