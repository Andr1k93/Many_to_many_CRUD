package com.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
@Repository
public class StudentRepositoryImpl implements StudentRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void insertStudent(Student student) {
		em.persist(student);
	}

	@Override
	public void updateStudent(String studentEmail, Student student) {
		String studentPassportNumber = readStudentPassportNumberByEmail(studentEmail);
		Student studentOnDB = em.find(Student.class, studentPassportNumber);
		student.setCourses(studentOnDB.getCourses());
		student.setPassportNumber(studentPassportNumber);
		em.merge(student);
	}

	@Override
	public void deleteNotRelatedStudent(String studentEmail) {
		em.remove(em.find(Student.class, readStudentPassportNumberByEmail(studentEmail)));
	}

	@Override
	public List<Student> readAllStudents() {
		return em.createNamedQuery("Student.findAll", Student.class).getResultList();
	}

	private String readStudentPassportNumberByEmail(String studentEmail) {
		return em.createNamedQuery("Student.findPassportNumberByEmail", String.class)
				.setParameter("email", studentEmail).getSingleResult();
	}

}
