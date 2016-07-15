package model;

import javafx.beans.property.SimpleIntegerProperty;

public class MatchTableView {

	private final SimpleIntegerProperty idMatch;
	private final SimpleIntegerProperty numeroGiocatori;
	private final SimpleIntegerProperty giocatoriCorrenti;

	public MatchTableView(Integer idMatch,Integer numeroGiocatori,Integer giocatoriCorrenti) {
		this.idMatch = new SimpleIntegerProperty(idMatch);
		this.numeroGiocatori = new SimpleIntegerProperty(numeroGiocatori);
		this.giocatoriCorrenti = new SimpleIntegerProperty(giocatoriCorrenti);

	}

	public Integer getIdMatch() {
		return idMatch.get();
	}
	public Integer getNumeroGiocatori() {
		return numeroGiocatori.get();
	}
	public Integer getGiocatoriCorrenti() {
		return giocatoriCorrenti.get();
	}

	public void setIdMatch(Integer idMatch) {
		this.idMatch.setValue(idMatch);
	}

	public void setNumeroGiocatori(Integer numberPlayer) {
		this.numeroGiocatori.setValue(numberPlayer);
	}

	public void setGiocatoriCorrenti(Integer size) {
		this.giocatoriCorrenti.setValue(size);
	}
}
