package core;

import gui.panels.OutputMediator;

public class AzioneMovimento extends AzioneAstratta {
	//Questo valore determina il movimento di un numero casuale di caselle:
	//		true 	== avanti
	//		false	== indietro
	private Boolean direzione;

	public AzioneMovimento( GameManager gm, Boolean direzione ){

		super(gm);
		//ATTENZIONE bisogna mettere non "true" ma "direzione"
		this.direzione = direzione;
		//ATTENZIONE TODO
	}

	public void esegui( Giocatore g ) {

		OutputMediator.println(g.getNome() + " rilancia i dadi");

		if(g.getOrdineDiPartenza() == 0)
			gm.getGestore().setGiocatoreAttuale(gm.getGestore().size()-1);
		else
			gm.getGestore().setGiocatoreAttuale(g.getOrdineDiPartenza()-1);

		gm.setDirezione(direzione);

	}
}
