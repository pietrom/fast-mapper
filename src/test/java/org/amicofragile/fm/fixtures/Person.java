package org.amicofragile.fm.fixtures;

public class Person {
	private final String firstName, surname;

	public Person(String firstname, String surname) {
		this.firstName = firstname;
		this.surname = surname;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getSurname() {
		return surname;
	}
}
