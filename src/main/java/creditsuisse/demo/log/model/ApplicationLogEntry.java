package creditsuisse.demo.log.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ApplicationLogEntry extends LogEntry{
    private final String type;
    private final String host;

    public ApplicationLogEntry(String id, String state, long timestamp, String type, String host) {
        super(id, state, timestamp);
        this.type = type;
        this.host = host;
    }
}
