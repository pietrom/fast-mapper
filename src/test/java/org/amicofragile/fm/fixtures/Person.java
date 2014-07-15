package org.amicofragile.fm.fixtures;

public class Person {
	private String firstName, surname;

	public Person() {
	}
	
	public Person(String firstname, String surname) {
		this.firstName = firstname;
		this.surname = surname;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
}
