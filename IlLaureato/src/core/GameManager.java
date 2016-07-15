package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import gui.panels.OutputMediator;
import javafx.collections.ObservableList;

public class GameManager extends GameManagerAstratta{

	private TavolaDiGioco tdg;
	private GestoreTurni gestore;
	private boolean direzione = true;
	private String nomeConfigurazione;

	public GameManager(){}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#getNomeConfigurazione()
	 */
	public String getNomeConfigurazione(){
		return this.nomeConfigurazione;
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#getDirezione()
	 */
	public boolean getDirezione(){
		return direzione;
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#setDirezione(boolean)
	 */
	public void setDirezione(boolean direzione){
		this.direzione = direzione;
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#getGestore()
	 */
	public GestoreTurni getGestore(){
		return gestore;
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#getNumeroGiocatori()
	 */
	public int getNumeroGiocatori(){
		return gestore.size();
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#getNomeGiocatore(int)
	 */
	public String getNomeGiocatore( int i ){
		return gestore.getGiocatore(i).getNome();
	}

	public ObservableList < Giocatore > getGiocatori(){
		return null;
	}

	public Giocatore getGiocatore(int i){
		return null;
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#notificaAlgiocatore(int, core.Giocatore)
	 */
	public void notificaAlgiocatore(int azione, Giocatore g){
		System.out.println("Sono in notifica al giocatore");
		setChanged();
		notifyObservers(new Stato(g,azione));
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#init(core.Giocatore[], int, java.lang.String)
	 */
	public GameManagerAstratta init( Giocatore[] nomiGiocatori, int numGiocatori, String nomeConfigurazione ) throws SQLException {
			this.nomeConfigurazione = nomeConfigurazione;
			tdg = new TavolaDiGioco(this,nomeConfigurazione);
			gestore = new GestoreTurni( nomiGiocatori, numGiocatori );
			return this;
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#start()
	 */
	public void start(){
		OutputMediator.println("Inizio Partita!");
		if(gestore.size() == 1) {
			finePartita();
		}
	}

	//Funzione che dovr� sostituire ordinaGiocatori del gestore turni in modo da controllollare
	//il lancio dei dadi e lasciare quest'azione all'utente dall'interfaccia (eliminando il ciclo for)
	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#decidiOrdine()
	 */
	public void decidiOrdine() {
		gestore.ordinaGiocatori();
	}

	//Funzione deve essere chiamata solo dopo aver chiamato prima la funzione start()
	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#turnoSuccessivo(int)
	 */
	public int turnoSuccessivo(int numGiocatori){
	   	Giocatore corrente = gestore.next();
	   	int anniAccademici = corrente.getAnniAccademici();

	   	OutputMediator.println(corrente.getNome() +" lancia i dadi : "+ corrente.lancia());
	   	setChanged();
	   	notifyObservers(new Stato(corrente, new Integer(1)));

//	   	setChanged();
//	   	notifyObservers(new Stato(4));

		//Dopo che il giocatore lancia i dadi la sua posizione viene aggiornata e vengono attivati gli effetti della casella dove si verr� a posizionare
	   	if(direzione){
	   		updatePosizioneGiocatore(corrente);
	   		if(corrente.getAnniAccademici() > anniAccademici ){
	   			System.out.println("il giocatore "+corrente.getOrdineDiPartenza() + " ha "+corrente.getAnniAccademici());
	   			System.out.println("Anni accademici cambiati");
	   			setChanged();
	   			notifyObservers(new Stato(corrente,5));
	   		}

	   	}
	   	else{
	   		//OutputMediator.println( corrente.getNome() +" si deve muovere indietro di "+ corrente.getRisultatoDado() );
	   		updatePosizioneGiocatoreIndietro(corrente);
	   	}

	  	//OutputMediator.println( corrente.getPos().getX() + "  " + corrente.getPos().getY() );
	  	controllaCasella(corrente);

		//I giocatori che raggiungono 180 crediti vengono eliminati dal gioco
	  	if( corrente.getCrediti() >= 180 ){
	  		OutputMediator.println("Il giocatore: "+ corrente.getNome() + " si � laureato!!._.\nQuindi verr� per sempre escluso dall'universit�!");
	  		gestore.rimuovi(corrente);
	  		numGiocatori--;
	  		//TODO
	  		//FUNZIONE che avvisa l'uscita del giocatore

	  		//Verifico se ci sono altri giocatori
	  		if(numGiocatori == 1){
	  			return numGiocatori;
	  		}
	  	}

		return numGiocatori;
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#finePartita()
	 */
	public void finePartita(){
		//TODO
		//FUNZIONE di fine gioco -> termina il gioco scrivendo in output il vincitore
		OutputMediator.println("Fine partita!");
		Giocatore vincitore = gestore.getVincitore();
		OutputMediator.println("Il vincitore �: "+ vincitore.getNome() +" con "+ vincitore.getCrediti() +" crediti e con "+ vincitore.getAnniAccademici() +" anni accademici!");
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

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#salvaPartita(java.lang.String)
	 */
	public void salvaPartita(String file)
	{
		ObjectOutputStream output = null;

		try
		{
			output = new ObjectOutputStream(new FileOutputStream(file+".fap"));

			//Salvataggio dei giocatori
			System.out.println("La size mentre salvo �: " + gestore.getGiocatori().size());
			output.writeObject(new ArrayList<Giocatore>(gestore.getGiocatori()));

			//Salvataggio dei turni
			output.writeObject(gestore.getTurni());

			//Salvataggio variabili: numGiocatori, giocatoreAttuale e statoIniziale
			output.writeObject(gestore.getGiocatori().size());
			output.writeObject(gestore.getGiocatoreAttuale());
			output.writeObject(gestore.getStatoIniziale());
			output.writeObject(nomeConfigurazione);

		}
		catch (IOException e)
		{
			System.out.println(e);
			System.out.println("Impossibile salvare la partita.");
		}

		try {output.close();}
		catch (IOException e2)
		{
			System.out.println(e2);
			System.out.println("Errore riscontrato nell'operazione di chiusura del file.");
		}

		System.out.println("Salvataggio Riuscito");
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#caricaPartita(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void caricaPartita(String partita){
		ObjectInputStream input = null;

		try
		{
			input = new ObjectInputStream(new FileInputStream(partita));
		}
		//catturo il null pointer
		catch(NullPointerException e){
			System.out.println("Nessun file selezionato.");
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e);
			System.out.println("File non trovato.");
		}
		catch (IOException e)
		{
			System.out.println(e);
			System.err.println("Impossibile aprire il file.");
		}

		try
		{

			// Caricamento dei giocatori
			gestore.setGiocatori((ArrayList<Giocatore>) input.readObject());
			System.out.println("La size mentre carico �: " + gestore.getGiocatori().size());

			// Caricamento dei turni
			gestore.setTurni((ArrayList<Integer>)input.readObject());

			// Caricamento variabili: numGiocatori, giocatoreAttuale e statoIniziale
			gestore.setNumeroGiocatori((int)input.readObject());
			gestore.setGiocatoreAttuale((int)input.readObject());
			gestore.setStatoIniziale((boolean)input.readObject());
			this.nomeConfigurazione = (String) input.readObject();

			System.out.println(gestore.getGiocatori().toString());
			System.out.println(gestore.getTurni().toString());
			System.out.println(gestore.size());
			System.out.println(gestore.getGiocatoreAttuale());
			System.out.println(gestore.getStatoIniziale());

		}
		catch (IOException e1)
		{
			System.err.println("Impossibile caricare la partita.");
		}
		catch (ClassNotFoundException e1)
		{
			e1.getStackTrace();
			e1.getMessage();
		}

		try {input.close();}
		catch (IOException e2)
		{
			System.err.println("Errore riscontrato nell'operazione di chiusura del file.");
		}

		//Allineamento dell'istanza della Tavola da gioco con gli oggetti caricati

		System.out.println("Caricamento Riuscito");
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#oldPartita(java.lang.String)
	 */
	public void oldPartita(String partita){
		caricaPartita(partita);
		//OutputMediator.caricaGUI();
		setChanged();
		notifyObservers(new Stato(null, new Integer(0)));
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#getTavolaDiGioco()
	 */
	public TavolaDiGioco getTavolaDiGioco(){
		return this.tdg;
	}

	/* (non-Javadoc)
	 * @see core.InterfacciaGameManager#toString()
	 */
	public String toString(){

		StringBuffer sb = new StringBuffer();

		for ( int i = 0; i < tdg.getTavola().length; i++ ) {
			for ( int j = 0; j < tdg.getTavola()[0].length; j++ )
				sb.append(tdg.getCasella(i, j) + " ");
			sb.append("\n");
		}
		return sb.toString();
	}

}
