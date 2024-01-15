package springjpa.com.example.JPA.Masterclass;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

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
			student.addBook(
					new Book(
							"computer science",
							LocalDateTime.now().minusDays(4)
					)
			);
			student.addBook(
					new Book(
							"lean startup",
							LocalDateTime.now()
					)
			);
			student.addBook(
					new Book(
							"Vivek Ramasawmy",
							LocalDateTime.now().minusYears(1)
					)
			);
			StudentIdCard  studentIdCard = new StudentIdCard(student, "1234567");
			student.setStudentIdCard(studentIdCard);
			studentRepository.save(student);
			studentRepository.findById(1L).ifPresent(s -> {
				System.out.println("Find book lazy...");
				List<Book> books = student.getBooks();
				books.forEach(book -> {
					System.out.println(s.getFirstName() + " borrowed " + book.getBookName());
				});
			});
//			studentIdCardRepository.findById(1L).ifPresent(System.out::println);
//			studentRepository.deleteById(1L);
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

