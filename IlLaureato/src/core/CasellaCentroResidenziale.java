package core;

import gui.panels.OutputMediator;

public class CasellaCentroResidenziale extends CasellaAstratta {

	public CasellaCentroResidenziale( AzioneAstratta azione, String nome ){

		super( azione, nome );
	}

	public void action( Giocatore g ){

		/*
		 * blocca il giocatore 2 turni
		 */
		OutputMediator.println( "Sei al centro residenziale! ");
		azione.esegui(g);
	}

}
