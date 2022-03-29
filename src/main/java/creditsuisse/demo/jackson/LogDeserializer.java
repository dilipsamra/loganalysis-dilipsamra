package creditsuisse.demo.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import creditsuisse.demo.log.model.ApplicationLogEntry;
import creditsuisse.demo.log.model.LogEntry;

import java.io.IOException;

public class LogDeserializer extends StdDeserializer {
    LogDeserializer(Class<?> vc) {
        super(vc);
    }
    @Override
    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        String id = node.get("id").asText();
        String state = node.get("state").asText();
        long length = node.get("timestamp").asLong();

        boolean applicationLogEntry = node.has("type");
        if (!applicationLogEntry) {
            return new LogEntry(id, state, length);
        }

        String type = node.get("type").asText();
        String host = node.get("host").asText();
        return new ApplicationLogEntry(id, state, length, type, host);
    }
}
