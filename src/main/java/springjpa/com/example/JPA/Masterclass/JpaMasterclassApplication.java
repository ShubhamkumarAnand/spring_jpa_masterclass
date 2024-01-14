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
			studentRepository.saveAll(List.of(maria, ram));
			System.out.println(studentRepository.count());

			studentRepository
					.findById(2L)
					.ifPresentOrElse(
							System.out::println,
							() -> System.out.println("Student with id = 2 not Found"));

			studentRepository
					.findById(3L)
					.ifPresentOrElse(
							System.out::println,
							() -> System.out.println("Student with id = 3 not Found"));

			List<Student> students = studentRepository.findAll();
			students.forEach(System.out::println);

			studentRepository.deleteById(1L);
			System.out.println(studentRepository.count());
		};
	}
}

