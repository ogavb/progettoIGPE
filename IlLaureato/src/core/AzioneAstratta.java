package core;

import core.Giocatore;

public abstract class AzioneAstratta implements Azione{

	protected GameManager gm;

	public AzioneAstratta(GameManager gm){

		super();

		this.gm = gm;
	}

	public abstract void esegui( Giocatore g );

}
