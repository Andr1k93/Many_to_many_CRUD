package com.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Course;
import com.entity.Student;
import com.repository.StudentCourseRepository;
import com.vo.CourseStudentsVO;
import com.vo.StudentCoursesVO;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

	@Autowired
	private StudentCourseRepository studentCourseRepository;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void insertStudentCourses(Student student, List<Course> courses) {
		try {
			studentCourseRepository.insertStudentCourses(student, courses);
			log.info("Inserimento di uno Student e dei suoi Course avvenuto con successo");
		} catch (Exception e) {
			log.error("Inserimento di uno Student e dei suoi Course fallito");
			e.printStackTrace();
		}
	}

	@Override
	public void insertCourseStudents(Course course, List<Student> students) {
		try {
			studentCourseRepository.insertCourseStudents(course, students);
			log.info("Inserimento di un Course e dei suoi Student avvenuto con successo");
		} catch (Exception e) {
			log.error("Inserimento di un Course e dei suoi Student fallito");
			e.printStackTrace();
		}
	}

	@Override
	public void addCourseToStudent(String studentEmail, Course course) {
		try {
			studentCourseRepository.addCourseToStudent(studentEmail, course);
			log.info("Aggiunta di un Course ad uno Student avvenuta con successo");
		} catch (Exception e) {
			log.error("Aggiunta di un Course ad uno Student fallita");
			e.printStackTrace();
		}
	}

	@Override
	public void addStudentToCourse(String courseTitle, Student student) {
		try {
			studentCourseRepository.addStudentToCourse(courseTitle, student);
			log.info("Aggiunta di uno Student ad un Course avvenuta con successo");
		} catch (Exception e) {
			log.error("Aggiunta di uno Student ad un Course fallita");
			e.printStackTrace();
		}
	}

	@Override
	public void removeStudentCourseRelation(String studentEmail, String courseTitle) {
		try {
			studentCourseRepository.removeStudentCourseRelation(studentEmail, courseTitle);
			log.info("Rimozione della relazione tra uno Student ed un Course avvenuta con successo");
		} catch (Exception e) {
			log.error("Rimozione della relazione tra uno Student ed un Course fallita");
			e.printStackTrace();
		}

	}

	@Override
	public List<StudentCoursesVO> readCoursesByStudent(String studentEmail) {
		List<StudentCoursesVO> result = null;
		try {
			result = studentCourseRepository.readCoursesByStudent(studentEmail);
			log.info("Lettura dei Course di uno Student avvenuta con successo");
		} catch (Exception e) {
			log.error("Lettura dei Course di uno Student fallita");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<CourseStudentsVO> readStudentsByCourse(String courseTitle) {
		List<CourseStudentsVO> result = null;
		try {
			result = studentCourseRepository.readStudentsByCourse(courseTitle);
			log.info("Lettura degli Student di un Course avvenuta con successo");
		} catch (Exception e) {
			log.error("Lettura degli Student di un Course fallita");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Course> test() {
		return studentCourseRepository.test();
	}

}
