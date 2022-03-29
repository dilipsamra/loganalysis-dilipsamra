package creditsuisse.demo.event.service;

import creditsuisse.demo.event.model.Event;
import creditsuisse.demo.log.model.LogEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import org.springframework.core.convert.converter.Converter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
public class EventConvertor implements Converter<LogEntry, Event> {
    private final ConcurrentMap<String, LogEntry> map;

    public EventConvertor() {
        this.map = new ConcurrentHashMap<>();
    }

    EventConvertor(ConcurrentMap<String, LogEntry> map) {
        this.map = map;
    }

    @Override
    @EventListener
    public Event convert(LogEntry logSource) {
        String logId = logSource.getId();
        LogEntry logEntry = map.putIfAbsent(logId, logSource);
        if (logEntry == null || logSource.equals(logEntry)) {
            return null;
        }
        return convertLogToEvent(logSource, logEntry);
    }

    private Event convertLogToEvent(LogEntry logEntry, LogEntry handledLog) {
        long duration = Math.abs(logEntry.getTimestamp() - handledLog.getTimestamp());
        Event event = new Event(logEntry, duration);
        log.info("Logs {} and {} are transformed to {}", logEntry, handledLog, event);
        return event;
    }
}
