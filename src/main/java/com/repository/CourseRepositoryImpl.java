package com.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Course;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
@Repository
public class CourseRepositoryImpl implements CourseRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void insertCourse(Course course) {
		em.persist(course);
	}

	@Override
	public void updateCourse(String courseTitle, Course course) {
		String courseCode = readCourseCodeByTitle(courseTitle);
		Course courseOnDB = em.find(Course.class, courseCode);
		course.setStudents(courseOnDB.getStudents());
		course.setCode(courseCode);
		em.merge(course);
	}

	@Override
	public void deleteNotRelatedCourse(String courseTitle) {
		em.remove(em.find(Course.class, readCourseCodeByTitle(courseTitle)));
	}

	@Override
	public List<Course> readAllCourses() {
		return em.createNamedQuery("Course.findAll", Course.class).getResultList();
	}

	private String readCourseCodeByTitle(String courseTitle) {
		return em.createNamedQuery("Course.findCodeByTitle", String.class).setParameter("title", courseTitle)
				.getSingleResult();
	}

}
