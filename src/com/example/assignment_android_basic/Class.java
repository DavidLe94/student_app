package com.example.assignment_android_basic;

public class Class {
	private String ClassID;
	private String ClassName;
	
	public String getClassID() {
		return ClassID;
	}
	public void setClassID(String classID) {
		ClassID = classID;
	}
	public String getClassName() {
		return ClassName;
	}
	public void setClassName(String className) {
		ClassName = className;
	}
	public Class(String classID, String className) {
		super();
		ClassID = classID;
		ClassName = className;
	}
	
	
	
}
