package creditsuisse.demo.log.model;

import lombok.Data;

@Data
public class LogEntry {
    private final String id;
    private final String state;
    private final long timestamp;

    public LogEntry(String id, String state, long timestamp) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
    }
}
