package com.example.assignment_android_basic;

public class Student {
	private String ID, Name, Email, ClassID;
	private double Mark;
	private byte[] Avatar;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getClassID() {
		return ClassID;
	}
	public void setClassID(String classID) {
		ClassID = classID;
	}
	public double getMark() {
		return Mark;
	}
	public void setMark(double mark) {
		Mark = mark;
	}
	public byte[] getAvatar() {
		return Avatar;
	}
	public void setAvatar(byte[] avatar) {
		Avatar = avatar;
	}
	public Student(String iD, String name, String email, String classID,
			double mark, byte[] avatar) {
		super();
		ID = iD;
		Name = name;
		Email = email;
		ClassID = classID;
		Mark = mark;
		Avatar = avatar;
	}
	
	
	
	
}
