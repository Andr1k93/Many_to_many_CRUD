package com.repository;

import java.util.List;

import com.entity.Course;
import com.entity.Student;
import com.vo.CourseStudentsVO;
import com.vo.StudentCoursesVO;

public interface StudentCourseRepository {

	public void insertStudentCourses(Student student, List<Course> courses);

	public void insertCourseStudents(Course course, List<Student> students);

	public void addCourseToStudent(String studentEmail, Course course);

	public void addStudentToCourse(String courseTitle, Student student);

	public void removeStudentCourseRelation(String studentEmail, String courseTitle);

	/*
	 * To do: vedere se implementare anche la rimozione di student e course che
	 * hanno relazioni in particolar modo, essendo student l'owner della relation,
	 * la rimozione sul course dovrebbe esser problematica
	 */

	public List<StudentCoursesVO> readCoursesByStudent(String studentEmail);

	public List<CourseStudentsVO> readStudentsByCourse(String courseTitle);

	public List<Course> test();

}
