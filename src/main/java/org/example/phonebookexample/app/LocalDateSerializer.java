package org.example.phonebookexample.app;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author Nicholas Drone on 7/23/17.
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate>
{
    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException, JsonProcessingException
    {
        jsonGenerator.writeString(localDate.format(ApplicationUtils.FORMATTER));
    }
}
