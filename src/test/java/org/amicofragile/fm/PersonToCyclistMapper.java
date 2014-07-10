package org.amicofragile.fm;

import org.amicofragile.fm.fixtures.Cyclist;
import org.amicofragile.fm.fixtures.Person;

public class PersonToCyclistMapper implements ClassMapper<Person, Cyclist> {

	@Override
	public Cyclist map(Person input) {
		final String fullName = String.format("%s %s", input.getFirstName().toUpperCase(), input.getSurname().toUpperCase());
		return new Cyclist(fullName);
	}
}
