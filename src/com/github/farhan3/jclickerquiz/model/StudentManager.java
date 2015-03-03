package com.github.farhan3.jclickerquiz.model;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class StudentManager {
	
	private static StudentManager _studentManger;
	
	private static Collection<Integer> _students = new LinkedList<Integer>();
	
	private StudentManager() {
	}
	
	public static StudentManager getInstance() {
		if (_studentManger == null) {
			_studentManger = new StudentManager();
		}
		
		return _studentManger;
	}
	
	public void addStudent(int studentNumber)  {
		_students.add(studentNumber);
	}
	
	public void addStudents(String studentListFilePath) throws URISyntaxException {
		_students.addAll(DataUtil.getStudentNumbers(studentListFilePath));
	}
	
	public void addStudents(Collection<Integer> students) {
		_students.addAll(students);
	}
	
	public Collection<Integer> getStudents() {
		return Collections.unmodifiableCollection(_students);
	}
	
	public boolean checkStudentInClass(int studentNumber) {
		return _students.contains(studentNumber);
	}
}
