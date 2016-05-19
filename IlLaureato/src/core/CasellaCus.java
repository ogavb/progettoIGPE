package core;

import gui.panels.OutputMediator;

public class CasellaCus extends CasellaAstratta{

	public CasellaCus( AzioneAstratta azione, String nome ){

		super( azione, nome );
	}

	public void action( Giocatore g ){

		OutputMediator.println( "Sei al cus! ");
		azione.esegui(g);
	}

}
