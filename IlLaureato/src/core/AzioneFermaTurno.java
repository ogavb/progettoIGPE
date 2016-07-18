package core;

import networking.GameManagerNetwork;

public class AzioneFermaTurno extends AzioneAstratta {

	private int numTurni;

	public AzioneFermaTurno( GameManagerAstratta gm, int numTurni ) {

		super(gm);

		this.numTurni = numTurni;
	}

	public void esegui( Giocatore g ) {

		gm.getGestore().modificaTurni( g, numTurni );
		if( gm instanceof GameManagerNetwork)
			((GameManagerNetwork)gm).setYourRound(false);

	}

	public int getNumTurni() {

		return numTurni;
	}

	public void setNumTurni( int numTurni ) {

		this.numTurni = numTurni;
	}

}
