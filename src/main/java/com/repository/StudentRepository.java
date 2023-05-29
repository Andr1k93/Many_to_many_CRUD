package com.repository;

import java.util.List;

import com.entity.Student;

public interface StudentRepository {

	public void insertStudent(Student student);

	public void updateStudent(String studentEmail, Student student);

	public void deleteNotRelatedStudent(String studentEmail);

	public List<Student> readAllStudents();

}
