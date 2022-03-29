package creditsuisse.demo.log.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import creditsuisse.demo.log.model.LogEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class LogParser {
    private final ObjectMapper objectMapper;

    @Autowired
    public LogParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @EventListener
    public LogEntry parseLine(String line) {
        try {
            LogEntry logEntry = objectMapper.readValue(line, LogEntry.class);
            log.info("Line " + logEntry.toString());
            return logEntry;
        } catch (Exception e) {
            log.error("Parsing error " + line, e);
            return null;
        }
    }
}
