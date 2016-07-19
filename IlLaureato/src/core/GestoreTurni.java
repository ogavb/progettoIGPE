package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import gui.panels.OutputMediator;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class GestoreTurni extends Observable implements Serializable {
	private static final long serialVersionUID = 1L;

	private ObservableList<Giocatore> giocatori;
	private List<Integer> turni;
	private int numGiocatori;

	// Indice del giocatore a cui tocca lanciare i dadi
	private int giocatoreAttuale;

	private boolean statoIniziale;
	private Giocatore[] players;
	private int count;
	private boolean controlla;

	public GestoreTurni(Giocatore[] players, int numGiocatori) {
		this.giocatori = FXCollections.observableArrayList();

		giocatori.addListener(new ListChangeListener<Giocatore>() {

			@Override
			public void onChanged(Change<? extends Giocatore> c) {
				statoIniziale = false;
				c.next();
				if (!statoIniziale) {
					if (c.wasReplaced()) {
						Giocatore spostato = c.getRemoved().get(0);
						setChanged();
						notifyObservers(new Stato(spostato, new Integer(2)));
						System.out.println(spostato);
					}
					else if (c.wasRemoved() && controlla) {
						Giocatore rimosso = c.getRemoved().get(0);
						setChanged();
						notifyObservers(new Stato(rimosso, new Integer(3)));
					}
				}
			}
		});

		this.count = 0;
		this.controlla = true;
		this.numGiocatori = numGiocatori;
		this.players = players;

		// Inizializzo l'arrayList dei turni
		this.turni = new ArrayList<Integer>();
		for (int i = 0; i < players.length; i++) {
			this.turni.add(new Integer(0));
		}
		this.giocatoreAttuale = players.length - 1;
	}

	public Giocatore next() {
		while (true) {
			giocatoreAttuale++;
			// Se abbiamo finito i giocatori di questo giro di turnazione prendiamo
			// il primo giocatore
			if (giocatoreAttuale >= giocatori.size()) {
				giocatoreAttuale = 0;
			}

			// Se il giocatore non ha penalità
			if (turni.get(giocatoreAttuale) == 0) {
				return giocatori.get(giocatoreAttuale);
			}
			// Se il giocatore deve scontare penalità, passa il turno e ...
			else {
				// qui il giocatore sconta i turni di penalità
				turni.set(giocatoreAttuale, turni.get(giocatoreAttuale) - 1);
				OutputMediator.println(giocatori.get(giocatoreAttuale).getNome()
						+ ", sta fermo per altri " + turni.get(giocatoreAttuale)
						+ " turni!\n");
			}
			// il ciclo while si ripete finché non trova il giocatore che ha il
			// permesso di lanciare i dadi
		}
	}

	/*
	 * ritorna il nome del giocatore successivo a quello passato
	 */
	public String getNextPlayer(String giocatoreCorrente) {
		boolean trovato = false;

		for (Giocatore g : giocatori) {
			if (trovato) {
				return g.getNome();
			}
			if (g.getNome().equals(giocatoreCorrente)) {
				trovato = true;
			}
		}
		if (!trovato) {
			System.err.println("giocatore non trovato GestoreTurni");
		}
		return giocatori.get(0).getNome();

	}

	public Giocatore getVincitore() {
		if (giocatori.size() == 1) {
			return giocatori.get(0);
		}
		return null;
	}

	public int size() {
		return this.numGiocatori;
	}

	// FUNZIONE UTILE PER IL GIOCO IN RETE
	public void setOrdinaGiocatori(Giocatore[] g) {
		for (int i = 0; i < g.length; i++) {
			giocatori.add(g[i]);
		}

	}

	public void setNumeroGiocatori(int numGiocatori) {
		this.numGiocatori = numGiocatori;
	}

	public ObservableList<Giocatore> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(ArrayList<Giocatore> a) {
		controlla = false;
		this.giocatori.clear();

		Iterator<Giocatore> ig = a.iterator();
		while (ig.hasNext()) {
			this.giocatori.add(ig.next());
		}
		controlla = true;
	}

	public List<Integer> getTurni() {
		return turni;
	}

	public void setTurni(List<Integer> turni) {
		this.turni = turni;
	}

	public int getGiocatoreAttuale() {
		return giocatoreAttuale;
	}

	public void setGiocatoreAttuale(int giocatoreAttuale) {
		this.giocatoreAttuale = giocatoreAttuale;
	}

	public boolean getStatoIniziale() {
		return statoIniziale;
	}

	public void setStatoIniziale(boolean statoIniziale) {
		this.statoIniziale = statoIniziale;
	}

	public Giocatore getGiocatore(int i) {
		return this.giocatori.get(i);
	}

	public void rimuovi(Giocatore g) {
		for (int i = g.getOrdineDiPartenza() + 1; i < giocatori.size(); i++) {
			giocatori.get(i)
					.setOrdineDiPartenza(giocatori.get(i).getOrdineDiPartenza() - 1);
		}
		giocatori.remove(g);
		giocatoreAttuale--;

		if (giocatori.size() == 1) {
			System.err.println("");
			setChanged();
			System.err.println(this.getVincitore());
			notifyObservers(new Stato(this.getVincitore(), 4));
		}
	}

	public void ordinaGiocatori() {
		for (int j = 0; j < players.length; j++) {
			count++;
			players[count - 1].lanciaPerOrdine();
			this.giocatori.add(players[count - 1]);

		}
		if (count == this.numGiocatori) {
			Collections.sort(giocatori);

			Iterator<Giocatore> j = giocatori.iterator();
			int cont = 0;
			while (j.hasNext()) {
				Giocatore tmp = j.next();
				tmp.setOrdineDiPartenza(cont);
				cont++;
			}
		}
	}

	// Funzione che modifica i turni che il giocatore deve aspettare
	public void modificaTurni(Giocatore g, int numTurni) {
		turni.set(giocatori.indexOf(g),
				turni.get(giocatori.indexOf(g)) + numTurni);
	}
}