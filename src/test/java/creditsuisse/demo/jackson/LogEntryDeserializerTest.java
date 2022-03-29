package creditsuisse.demo.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import creditsuisse.demo.log.model.ApplicationLogEntry;
import creditsuisse.demo.log.model.LogEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogEntryDeserializerTest {
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private JsonParser jsonParser;
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private JsonNode jsonNode;
  private LogDeserializer deserializer;

  @Before
  public void setUp() throws IOException {
    deserializer = new LogDeserializer(LogEntry.class);

    when(jsonParser.getCodec().readTree(jsonParser)).thenReturn(jsonNode);
    when(jsonNode.get("timestamp").asLong()).thenReturn(1L);
    when(jsonNode.get("state").asText()).thenReturn("state");
    when(jsonNode.get("id").asText()).thenReturn("id");
  }

  @Test
  public void deserializeLogEntry() throws IOException {
    when(jsonNode.has("type")).thenReturn(false);

    LogEntry logEntry = (LogEntry) deserializer.deserialize(jsonParser, null);

    assertFalse(logEntry instanceof ApplicationLogEntry);
    assertEquals(1L, logEntry.getTimestamp());
    assertEquals("state", logEntry.getState());
    assertEquals("id", logEntry.getId());

  }

  @Test
  public void deserializeApplicationLogEntry() throws IOException {
    when(jsonNode.get("host").asText()).thenReturn("host");
    when(jsonNode.has("type")).thenReturn(true);
    when(jsonNode.get("type").asText()).thenReturn("type");

    LogEntry logEntry = (LogEntry) deserializer.deserialize(jsonParser, null);

    assertTrue(logEntry instanceof ApplicationLogEntry);
    ApplicationLogEntry applicationLogEntry = (ApplicationLogEntry) logEntry;
    assertEquals("id", applicationLogEntry.getId());
    assertEquals("type", applicationLogEntry.getType());
    assertEquals("host", applicationLogEntry.getHost());
    assertEquals("state", applicationLogEntry.getState());
    assertEquals(1L, applicationLogEntry.getTimestamp());

  }
}