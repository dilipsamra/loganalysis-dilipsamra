package creditsuisse.demo.event.service;

import creditsuisse.demo.event.model.Event;
import creditsuisse.demo.log.service.LogScanner;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository repository;
    private final LogScanner logScanner;

    public EventService(EventRepository repository, LogScanner logScanner) {
        this.repository = repository;
        this.logScanner = logScanner;
    }

    public void parseEvents(String path) {
        logScanner.read(path);
    }

    @EventListener
    public void saveEvent(Event entry) {
        repository.save(entry);
    }
}
