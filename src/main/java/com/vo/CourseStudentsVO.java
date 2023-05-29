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

public class CourseStudentsVO implements Serializable {

	private static final long serialVersionUID = -8373626730787845971L;

	private String courseTitle;

	private String studentEmail;

}
