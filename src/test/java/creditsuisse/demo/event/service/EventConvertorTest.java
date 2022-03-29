package creditsuisse.demo.event.service;

import creditsuisse.demo.event.model.Event;
import creditsuisse.demo.event.model.Type;
import creditsuisse.demo.log.model.ApplicationLogEntry;
import creditsuisse.demo.log.model.LogEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EventConvertorTest {
  private ConcurrentMap<String, LogEntry> map;
  private EventConvertor converter;

  @Before
  public void setUp() {
    map = new ConcurrentHashMap<>();
    converter = new EventConvertor(map);
  }

  @After
  public void tearDown() {
    map.clear();
  }

  @Test
  public void convertFirstLogEntry() {
    LogEntry entry = new LogEntry("id", "state", 1L);
    Event event = converter.convert(entry);

    assertNull(event);
    assertSame(entry, map.get("id"));
  }

  @Test
  public void convertSecondLogEntryWithSameId() {
    LogEntry entry1 = new LogEntry("id", "state", 1L);
    LogEntry entry2 = new LogEntry("id", "state", 2L);
    Event event1 = converter.convert(entry1);
    Event event2 = converter.convert(entry2);

    assertNull(event1);
    assertNotNull(event2);
    assertEquals("id", event2.getId());
    assertNull(event2.getHost());
    assertEquals(1L, event2.getTimestamp());
    assertNull(event2.getType());

    assertSame(entry1, map.get("id"));
  }

  @Test
  public void convertFirstApplicationLogEntry() {
    LogEntry entry = new ApplicationLogEntry("id", "state", 1L, "type", "host");
    Event event = converter.convert(entry);

    assertNull(event);
    assertSame(entry, map.get("id"));
  }

  @Test
  public void convertSecondApplicationLogEntryWithSameId() {
    LogEntry entry1 = new ApplicationLogEntry("id", "state", 1L, Type.APPLICATION_LOG.name(), "host");
    LogEntry entry2 = new ApplicationLogEntry("id", "state", 2L, Type.APPLICATION_LOG.name(), "host");
    Event event1 = converter.convert(entry1);
    Event event2 = converter.convert(entry2);

    assertNull(event1);
    assertNotNull(event2);
    assertEquals("id", event2.getId());
    assertEquals("host", event2.getHost());
    assertEquals(1L, event2.getTimestamp());
    assertEquals(Type.APPLICATION_LOG, event2.getType());

    assertSame(entry1, map.get("id"));
  }

  @Test
  public void convertRandomLogEntries() {
    Set<LogEntry> logEntries = new HashSet<>();

    for (int i = 0; i < 50; i++) {
      int id = i % 25;
      LogEntry entry = new LogEntry("lid" + id, i < 25 ? "STARTED" : "FINISHED", i % 4);
      logEntries.add(entry);
    }

    for (int i = 50; i < 100; i++) {
      int id = (i - 50) % 25;
      LogEntry entry = new ApplicationLogEntry("alid" + id, i < 75 ? "STARTED" : "FINISHED", i, Type.APPLICATION_LOG.name(), "host" + id);
      logEntries.add(entry);
    }

    Set<Event> eventEntries = new HashSet<>();
    for (LogEntry logEntry : logEntries) {
      Event convert = converter.convert(logEntry);
      if (convert == null) {
        continue;
      }
      eventEntries.add(convert);
    }

    assertEquals(50, eventEntries.size());
    assertEquals(25, eventEntries.stream().filter(eventEntry -> eventEntry.getTimestamp() > 4).count());
    assertEquals(25, eventEntries.stream().filter(Event::isAlert).count());
    assertTrue(eventEntries.stream().filter(eventEntry -> eventEntry.getTimestamp() > 4).allMatch(Event::isAlert));
    assertTrue(eventEntries.stream().filter(eventEntry -> StringUtils.hasText(eventEntry.getHost())).allMatch(eventEntry -> eventEntry.getType() == Type.APPLICATION_LOG));
    assertTrue(eventEntries.stream().filter(eventEntry -> eventEntry.getHost() == null).allMatch(eventEntry -> eventEntry.getType() == null));
  }
}