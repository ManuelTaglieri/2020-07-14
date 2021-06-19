package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class Simulatore {
	
	//mondo
	private Map<Team, Integer> reporter;
	private int numPartite;
	
	//input
	private int x;
	private PriorityQueue<Match> partite;
	private HashMap<Integer, Team> squadre;
	private Model model;
	
	//output
	private double repMedi;
	private int partiteCritiche;
	
	public void init(int n, int x, List<Match> partite, Map<Integer, Team> idMap, Model modello) {
		
		this.x = x;
		this.model = modello;
		this.partite = new PriorityQueue<Match>();
		this.squadre = new HashMap<>(idMap);
		this.reporter = new HashMap<>();
		
		for (Match m : partite) {
			this.partite.add(m);
		}
		
		this.repMedi = 0;
		this.numPartite = partite.size();
		
		for (Team t : idMap.values()) {
			reporter.put(t, n);
		}
		
	}
	
	public void sim() {
		
		while (!this.partite.isEmpty()) {
			Match m = this.partite.poll();
			int repMatch = this.reporter.get(this.squadre.get(m.getTeamHomeID()))+this.reporter.get(this.squadre.get(m.getTeamAwayID()));
			this.repMedi += (repMatch);
			if (repMatch<x) {
				this.partiteCritiche++;
			}
			
			if (m.getResultOfTeamHome()==1) {
				Team vincente = this.squadre.get(m.getTeamHomeID());
				Team perdente = this.squadre.get(m.getTeamAwayID());
				double casuale1 = Math.random();
				if (casuale1<0.5) {
					List<Team> listaMig = this.model.getMigliori(vincente);
					if (!listaMig.isEmpty()) {
						Random r1 = new Random();
						int randomIndex1 = r1.nextInt(listaMig.size());
						Team t = listaMig.get(randomIndex1);
						reporter.put(t, reporter.get(t)+1);
						reporter.put(vincente, reporter.get(vincente)-1);
					}
				}
				double casuale2 = Math.random();
				if (casuale2<0.2) {
					List<Team> listaPeg = this.model.getPeggiori(perdente);
					if (!listaPeg.isEmpty()) {
						Random r2 = new Random();
						int randomIndex2 = r2.nextInt(listaPeg.size());
						Team t = listaPeg.get(randomIndex2);
						Random r3 = new Random();
						if (reporter.get(perdente)>1) {
						int caso = r3.ints(1, reporter.get(perdente)+1).findFirst().getAsInt();
						reporter.put(t, reporter.get(t)+caso);
						reporter.put(perdente, reporter.get(perdente)-caso);
						} else {
						reporter.put(t, reporter.get(t)+1);
						reporter.put(perdente, reporter.get(perdente)-1);
						}
					}
				}
			} else if (m.getResultOfTeamHome()==-1) {
				Team vincente = this.squadre.get(m.getTeamAwayID());
				Team perdente = this.squadre.get(m.getTeamHomeID());
				double casuale1 = Math.random();
				if (casuale1<0.5) {
					List<Team> listaMig = this.model.getMigliori(vincente);
					if (!listaMig.isEmpty()) {
						Random r1 = new Random();
						int randomIndex1 = r1.nextInt(listaMig.size());
						Team t = listaMig.get(randomIndex1);
						reporter.put(t, reporter.get(t)+1);
						reporter.put(vincente, reporter.get(vincente)-1);
					}
				}
				double casuale2 = Math.random();
				if (casuale2<0.2) {
					List<Team> listaPeg = this.model.getPeggiori(perdente);
					if (!listaPeg.isEmpty()) {
						Random r2 = new Random();
						int randomIndex2 = r2.nextInt(listaPeg.size());
						Team t = listaPeg.get(randomIndex2);
						Random r3 = new Random();
						if (reporter.get(perdente)>1) {
						int caso = r3.ints(1, reporter.get(perdente)+1).findFirst().getAsInt();
						reporter.put(t, reporter.get(t)+caso);
						reporter.put(perdente, reporter.get(perdente)-caso);
						} else {
						reporter.put(t, reporter.get(t)+1);
						reporter.put(perdente, reporter.get(perdente)-1);
						}
					}
				}
			}
		}
		
	}

	public double getRepMedi() {
		repMedi = repMedi/numPartite;
		return repMedi;
	}

	public int getPartiteCritiche() {
		return partiteCritiche;
	}

}
