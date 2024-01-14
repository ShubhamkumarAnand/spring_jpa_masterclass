package springjpa.com.example.JPA.Masterclass;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaMasterclassApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaMasterclassApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			Faker faker = new Faker();
			for (int i = 0; i <= 20; i++) {
				String firstName = faker.name().firstName();
				String lastName = faker.name().lastName();
				String email = String.format("%s.%s@imskanand.code", firstName, lastName);
				Student student = new Student(firstName, lastName, email, faker.number().numberBetween(17, 45));
				studentRepository.save(student);
			}
		};
	}
}

