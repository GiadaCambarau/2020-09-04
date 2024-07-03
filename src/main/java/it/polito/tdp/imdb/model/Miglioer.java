package it.polito.tdp.imdb.model;

import java.util.Objects;

public class Miglioer {
	private Movie m;
	private int somma;
	public Miglioer(Movie m, int somma) {
		super();
		this.m = m;
		this.somma = somma;
	}
	public Movie getM() {
		return m;
	}
	public void setM(Movie m) {
		this.m = m;
	}
	public int getSomma() {
		return somma;
	}
	public void setSomma(int somma) {
		this.somma = somma;
	}
	@Override
	public int hashCode() {
		return Objects.hash(m, somma);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Miglioer other = (Miglioer) obj;
		return Objects.equals(m, other.m) && somma == other.somma;
	}
	@Override
	public String toString() {
		return  m + "     " + somma ;
	}
	
	
}
