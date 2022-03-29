package creditsuisse.demo.event.service;

import creditsuisse.demo.event.model.Event;
import creditsuisse.demo.log.model.LogEntry;
import creditsuisse.demo.log.service.LogScanner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {
  @Mock
  private EventRepository repository;
  @Mock
  private LogScanner logScanner;

  private EventService eventService;

  @Before
  public void setUp() {
    eventService = new EventService(repository, logScanner);
  }

  @Test
  public void parseEvents() {
    String somePath = "somePath";
    eventService.parseEvents(somePath);

    ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
    verify(logScanner, times(1)).read(pathCaptor.capture());
    String capturedEvent = pathCaptor.getValue();
    assertSame(somePath, capturedEvent);
  }

  @Test
  public void saveEvent() {
    Event entry = new Event(new LogEntry("id", "state", 1L), 1L);
    eventService.saveEvent(entry);

    ArgumentCaptor<Event> pathCaptor = ArgumentCaptor.forClass(Event.class);
    verify(repository, times(1)).save(pathCaptor.capture());
    Event capturedEvent = pathCaptor.getValue();
    assertSame(entry, capturedEvent);
  }
}