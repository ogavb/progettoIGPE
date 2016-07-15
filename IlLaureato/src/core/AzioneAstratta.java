package core;

import core.Giocatore;

public abstract class AzioneAstratta implements Azione{

	protected GameManagerAstratta gm;

	public AzioneAstratta(GameManagerAstratta gm){

		super();

		this.gm = gm;
	}

	public abstract void esegui( Giocatore g );

}
