package com.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@NamedQueries({ @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
		@NamedQuery(name = "Student.findPassportNumberByEmail", query = "SELECT s.passportNumber FROM Student s WHERE s.email = :email"),
		@NamedQuery(name = "Student.findAllCourses", query = "SELECT s.courses FROM Student s WHERE s.email = :email") })
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "passport_number")
	private String passportNumber;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(unique = true, nullable = false)
	private String email;

	private Integer age;

	@ManyToMany()
	@JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "passport_number"), inverseJoinColumns = @JoinColumn(name = "code"))
	private List<Course> courses = new ArrayList<>();

	public Student(String passportNumber, String firstName, String lastName, String email, Integer age) {
		this.passportNumber = passportNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.age = age;
	}

	public Student(String firstName, String lastName, String email, Integer age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Student [passportNumber=" + passportNumber + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", age=" + age + "]";
	}

}
