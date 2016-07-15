package core;

import java.sql.SQLException;

public interface InterfacciaGameManager {

	String getNomeConfigurazione();

	boolean getDirezione();

	void setDirezione(boolean direzione);

	GestoreTurni getGestore();

	int getNumeroGiocatori();

	String getNomeGiocatore(int i);

	void notificaAlgiocatore(int azione, Giocatore g);

	InterfacciaGameManager init(Giocatore[] nomiGiocatori, int numGiocatori, String nomeConfigurazione) throws SQLException;

	void start();

	void decidiOrdine();

	int turnoSuccessivo(int numGiocatori);

	void finePartita();

	void salvaPartita(String file);

	void caricaPartita(String partita);

	void oldPartita(String partita);

	TavolaDiGioco getTavolaDiGioco();

	String toString();

}