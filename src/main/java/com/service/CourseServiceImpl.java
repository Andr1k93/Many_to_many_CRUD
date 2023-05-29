package com.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Course;
import com.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepository;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void insertCourse(Course course) {
		try {
			courseRepository.insertCourse(course);
			log.info("Inserimento Course avvenuto con successo");
		} catch (Exception e) {
			log.error("Inserimento Course fallito");
			e.printStackTrace();
		}

	}

	@Override
	public void updateCourse(String courseTitle, Course course) {
		try {
			courseRepository.updateCourse(courseTitle, course);
			log.info("Aggiornamento Course avvenuto con successo");
		} catch (Exception e) {
			log.error("Aggiornamento Course fallito");
			e.printStackTrace();
		}

	}

	@Override
	public void deleteNotRelatedCourse(String courseTitle) {
		try {
			courseRepository.deleteNotRelatedCourse(courseTitle);
			log.info("Cancellazione Course avvenuta con successo");
		} catch (Exception e) {
			log.error("Cancellazione Course fallita");
			e.printStackTrace();
		}

	}

	@Override
	public List<Course> readAllCourses() {
		List<Course> courses = null;
		try {
			courses = courseRepository.readAllCourses();
			log.info("Lettura dei Course avvenuta con successo");
		} catch (Exception e) {
			log.error("Lettura Course fallita");
			e.printStackTrace();
		}
		return courses;
	}

}
