package creditsuisse.demo.event.model;

import creditsuisse.demo.log.model.ApplicationLogEntry;
import creditsuisse.demo.log.model.LogEntry;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventTest {

  @Test
  public void createEventEntry() {
    Event entry = new Event(new LogEntry("id", "state", 1L), 3);
    assertEquals("id", entry.getId());
    assertEquals(3, entry.getTimestamp());
    assertNull(entry.getType());
    assertNull(entry.getHost());
    assertFalse(entry.isAlert());

    entry = new Event(new LogEntry("id", "state", 1L), 5);
    assertEquals("id", entry.getId());
    assertEquals(5, entry.getTimestamp());
    assertNull(entry.getType());
    assertNull(entry.getHost());
    assertTrue(entry.isAlert());

    entry = new Event(new ApplicationLogEntry("id", "state", 1L, Type.APPLICATION_LOG.name(), "123"), 3);
    assertEquals("id", entry.getId());
    assertEquals(3, entry.getTimestamp());
    assertEquals(Type.APPLICATION_LOG, entry.getType());
    assertEquals("123", entry.getHost());
    assertFalse(entry.isAlert());

    entry = new Event(new ApplicationLogEntry("id", "state", 1L, Type.APPLICATION_LOG.name(), "123"), 5);
    assertEquals("id", entry.getId());
    assertEquals(5, entry.getTimestamp());
    assertEquals(Type.APPLICATION_LOG, entry.getType());
    assertEquals("123", entry.getHost());
    assertTrue(entry.isAlert());
  }
}