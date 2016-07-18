package networking;

import java.sql.SQLException;

import core.Casella;
import core.GameManagerAstratta;
import core.GestoreTurni;
import core.Giocatore;
import core.Stato;
import core.TavolaDiGioco;
import gui.panels.OutputMediator;
import javafx.collections.ObservableList;

public class GameManagerNetwork extends GameManagerAstratta {

	private TavolaDiGioco tdg;
	private GestoreTurni gestore;
	private boolean direzione = true;
	private String nomeConfigurazione;
	private boolean isYourRound = false;

	private Client client;

	public GameManagerNetwork(){}

	public void setClient(Client client){
		this.client = client;
	}

	@Override
	public String getNomeConfigurazione() {
		return this.nomeConfigurazione;
	}

	@Override
	public boolean getDirezione() {
		return direzione;
	}

	@Override
	public void setDirezione(boolean direzione) {
		this.direzione = direzione;
	}

	@Override
	public GestoreTurni getGestore() {
		return gestore;
	}

	@Override
	public int getNumeroGiocatori() {
		return gestore.size();
	}

	public ObservableList < Giocatore > getGiocatori(){
		return gestore.getGiocatori();
	}

	public Giocatore getGiocatore(int i){
		return gestore.getGiocatore(i);
	}

	@Override
	public String getNomeGiocatore(int i) {
		return gestore.getGiocatore(i).getNome();
	}

	@Override
	public void notificaAlgiocatore(int azione, Giocatore g) {

	}

	public void setOrdinaGiocatori(Giocatore[] g){
		gestore.setOrdinaGiocatori(g);
	}

	@Override
	public GameManagerAstratta init(Giocatore[] nomiGiocatori, int numGiocatori, String nomeConfigurazione)
			throws SQLException {

		this.nomeConfigurazione = nomeConfigurazione;
		tdg = new TavolaDiGioco(this,nomeConfigurazione);
		gestore = new GestoreTurni( nomiGiocatori, numGiocatori );
		return this;
	}

	@Override
	public void decidiOrdine() {
		gestore.ordinaGiocatori();
	}

	@Override
	public void turnoSuccessivo(int lancioCorrente) {

		this.setYourRound(false);
		this.client.addRequest("13##"+gestore.getNextPlayer(client.getNomeGiocatore())+"##10");

		Giocatore corrente = gestore.next();
	   	int anniAccademici = corrente.getAnniAccademici();

	   	client.addRequest("12##11#"+String.valueOf(lancioCorrente));

	   	OutputMediator.println(corrente.getNome() +" lancia i dadi : "+ corrente.lancia(lancioCorrente));

		//Dopo che il giocatore lancia i dadi la sua posizione viene aggiornata e vengono attivati gli effetti della casella dove si verrà a posizionare
   		updatePosizioneGiocatore(corrente);
   		//client.addRequest("12##12#"+String.valueOf(lancioCorrente));
   		if(corrente.getAnniAccademici() > anniAccademici ){
   			System.out.println("il giocatore "+corrente.getOrdineDiPartenza() + " ha "+corrente.getAnniAccademici());
   			System.out.println("Anni accademici cambiati");
   			setChanged();
   			notifyObservers(new Stato(corrente,5));
	   		}

	  	controllaCasella(corrente);
	}

	@Override
	public void finePartita() {
		OutputMediator.println("Fine partita!");
		Giocatore vincitore = gestore.getVincitore();
		OutputMediator.println("Il vincitore è: "+ vincitore.getNome() +" con "+ vincitore.getCrediti() +" crediti e con "+ vincitore.getAnniAccademici() +" anni accademici!");
	}

	protected void controllaCasella( Giocatore corrente ){

		Casella c = tdg.getCasella(corrente.getPos().getX(), corrente.getPos().getY());
		//Si fa partite la funzione action della casella corrente
		c.action( corrente );
	}

	protected void updatePosizioneGiocatore( Giocatore g ){

		OutputMediator.println( "Si sposta da: "+ g.getPos().getX() + "  " + g.getPos().getY() );

		if( g.getRisultatoDado() >= tdg.getTavola()[0].length - g.getPos().getY() )
			g.getPos().setX( ( g.getPos().getX() + 1 ) % tdg.getTavola().length );

		g.getPos().setY( ( g.getPos().getY() + g.getRisultatoDado() )
								  % tdg.getTavola()[0].length );


		gestore.getGiocatori().set(g.getOrdineDiPartenza(), g);

		OutputMediator.println( "A: "+ g.getPos().getX() + "  " + g.getPos().getY() );
	}

	protected void updatePosizioneGiocatoreIndietro( Giocatore g ){

		OutputMediator.println( "Si sposta da: "+ g.getPos().getX() + "  " + g.getPos().getY() );

		if ( g.getRisultatoDado() > g.getPos().getY() )
		{
	       if ( g.getPos().getX() > 0 )  // se non passo dal via ( al contrario )
				 g.getPos().setX(g.getPos().getX() -1 );
		   else
		   {
		      g.getPos().setX(tdg.getTavola().length -1);
		   }
	       g.getPos().setY(tdg.getTavola()[0].length - ( g.getRisultatoDado() - g.getPos().getY() ) );
		}
	    else
	    	g.getPos().setY( g.getPos().getY() - g.getRisultatoDado() ) ;

		gestore.getGiocatori().set(g.getOrdineDiPartenza(), g);

		OutputMediator.println( "A: "+ g.getPos().getX() + "  " + g.getPos().getY() );

	}

	@Override
	public void salvaPartita(String file) {

	}

	@Override
	public void caricaPartita(String partita) {

	}

	@Override
	public void oldPartita(String partita) {

	}

	@Override
	public TavolaDiGioco getTavolaDiGioco() {
		return this.tdg;
	}

	public String toString(){

		StringBuffer sb = new StringBuffer();

		for ( int i = 0; i < tdg.getTavola().length; i++ ) {
			for ( int j = 0; j < tdg.getTavola()[0].length; j++ )
				sb.append(tdg.getCasella(i, j) + " ");
			sb.append("\n");
		}
		return sb.toString();
	}

	public boolean isYourRound() {

		return isYourRound;

	}

	public void setYourRound(boolean b){
		this.isYourRound = b;
	}

}
