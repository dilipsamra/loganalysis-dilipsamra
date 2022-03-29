package creditsuisse.demo.event.model;

import creditsuisse.demo.log.model.ApplicationLogEntry;
import creditsuisse.demo.log.model.LogEntry;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity
public class Event {
    @Id
    private final String id;
    private final long timestamp;
    @Enumerated(value = EnumType.STRING)
    private final Type type;
    private final String host;
    private final boolean alert;

    public Event(LogEntry entry, long timestamp) {
        this.id = entry.getId();
        this.timestamp = timestamp;
        this.alert = timestamp > 4;

        boolean isApplicationLogEntry = entry instanceof ApplicationLogEntry;
        this.type = isApplicationLogEntry ? Type.valueOf(((ApplicationLogEntry) entry).getType()) : null;
        this.host = isApplicationLogEntry ? ((ApplicationLogEntry) entry).getHost() : null;
    }
}
