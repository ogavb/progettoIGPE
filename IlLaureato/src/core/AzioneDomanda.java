package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import gui.panels.OutputMediator;
import jdbc.PrelevatoreDomanda;
import model.Risposta;

public class AzioneDomanda extends AzioneAstratta {

	private String domanda;
	private Risposta ris;
	private String rispostaEsatta;
	private int crediti;

	public AzioneDomanda(GameManager gm) {

		super(gm);

		this.ris = new Risposta();

	}

	public String getRispostaEsatta(){
		return 	rispostaEsatta;
	}

	public void setRispostaEsatta( String s){
		this.rispostaEsatta = s;
	}

	public int getCrediti(){
		return 	crediti;
	}

	public void setCrediti	( int s){
		this.crediti = s;
	}


	public String getDomanda() {

		return domanda;
	}

	public void setDomanda(String domanda) {

		this.domanda = domanda;
	}


	public Risposta getRisposte() {

		return ris;
	}

	public void setRisposte(Risposta ris) {

		this.ris = ris;
	}

	@Override
	public void esegui(Giocatore g) {

		PrelevatoreDomanda database = new PrelevatoreDomanda(this);
		database.query();
	}

	public void controllaEsitoEsame( String risposta, Giocatore g ) {

		String rispostaGiocatore = risposta;

		if( rispostaGiocatore.equals( rispostaEsatta ) ){
				OutputMediator.println( "Risposta corretta! ");
				g.aggiornaCrediti( crediti );
				return;
			}
			OutputMediator.println( "Risposta errata! " );
			return;
		}

}
