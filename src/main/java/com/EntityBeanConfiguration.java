package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.entity.Course;
import com.entity.Student;

@Configuration
public class EntityBeanConfiguration {

	/*
	 * Beans per le CRUD su Course
	 */
	@Bean(name = "courseToInsert1")
	public Course getCourseToInsert1() {
		return new Course("Java Base", "Corso su AAAAAAA - Base level");
	}

	@Bean(name = "courseToUpdate1")
	public Course getCourseToUpdate1() {
		return new Course("Java Base", "Corso su Java Base - Base level");
	}

	/*
	 * Beans per le CRUD su Student
	 */

	@Bean(name = "studentToInsert1")
	public Student getStudentToInsert1() {
		return new Student("Mikele", "Cerqua", "mic_cer@gmail.com", -12);
	}

	@Bean(name = "studentToUpdate1")
	public Student getStudentToUpdate1() {
		return new Student("Michele", "Cerqua", "mic_cer@gmail.com", 21);
	}

	/*
	 * Beans per le CRUD sulle Commons
	 */

	@Bean(name = "studentToInsert2")
	public Student getStudentToInsert2() {
		return new Student("Federico", "Maniscalco", "fed_man@gmail.com", 21);
	}

	@Bean(name = "courseToInsert2")
	public Course getCourseToInsert2() {
		return new Course("Java Base", "Corso su Java Base - Base level");
	}

	@Bean(name = "courseToInsert3")
	public Course getCourseToInsert3() {
		return new Course("Java Spring", "Corso su Java Spring - Advanced level");
	}

	@Bean(name = "courseToInsert4")
	public Course getCourseToInsert4() {
		return new Course("Angular", "Corso Frontend - The Best you'll ever find");
	}

	@Bean(name = "studentToInsert3")
	public Student getStudentToInsert3() {
		return new Student("Francesco", "Rizzi", "fra_riz@gmail.com", 21);
	}

	@Bean(name = "studentToInsert4")
	public Student getStudentToInsert4() {
		return new Student("Roberto", "Graffeo", "rob_gra@gmail.com", 21);
	}

	@Bean(name = "courseToInsert5")
	public Course getCourseToInsert5() {
		return new Course("Filosofia", "Monografia su Immanuel Kant");
	}

	@Bean(name = "studentToInsert5")
	public Student getStudentToInsert5() {
		return new Student("Andrea", "Comparato", "and_com@gmail.com", 21);
	}

}
