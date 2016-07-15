package core;

import gui.panels.OutputMediator;

public class CasellaMensa extends CasellaAstratta{

	public CasellaMensa( AzioneAstratta azione, String nome ){

		super( azione, nome );
	}

	public void action( Giocatore g ){

		/*
		 * ferma il giocatore di un turno
		 */
		OutputMediator.println( "Sei a mensa! ");
		azione.esegui(g);
	}

}
