package com.service;

import java.util.List;

import com.entity.Course;

public interface CourseService {

	public void insertCourse(Course course);

	public void updateCourse(String courseTitle, Course course);

	public void deleteNotRelatedCourse(String courseTitle);

	public List<Course> readAllCourses();

}
