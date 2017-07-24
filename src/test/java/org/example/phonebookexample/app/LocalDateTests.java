package org.example.phonebookexample.app;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author Nicholas Drone on 7/23/17.
 */
public class LocalDateTests
{
    private LocalDate localDate;

    @Before
    public void setUp()
    {
        localDate = LocalDate.now();
    }

    @Test
    public void dateSerialization() throws IOException
    {
        JsonGenerator jsonGenerator = Mockito.mock(JsonGenerator.class);

        LocalDateSerializer serializer = new LocalDateSerializer();
        serializer.serialize(localDate, jsonGenerator, Mockito.mock(SerializerProvider.class));

        Mockito.verify(jsonGenerator, Mockito.times(1))
            .writeString(localDate.format(ApplicationUtils.FORMATTER));
    }

    @Test
    public void dateDeserializtion() throws IOException
    {
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        Mockito.when(jsonParser.getValueAsString())
            .thenReturn(localDate.format(ApplicationUtils.FORMATTER));

        LocalDateDeserializer deserializer = new LocalDateDeserializer();
        LocalDate testDate = deserializer.deserialize(jsonParser,
            Mockito.mock(DeserializationContext.class));

        Assert.assertThat("Dates should match", testDate.toString(),
            org.hamcrest.Matchers.equalTo(localDate.toString()));
    }
}
