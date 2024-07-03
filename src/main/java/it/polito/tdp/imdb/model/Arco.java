package it.polito.tdp.imdb.model;

import java.util.Objects;

public class Arco {
	private Movie m1;
	private Movie m2;
	private int peso;
	public Arco(Movie m1, Movie m2, int peso) {
		super();
		this.m1 = m1;
		this.m2 = m2;
		this.peso = peso;
	}
	public Movie getM1() {
		return m1;
	}
	public void setM1(Movie m1) {
		this.m1 = m1;
	}
	public Movie getM2() {
		return m2;
	}
	public void setM2(Movie m2) {
		this.m2 = m2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		return Objects.hash(m1, m2, peso);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		return Objects.equals(m1, other.m1) && Objects.equals(m2, other.m2) && peso == other.peso;
	}
	

}
