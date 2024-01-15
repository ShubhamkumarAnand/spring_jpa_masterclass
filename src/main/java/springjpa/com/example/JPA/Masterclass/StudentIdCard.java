package springjpa.com.example.JPA.Masterclass;

import jakarta.persistence.*;

@Entity(name = "StudentIdCard")
@Table(name = "student_id_card", uniqueConstraints = {
        @UniqueConstraint(name = "student_card_number_unique", columnNames = "cardNumber")
})
public class StudentIdCard {
    @Id
    @SequenceGenerator(
            name = "student_card_id_sequence",
            sequenceName = "student_card_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_card_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @OneToOne(
            cascade = CascadeType.ALL
//            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id"
    )
    private Student student;
    @Column(
            name = "card_number",
            nullable = true,
            length = 15
    )
    private String cardNumber;

    public StudentIdCard(Student student, String cardNumber) {
        this.student = student;
        this.cardNumber = cardNumber;
    }

    public StudentIdCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public StudentIdCard() {
    }

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", student=" + student +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
