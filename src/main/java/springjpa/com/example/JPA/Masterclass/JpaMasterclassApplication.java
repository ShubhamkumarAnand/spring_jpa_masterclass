package springjpa.com.example.JPA.Masterclass;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class JpaMasterclassApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaMasterclassApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			Student maria = new Student("maria", "jon", "maria@spring.edu", 24);
			Student ram = new Student("ram", "chandra", "ram@ramayan.com", 21);
			Student ramS = new Student("ram", "chandra", "ramsharma@ramayan.com", 25);
			studentRepository.saveAll(List.of(maria, ram, ramS));
			studentRepository
					.findStudentByEmail("ram@ramayan.com")
					.ifPresentOrElse(System.out::println,
							() -> System.out.println("Student with given email not found"));

			studentRepository.findStudentByFirstNameAndAgeIsGreaterThanEqual("ram", 21).forEach(System.out::println);

			studentRepository.findStudentByFirstNameAndAgeIsGreaterThanEqualNative("ram", 21).forEach(System.out::println);
		};
	}
}

