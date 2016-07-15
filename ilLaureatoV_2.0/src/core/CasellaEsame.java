package core;

public class CasellaEsame extends CasellaAstratta {

	public CasellaEsame( AzioneAstratta azione, String nome ){

		super( azione, nome );

	}

	public void action( Giocatore g ){

		azione.esegui(g);

	}


}
