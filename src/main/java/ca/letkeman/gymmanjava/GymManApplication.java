package ca.letkeman.gymmanjava;

import ca.letkeman.gymmanjava.model.Exercise;
import ca.letkeman.gymmanjava.repository.ExerciseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GymManApplication {

	private static final Logger log = LoggerFactory.getLogger(GymManApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GymManApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@Bean
	public CommandLineRunner demo(ExerciseRepository repository) {
		return args -> {
			repository.save(new Exercise("pete", "letkeman"));
			repository.save(new Exercise("alex", "smith"));
			repository.save(new Exercise("user", "name"));
			repository.save(new Exercise("fred", "java"));

			log.info("finall():");
			repository.findAll().forEach(exercise -> log.info(exercise.toString()));
			log.info("");

			log.info("find letkeman");
			log.info(repository.findByName("pete").toString());

			log.info("find id");
			log.info(repository.findById(2L).toString());
		};
	};
}
