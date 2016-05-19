package core;

public abstract class CasellaAstratta implements Casella {

	protected AzioneAstratta azione;
	protected String nome;

	public CasellaAstratta( AzioneAstratta azione, String nome ){

		this.azione = azione;
		this.nome = nome;

	}

	public String toString(){

		return this.nome;

	}

	public AzioneAstratta getAzione(){

		return this.azione;
	}

	public void setAzione( AzioneAstratta azione ){

		this.azione = azione;
	}

	public abstract void action( Giocatore g );

}
