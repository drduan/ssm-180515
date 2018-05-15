package utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Bigdecimal4Serialize extends JsonSerializer<BigDecimal> {
    private DecimalFormat df = new DecimalFormat("0.0000");


    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        // TODO Auto-generated method stub
        gen.writeString(df.format(value));
    }
}
