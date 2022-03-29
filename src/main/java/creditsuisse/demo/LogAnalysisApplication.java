package creditsuisse.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import creditsuisse.demo.event.service.EventService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class LogAnalysisApplication implements CommandLineRunner {
	private final EventService eventService;
	public LogAnalysisApplication(EventService eventService){
		this.eventService = eventService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LogAnalysisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args == null || args.length != 1) {
			throw new IllegalArgumentException("Need to input 1 and only 1 argument");
		}

		eventService.parseEvents(args[0]);
		System.exit(0);
	}
}
