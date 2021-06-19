package it.polito.tdp.PremierLeague.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Graph<Team, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Team, Integer> classifica;
	private Map<Integer, Team> idMap;
	private Simulatore sim;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo() {
		
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.idMap = new HashMap<>();
		this.dao.listAllTeams(idMap);
		
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		this.classifica = new HashMap<>();
		for (Team t : idMap.values()) {
			this.classifica.put(t, 0);
		}
		this.dao.calcolaClassifica(classifica, idMap);
		
		for (Team t1 : idMap.values()) {
			for (Team t2 : idMap.values()) {
				int differenza = classifica.get(t1) - classifica.get(t2);
				if (differenza>0) {
					Graphs.addEdge(this.grafo, t1, t2, differenza);
				} else if (differenza<0) {
					Graphs.addEdge(this.grafo, t2, t1, (-1)*differenza);
				}
			}
		}
		
		for (Team t : idMap.values()) {
			t.setPunti(classifica.get(t));
		}
		
	}
	
	public int getVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Collection<Team> getSquadre() {
		return idMap.values();
	}
	
	public Map<Team, Integer> getClassifica() {
		return classifica;
	}

	public List<Team> getPeggiori(Team t) {
		int puntiT = classifica.get(t);
		List<Team> risultato = new LinkedList<>();
		
		for (Team squadra : idMap.values()) {
			if (classifica.get(squadra)<puntiT) {
				risultato.add(squadra);
			}
		}
		Collections.sort(risultato, new Comparator<Team>() {

			@Override
			public int compare(Team o1, Team o2) {
				return o2.getPunti()-o1.getPunti();
			}
			
		});
		
		return risultato;
	}
	
	public List<Team> getMigliori(Team t) {
		int puntiT = classifica.get(t);
		List<Team> risultato = new LinkedList<>();
		
		for (Team squadra : idMap.values()) {
			if (classifica.get(squadra)>puntiT) {
				risultato.add(squadra);
			}
		}
		Collections.sort(risultato, new Comparator<Team>() {

			@Override
			public int compare(Team o1, Team o2) {
				return o1.getPunti()-o2.getPunti();
			}
			
		});
		
		return risultato;
	}
	
	public void simula(int n, int x) {
		this.sim = new Simulatore();
		this.sim.init(n, x, this.dao.listAllMatches(), this.idMap, this);
		this.sim.sim();
	}
	
	public double getRepMedi() {
		return this.sim.getRepMedi();
	}
	
	public int getPartiteCritiche() {
		return this.sim.getPartiteCritiche();
	}
	
}
