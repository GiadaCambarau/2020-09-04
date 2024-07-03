package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	private ImdbDAO dao;
	private List<Movie> vertici;
	private Graph<Movie,DefaultWeightedEdge> grafo;
	private Map<Integer, Movie> mappa;
	private List<Arco> archi;
	private List<Movie> best;
	private int max;
	
	public Model() {
		super();
		this.dao = new ImdbDAO();
		this.vertici = dao.getMovies();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.mappa = new HashMap<>();
	}
	public List<Movie> getMovies(){
		return this.vertici;
	}
	
	public void creaGrafo(double r) {
		Graphs.addAllVertices(this.grafo, vertici);
		for (Movie m : this.grafo.vertexSet()) {
			mappa.put(m.id, m);
		}
		this.archi = dao.getArchi(r,mappa );
		for (Arco a : archi){
			Graphs.addEdgeWithVertices(this.grafo, a.getM1(), a.getM2(), a.getPeso());
		}
	}
	public int getV() {
		return this.grafo.vertexSet().size();
	}
	public int getA() {
		return this.grafo.edgeSet().size();
	}
	
	public Miglioer getMigliore() {
		int max =0;
		Miglioer migliore = null;
		for (Movie m: this.grafo.vertexSet()) {
			//trovo i vicini
			List<Movie> vicini = Graphs.successorListOf(this.grafo, m);
			int peso = calcolaPeso(vicini, m);
			if (peso>max ) {
				max =peso;
				migliore = new Miglioer(m, peso);
			}
		}
		return migliore;
	}
	private int calcolaPeso(List<Movie> vicini, Movie m) {
		int peso =0;
		if (vicini.size()<=1) {
			peso =0;
		}
		
		for (int i =0; i<vicini.size();i++) {
			DefaultWeightedEdge e = this.grafo.getEdge(m, vicini.get(i));
			peso += this.grafo.getEdgeWeight(e);
		}
		return peso;
	}
	
	//cammino aciclico di film più lungo che:  
//	parta da m; 
//	abbia una sequenza di pesi incrementale. Il peso dell’arco esplorato al passo t+1, in particolare, deve 
//	sempre essere maggiore o uguale al peso dell’arco esplorato al passo t. 

	public List<Movie> trovaPercorso(Movie m){
		List<Movie> parziale = new ArrayList<>();
		this.best = new ArrayList<>();
		this.max=0;
		
		List<Movie> vicini= Graphs.neighborListOf(this.grafo, m);
		parziale.add(m);
		int pesoPrecedente =0;
		ricorsione(m, parziale, pesoPrecedente);
		
		
		return this.best;
	}
	private void ricorsione(Movie m, List<Movie> parziale, int pesoPrecedente) {
		Movie corrente = m;
		
		List<Movie> nuoviVicini = Graphs.neighborListOf(this.grafo, corrente);
		//condizione di uscita
		if (nuoviVicini.isEmpty()) {
			if (parziale.size()>=max) {
				this.best = new ArrayList<>(parziale);
				this.max = parziale.size();
			}
			return;
		}
		
		//caso normale
		for (Movie m1: nuoviVicini) {
			//se la lista ha solo un vertice non esiste ancora un arco
			if (parziale.size()<=1) {
				parziale.add(m1);
				ricorsione(m1, parziale,pesoPrecedente);
				parziale.remove(parziale.size()-1);
			}else {
				DefaultWeightedEdge e = this.grafo.getEdge( corrente, m1);
				int peso = (int) this.grafo.getEdgeWeight(e);
				if (peso>=pesoPrecedente) {
					parziale.add(m1);
					ricorsione(m1, parziale, peso);
					parziale.remove(parziale.size()-1);
				}
			}
			
			
		}
	}
	
	
	
	
}
