package com.service;

import java.util.List;

import com.entity.Student;

public interface StudentService {

	public void insertStudent(Student student);

	public void updateStudent(String studentEmail, Student student);

	public void deleteNotRelatedStudent(String studentEmail);

	public List<Student> readAllStudents();

}
