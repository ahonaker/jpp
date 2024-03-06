package net.derbyparty.jpp.chartparser.charts.pdf;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * The rider of the {@link Starter}
 */
public class Jockey {
	public String name;
	public String firstName;
	public String lastName;

    @JsonCreator
    public Jockey(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = (firstName != null && !firstName.isEmpty()) ?
                (firstName + " " + lastName) : lastName;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jockey jockey = (Jockey) o;

        if (name != null ? !name.equals(jockey.name) : jockey.name != null) return false;
        if (firstName != null ? !firstName.equals(jockey.firstName) : jockey.firstName != null)
            return false;
        return lastName != null ? lastName.equals(jockey.lastName) : jockey.lastName == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Jockey{" +
                "name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

	public Jockey() {
		super();
		
		// TODO Auto-generated constructor stub
	}
}
