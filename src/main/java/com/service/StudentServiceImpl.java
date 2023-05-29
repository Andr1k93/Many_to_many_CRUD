package com.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Student;
import com.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void insertStudent(Student student) {
		try {
			studentRepository.insertStudent(student);
			log.info("Inserimento Student avvenuto con successo");
		} catch (Exception e) {
			log.error("Inserimento Student fallito");
			e.printStackTrace();
		}
	}

	@Override
	public void updateStudent(String studentEmail, Student student) {
		try {
			studentRepository.updateStudent(studentEmail, student);
			log.info("Aggiornamento Student avvenuto con successo");
		} catch (Exception e) {
			log.error("Aggiornamento Student fallito");
			e.printStackTrace();
		}
	}

	@Override
	public void deleteNotRelatedStudent(String studentEmail) {
		try {
			studentRepository.deleteNotRelatedStudent(studentEmail);
			log.info("Cancellazione Student avvenuta con successo");
		} catch (Exception e) {
			log.error("Cancellazione Student fallita");
			e.printStackTrace();
		}
	}

	@Override
	public List<Student> readAllStudents() {
		List<Student> students = null;
		try {
			students = studentRepository.readAllStudents();
			log.info("Lettura Student avvenuta con successo");
		} catch (Exception e) {
			log.error("Lettura Student fallita");
			e.printStackTrace();
		}
		return students;
	}

}
