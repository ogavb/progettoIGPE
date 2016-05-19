package core;

public class Stato {

	private Giocatore giocatore;
	private Azione azione;
	private int scelta;

	public Stato(Giocatore giocatore,int scelta){
		this.giocatore = giocatore;
		this.scelta = scelta;
	}

	public Stato(Azione a){
		this.azione = a;
	}

	public Stato(int scelta){
		this.scelta = scelta;
	}

	public Giocatore getGiocatore(){
		return this.giocatore;
	}

	public int getScelta(){
		return this.scelta;
	}

	public Azione getAzione() {
		return azione;
	}

}
