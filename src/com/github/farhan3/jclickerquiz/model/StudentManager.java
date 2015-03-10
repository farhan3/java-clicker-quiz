package com.github.farhan3.jclickerquiz.model;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Singleton class to handle student data
 * 
 * @author farhan
 *
 */
public class StudentManager {
	
	private static StudentManager _studentManger;
	
	private static Collection<Integer> _students = new LinkedList<Integer>();
	
	private StudentManager() {
	}
	
	/**
	 * Get the instance of StudentManager
	 * @return the singleton StudentManger
	 */
	public static StudentManager getInstance() {
		if (_studentManger == null) {
			_studentManger = new StudentManager();
		}
		
		return _studentManger;
	}
	
	/**
	 * Add a new student to the class list
	 * @param studentNumber
	 */
	public void addStudent(int studentNumber)  {
		_students.add(studentNumber);
	}
	
	/**
	 * Read student numbers from a file and add them to the class list.
	 * 
	 * The file should contain a student number per line
	 * @param studentListFilePath
	 * @throws URISyntaxException
	 */
	public void addStudents(String studentListFilePath) throws URISyntaxException {
		_students.addAll(DataUtil.getStudentNumbers(studentListFilePath));
	}
	
	/**
	 * Add new students to the class list
	 * @param students
	 */
	public void addStudents(Collection<Integer> students) {
		_students.addAll(students);
	}
	
	/**
	 * Get the list of students in the class
	 * 
	 * @return
	 */
	public Collection<Integer> getStudents() {
		return Collections.unmodifiableCollection(_students);
	}
	
	/**
	 * Check if a student is in the class list
	 * 
	 * @param studentNumber
	 * @return true if the student is in the class, false otherwise
	 */
	public boolean checkStudentInClass(int studentNumber) {
		return _students.contains(studentNumber);
	}
}
