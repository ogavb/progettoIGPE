package core;

import java.sql.SQLException;
import java.util.Observable;

public class GameManagerAstratta extends Observable implements InterfacciaGameManager{

	@Override
	public String getNomeConfigurazione() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getDirezione() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDirezione(boolean direzione) {
		// TODO Auto-generated method stub

	}

	@Override
	public GestoreTurni getGestore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumeroGiocatori() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNomeGiocatore(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notificaAlgiocatore(int azione, Giocatore g) {
		// TODO Auto-generated method stub

	}

	@Override
	public InterfacciaGameManager init(Giocatore[] nomiGiocatori, int numGiocatori, String nomeConfigurazione)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void decidiOrdine() {
		// TODO Auto-generated method stub

	}

	@Override
	public int turnoSuccessivo(int numGiocatori) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void finePartita() {
		// TODO Auto-generated method stub

	}

	@Override
	public void salvaPartita(String file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void caricaPartita(String partita) {
		// TODO Auto-generated method stub

	}

	@Override
	public void oldPartita(String partita) {
		// TODO Auto-generated method stub

	}

	@Override
	public TavolaDiGioco getTavolaDiGioco() {
		// TODO Auto-generated method stub
		return null;
	}

}
