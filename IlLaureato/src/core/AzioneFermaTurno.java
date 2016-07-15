package core;

public class AzioneFermaTurno extends AzioneAstratta {

	private int numTurni;

	public AzioneFermaTurno( GameManagerAstratta gm, int numTurni ) {

		super(gm);

		this.numTurni = numTurni;
	}

	public void esegui( Giocatore g ) {

		gm.getGestore().modificaTurni( g, numTurni );

	}

	public int getNumTurni() {

		return numTurni;
	}

	public void setNumTurni( int numTurni ) {

		this.numTurni = numTurni;
	}

}
