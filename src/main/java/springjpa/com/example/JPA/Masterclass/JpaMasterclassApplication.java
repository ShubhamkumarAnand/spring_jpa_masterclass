package springjpa.com.example.JPA.Masterclass;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class JpaMasterclassApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaMasterclassApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository,
										StudentIdCardRepository studentIdCardRepository) {
		return args -> {
			Student student = generateOneStudent(studentRepository);
			StudentIdCard  studentIdCard = new StudentIdCard(student, "1234567");
			studentIdCardRepository.save(studentIdCard);
			studentRepository.findById(1L).ifPresent(System.out::println);
			studentIdCardRepository.findById(1L).ifPresent(System.out::println);
		};
	}

	private static Student generateOneStudent(StudentRepository studentRepository) {
		Faker faker = new Faker();
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String email = String.format("%s.%s@imskanand.code", firstName, lastName);
        //		studentRepository.save(student);
		return new Student(firstName, lastName, email, faker.number().numberBetween(17, 45));
	}

	private static void paginateAndSortStudentDataByFirstName(StudentRepository studentRepository) {
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("firstName").ascending());
		Page<Student> page = studentRepository.findAll(pageRequest);
		System.out.println(page);
	}

	private static void sortStudentDataByFirstNameAndAge(StudentRepository studentRepository) {
		Sort sort = Sort.by("firstName").ascending()
				.and(Sort.by("age").descending());
		studentRepository
				.findAll(sort)
				.forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
	}

	private static void generateRandomStudents(StudentRepository studentRepository) {
		Faker faker = new Faker();
		for (int i = 0; i <= 20; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@imskanand.code", firstName, lastName);
			Student student = new Student(firstName, lastName, email, faker.number().numberBetween(17, 45));
			studentRepository.save(student);
		}
	}
}

