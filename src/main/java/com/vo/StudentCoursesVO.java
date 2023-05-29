package com.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class StudentCoursesVO implements Serializable {

	private static final long serialVersionUID = 5720689288708644411L;

	private String studentEmail;

	private String courseTitle;

}
