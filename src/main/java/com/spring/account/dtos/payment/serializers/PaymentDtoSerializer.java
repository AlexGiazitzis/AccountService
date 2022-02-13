package com.spring.account.dtos.payment.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.spring.account.dtos.payment.PaymentDto;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * Custom {@link com.fasterxml.jackson.databind.ser.std.StdSerializer} for {@link com.spring.account.dtos.payment.PaymentDto}.
 * Allows representing fields with a different type than they are stored inside the application.
 * Specifically, the salary is represented as a {@link java.lang.String} message which describes the salary as dollars and cents.
 *
 * @author Alex Giazitzis
 */
@JsonComponent
public class PaymentDtoSerializer extends StdSerializer<PaymentDto> {

    public PaymentDtoSerializer() {
        this(null);
    }

    public PaymentDtoSerializer(Class<PaymentDto> paymentDtoClass) {
        super(paymentDtoClass);
    }

    @Override
    public void serialize(final PaymentDto value, final JsonGenerator gen,
                          final SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        gen.writeStringField("name", value.getName());
        gen.writeStringField("lastname", value.getLastname());
        gen.writeStringField("period", value.getPeriod());
        gen.writeStringField("salary", value.getSalary() / 100 + " dollar(s) " + value.getSalary() % 100 + " cent(s)");
        gen.writeEndObject();

    }
}
