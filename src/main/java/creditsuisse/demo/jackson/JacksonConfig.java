package creditsuisse.demo.jackson;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import creditsuisse.demo.log.model.LogEntry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
@Primary
@Configuration
public class JacksonConfig {
    @Primary
    @Bean
    public Module logHierarchyModule() {
        SimpleModule module = new SimpleModule("LogHierarchyModule");
        module.addDeserializer(LogEntry.class, new LogDeserializer(LogEntry.class));
        return module;
    }
}
