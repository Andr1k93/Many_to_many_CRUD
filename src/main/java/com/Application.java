package com;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.entity.Course;
import com.entity.Student;
import com.service.CourseService;
import com.service.StudentCourseService;
import com.service.StudentService;

import jakarta.annotation.Resource;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private CourseService courseService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentCourseService studentCourseService;

	/*
	 * Resources per Course
	 */
	@Resource(name = "courseToInsert1")
	private Course courseToInsert1;

	@Resource(name = "courseToUpdate1")
	private Course courseToUpdate1;

	/*
	 * Resources per Student
	 */

	@Resource(name = "studentToInsert1")
	private Student studentToInsert1;

	@Resource(name = "studentToUpdate1")
	private Student studentToUpdate1;

	/*
	 * Resources per le Commons
	 */

	@Resource(name = "studentToInsert2")
	private Student studentToInsert2;

	@Resource(name = "courseToInsert2")
	private Course courseToInsert2;

	@Resource(name = "courseToInsert3")
	private Course courseToInsert3;

	@Resource(name = "courseToInsert4")
	private Course courseToInsert4;

	@Resource(name = "studentToInsert3")
	private Student studentToInsert3;

	@Resource(name = "studentToInsert4")
	private Student studentToInsert4;

	@Resource(name = "courseToInsert5")
	private Course courseToInsert5;

	@Resource(name = "studentToInsert5")
	private Student studentToInsert5;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/*
		 * TEST FUNZIONALITA' BASE SULL'ENTITY COURSE
		 */

		// Inserisco il Course di Java Base con la descrizione sbagliata
		courseService.insertCourse(courseToInsert1); // Java Base

		// Aggiorno la descrizione del corso appena inserito
		courseService.updateCourse("Java Base", courseToUpdate1);

		// Stampo tutti i Course presenti sul DB
		System.out.println("*****************\n* Elenco Course *\n*****************");
		courseService.readAllCourses().forEach(System.out::println);

		// Rimuovo il Course appena aggiornato, che non ha relazioni con alcuno Student
		courseService.deleteNotRelatedCourse("Java Base");

		/*
		 * TEST FUNZIONALITA' BASE SULL'ENTITY STUDENT
		 */

		// Inserisco uno Student con nome ed eta' sbagliati:
		studentService.insertStudent(studentToInsert1);

		// Aggiorno nome ed eta' dello Student appena inserito:
		studentService.updateStudent("mic_cer@gmail.com", studentToUpdate1);

		// Stampo tutti gli Student presenti sul DB
		System.out.println("******************\n* Elenco Student *\n******************");
		studentService.readAllStudents().forEach(System.out::println);

		// Rimuovo lo Student appena aggiornato, che non ha relazioni con alcun Course
		studentService.deleteNotRelatedStudent("mic_cer@gmail.com");

		/*
		 * TEST FUNZIONALITA' COMUNI
		 */

		// Inserisco uno Student con la sua lista di Course
		List<Course> courses = new ArrayList<>();
		courses.add(courseToInsert2);
		courses.add(courseToInsert3);
		studentCourseService.insertStudentCourses(studentToInsert2, courses);

		// Inserisco un Course con la sua lista di Student
		List<Student> students = new ArrayList<>();
		students.add(studentToInsert3);
		students.add(studentToInsert4);
		studentCourseService.insertCourseStudents(courseToInsert4, students);

		// Aggiungo due Course ad uno Student. Un Course esiste gia'
		// ed un Course (filosofia) non esiste ancora sul DB.

		// Aggiungo a Francesco Rizzo il corso su Java Spring che gia' esiste
		studentCourseService.addCourseToStudent("fra_riz@gmail.com", courseToInsert3);

		// Aggiungo a Francesco Rizzo il corso di Filosofia che ancora non esiste
		studentCourseService.addCourseToStudent("fra_riz@gmail.com", courseToInsert5);

		// Testo il metodo per rimuovere la relazione tra uno Student
		// ed un Course: rimuovo da Francesco Rizzo il corso di Filosofia
		studentCourseService.removeStudentCourseRelation("fra_riz@gmail.com", "Filosofia");

		// Aggiungo due Student ad un Course. Uno Student esiste gia'
		// e l'altro Student (and_com@gmail.com) ancora non esiste sul DB

		// Aggiungo al corso Java Base Andrea Comparato che ancora non e' stato
		// inserito sul DB
		studentCourseService.addStudentToCourse("Java Base", studentToInsert5);

		// Aggiungo al corso Java Base Roberto Graffeo che gia' esiste sul DB
		studentCourseService.addStudentToCourse("Java Base", studentToInsert4);

		// Stampo la lista di corsi afferenti allo student fed_man@gmail.com
		System.out.println("***\n* Elenco Course di fed_man@gmail.com\n***");
		studentCourseService.readCoursesByStudent("fed_man@gmail.com").forEach(System.out::println);

		// Stampo la lista di studenti iscritti al corso di Java Spring
		System.out.println("***\n* Elenco Student di Java Spring\n***");
		studentCourseService.readStudentsByCourse("Java Spring").forEach(System.out::println);
	}

}
