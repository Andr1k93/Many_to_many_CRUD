package com.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Course;
import com.entity.Student;
import com.vo.CourseStudentsVO;
import com.vo.StudentCoursesVO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Transactional
@Repository
public class StudentCourseRepositoryImpl implements StudentCourseRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void insertStudentCourses(Student student, List<Course> courses) {
		// Associo ogni corso allo studente:
		// per ogni corso...
		courses.forEach(c -> {
			// ...prendo la sua lista di students e gli aggiungo questo student
			c.getStudents().add(student);
			// infine persisto il corso
			em.persist(c);
		});
		// associo allo student la lista dei suoi courses
		student.setCourses(courses);
		// persisto lo student
		em.persist(student);

	}

	@Override
	public void insertCourseStudents(Course course, List<Student> students) {
		// Associo ogni studente al corso, quindi iterando su ogni studente...
		students.forEach(s -> {
			// ...lo aggiungo alla lista di studenti del corso
			course.getStudents().add(s);
			// infine, aggiungo il corso alla lista di corsi di ogni studente
			s.getCourses().add(course);
		});
		// inserisco prima il corso poiche' essendo Student l'owner della relazione
		// scrivo sulla join table quando faccio operazioni su Student. Quindi se non
		// trovo l'id del corso nella sua tabella, non posso inserirlo nella join table
		em.persist(course);
		// infine inserisco tutti gli studenti aggiornando contestualmente la join table
		students.forEach(s -> em.persist(s));
	}

	@Override
	public void addCourseToStudent(String studentEmail, Course course) {
		// Do per scontato che lo student esista gia' sul DB
		Student student = em.find(Student.class, readStudentPassportNumberByEmail(studentEmail));

		// Controllo se il corso da aggiungere alla lista di corsi dello studente
		// e' un corso nuovo (e quindi da inserire sul DB) o gia' pre-esistente sul DB
		// tramite il suo courseCode.
		String courseTitle = course.getTitle();
		String courseCode = null;

		try {
			courseCode = readCourseCodeByTitle(courseTitle);
			// IL CORSO ESISTE SUL DB

			// Aggiungo lo studente alla lista di studenti del corso
			course.getStudents().add(student);

			// Aggiungo il corso alla lista di corsi dello studente
			student.getCourses().add(course);

			// Aggiorno il corso per far si' che la sua lista di studenti,
			// ovvero course.getStudents() (quindi a livello oggetti sul backend)
			// sia aggiornata. N.B. Sul DB l'associazione tra questo course
			// e questo student e' gia' correttamente realizzata (vedi join table).
			// Se non facciamo questo merge e recuperiamo con una select questo course
			// per vedere la lista di student ad esso iscritti, non comparirebbe l'ultimo
			// aggiunto. Col merge invece si', mantenendo una coerenza tra cio' che
			// compare sul DB con cio' che recuperiamo sul backend
			em.merge(course);

		} catch (NoResultException nre) {
			// IL CORSO NON ESISTE SUL DB

			// Aggiungo lo studente alla lista di studenti del corso
			course.getStudents().add(student);

			// Inserisco il corso sul DB affinche' le operazioni sulla join table
			// non diano errore
			em.persist(course);

			// Aggiungo il corso alla lista di corsi dello studente
			student.getCourses().add(course);
		} finally {
			// eseguo come ultima operazione il merge sull'owner della relazione
			em.merge(student);
		}

	}

	@Override
	public void addStudentToCourse(String courseTitle, Student student) {
		// vedi commenti su addCourseToStudent
		Course course = em.find(Course.class, readCourseCodeByTitle(courseTitle));

		String studentEmail = student.getEmail();
		String studentPassportNumber = null;

		try {
			studentPassportNumber = readStudentPassportNumberByEmail(studentEmail);
			student.getCourses().add(course);
			course.getStudents().add(student);
			em.merge(student);
		} catch (NoResultException nre) {
			course.getStudents().add(student);
			student.getCourses().add(course);
			em.persist(student);
		}
	}

	@Override
	public void removeStudentCourseRelation(String studentEmail, String courseTitle) {
		// Recupero sia il course che lo student in questione. Sto dando per scontato
		// che siano entrambi presenti sul DB
		Course course = em.find(Course.class, readCourseCodeByTitle(courseTitle));
		Student student = em.find(Student.class, readStudentPassportNumberByEmail(studentEmail));

		// Elimino l'associazione tra corso e studente nel seguente modo:
		// - dalla lista di studenti del corso, rimuovo la reference dello studente
		course.getStudents().remove(student);
		// - dalla lista di corsi dello studente, elimino la reference del corso
		student.getCourses().remove(course);
		// aggiorno quindi prima il course
		em.merge(course);
		// e poi lo student, che e' l'owner della relazione
		em.merge(student);

		// facendo i merge di entrambi gli oggetti, mantengo coerente la situazione
		// tra il DB ed il BE

	}

	@Override
	public List<StudentCoursesVO> readCoursesByStudent(String studentEmail) {
		// Inizializzo la lista di VO che restituiro'
		List<StudentCoursesVO> result = new ArrayList<>();

		// Recupero la lista di corsi afferenti allo studente con l'email in input
		List<Course> courses = em.createNamedQuery("Student.findAllCourses", Course.class)
				.setParameter("email", studentEmail).getResultList();

		// Popolo la lista di VO mappando ogni elemento con l'email dello studente
		// e i titoli dei corsi che segue
		courses.forEach(c -> result.add(new StudentCoursesVO(studentEmail, c.getTitle())));
		return result;
	}

	@Override
	public List<CourseStudentsVO> readStudentsByCourse(String courseTitle) {
		// Inizializzo la lista di VO che restituiro'
		List<CourseStudentsVO> result = new ArrayList<>();

		// Recupero la lista di student afferenti al corso con il titolo in input
		List<Student> students = em.createNamedQuery("Course.findAllStudents", Student.class)
				.setParameter("title", courseTitle).getResultList();

		// Popolo la lista di VO mappando ogni elemento con il titolo del corso
		// e le email degli studenti iscritti
		students.forEach(s -> result.add(new CourseStudentsVO(courseTitle, s.getEmail())));
		return result;
	}

	private String readCourseCodeByTitle(String courseTitle) {
		return em.createNamedQuery("Course.findCodeByTitle", String.class).setParameter("title", courseTitle)
				.getSingleResult();
	}

	private String readStudentPassportNumberByEmail(String studentEmail) {
		return em.createNamedQuery("Student.findPassportNumberByEmail", String.class)
				.setParameter("email", studentEmail).getSingleResult();
	}

	@Override
	public List<Course> test() {

		return em.createNamedQuery("Student.findAllCourses", Course.class).setParameter("email", "fed_man@gmail.com")
				.getResultList();
	}

}
