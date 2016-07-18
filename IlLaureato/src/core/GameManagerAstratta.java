package core;

import java.sql.SQLException;
import java.util.Observable;

import javafx.collections.ObservableList;
import networking.Client;

public class GameManagerAstratta extends Observable {

	public String getNomeConfigurazione() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getDirezione() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDirezione(boolean direzione) {
		// TODO Auto-generated method stub

	}

	public void setOrdinaGiocatori(Giocatore[] g){

	}

	public GestoreTurni getGestore() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumeroGiocatori() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getNomeGiocatore(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public ObservableList < Giocatore > getGiocatori(){
		return null;
	}

	public Giocatore getGiocatore(int i){
		return null;
	}

	public void notificaAlgiocatore(int azione, Giocatore g) {
		// TODO Auto-generated method stub

	}

	public Client getClient(){
		return null;
	}

	public GameManagerAstratta init(Giocatore[] nomiGiocatori, int numGiocatori, String nomeConfigurazione)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void decidiOrdine() {
		// TODO Auto-generated method stub

	}

	public void turnoSuccessivo(int lancioCorrente) {
		// TODO Auto-generated method stub
	}

	public void finePartita() {
		// TODO Auto-generated method stub

	}

	public void salvaPartita(String file) {
		// TODO Auto-generated method stub

	}

	public void caricaPartita(String partita) {
		// TODO Auto-generated method stub

	}

	public void oldPartita(String partita) {
		// TODO Auto-generated method stub

	}

	public TavolaDiGioco getTavolaDiGioco() {
		// TODO Auto-generated method stub
		return null;
	}

}
