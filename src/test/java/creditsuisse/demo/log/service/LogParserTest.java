package creditsuisse.demo.log.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import creditsuisse.demo.log.model.LogEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogParserTest {
  @Mock
  private ObjectMapper objectMapper;
  private LogParser logParser;

  @Before
  public void setUp() throws Exception {
    logParser = new LogParser(objectMapper);
  }

  @Test
  public void parseLogLineSuccessfully() throws IOException {
    String logLine = "successful";
    LogEntry toReturn = new LogEntry("id", "state", 1L);
    when(objectMapper.readValue(logLine, LogEntry.class)).thenReturn(toReturn);

    LogEntry logEntry = logParser.parseLine(logLine);
    assertSame(toReturn, logEntry);
  }

  @Test
  public void parseLogLineError() throws IOException {
    String logLine = "error";
    when(objectMapper.readValue(logLine, LogEntry.class)).thenThrow(new IOException("Error reading file"));
    LogEntry logEntry = logParser.parseLine(logLine);
    assertNull(logEntry);
  }
}