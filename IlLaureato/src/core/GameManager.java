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

public class GameManager extends GameManagerAstratta {

   private TavolaDiGioco tdg;
   private GestoreTurni gestore;
   private String nomeConfigurazione;

   public GameManager() {
   }

   public String getNomeConfigurazione() {
      return this.nomeConfigurazione;
   }

   public GestoreTurni getGestore() {
      return gestore;
   }

   public int getNumeroGiocatori() {
      return gestore.size();
   }

   public String getNomeGiocatore(int i) {
      return gestore.getGiocatore(i).getNome();
   }

   public ObservableList<Giocatore> getGiocatori() {
      return null;
   }

   public Giocatore getGiocatore(int i) {
      return null;
   }

   public void notificaAlgiocatore(int azione, Giocatore g) {
      System.out.println("Sono in notifica al giocatore");
      setChanged();
      notifyObservers(new Stato(g, azione));
   }

   public GameManagerAstratta init(Giocatore[] nomiGiocatori, int numGiocatori,
         String nomeConfigurazione) throws SQLException {
      this.nomeConfigurazione = nomeConfigurazione;
      tdg = new TavolaDiGioco(this, nomeConfigurazione);
      gestore = new GestoreTurni(nomiGiocatori, numGiocatori);
      return this;
   }

   public void decidiOrdine() {
      gestore.ordinaGiocatori();
   }

   public void turnoSuccessivo(int lancioCorrente) {
      Giocatore corrente = gestore.next();
      int anniAccademiciPrecedentiAlTurno = corrente.getAnniAccademici();
      OutputMediator.println(corrente.getNome() + " lancia i dadi : "
            + corrente.lancia(lancioCorrente));

      // Dopo che il giocatore lancia i dadi la sua posizione viene aggiornata
      // e vengono attivati gli effetti della casella dove si verr� a
      // posizionare
      updatePosizioneGiocatore(corrente);

      if (corrente.getAnniAccademici() > anniAccademiciPrecedentiAlTurno) {
         System.out.println("il giocatore " + corrente.getOrdineDiPartenza()
               + " ha " + corrente.getAnniAccademici());
         setChanged();
         notifyObservers(new Stato(corrente, 5));
      }
      controllaCasella(corrente);

   }

   public void finePartita() {
      // TODO
      // FUNZIONE di fine gioco -> termina il gioco scrivendo in output il
      // vincitore
      OutputMediator.println("FINE PARTITA!");
      Giocatore vincitore = gestore.getVincitore();
      OutputMediator.println("Il VINCITORE E': " + vincitore.getNome() + " CON "
            + vincitore.getCrediti() + " CREDITI E "
            + vincitore.getAnniAccademici() + " ANNI ACCADEMICI!");
   }

   protected void controllaCasella(Giocatore corrente) {

      Casella c = tdg.getCasella(corrente.getPos().getX(),
            corrente.getPos().getY());
      // Si fa partite la funzione action della casella corrente
      c.action(corrente);
   }

   protected void updatePosizioneGiocatore(Giocatore g) {

      if (g.getRisultatoDado() >= tdg.getTavola()[0].length - g.getPos().getY())
         g.getPos().setX((g.getPos().getX() + 1) % tdg.getTavola().length);

      g.getPos().setY((g.getPos().getY() + g.getRisultatoDado())
            % tdg.getTavola()[0].length);

      // questo cambiamento nel gestore fa scattare l'evento di movimento pedina
      gestore.getGiocatori().set(g.getOrdineDiPartenza(), g);

   }
   public void salvaPartita(String file) {
      ObjectOutputStream output = null;

      try {
         output = new ObjectOutputStream(new FileOutputStream(file + ".fap"));

         // Salvataggio dei giocatori
         System.out.println(
               "La size mentre salvo �: " + gestore.getGiocatori().size());
         output.writeObject(new ArrayList<Giocatore>(gestore.getGiocatori()));

         // Salvataggio dei turni
         output.writeObject(gestore.getTurni());

         // Salvataggio variabili: numGiocatori, giocatoreAttuale e
         // statoIniziale
         output.writeObject(gestore.getGiocatori().size());
         output.writeObject(gestore.getGiocatoreAttuale());
         output.writeObject(gestore.getStatoIniziale());
         output.writeObject(nomeConfigurazione);

      }
      catch (IOException e) {
         System.out.println(e);
         System.out.println("Impossibile salvare la partita.");
      }

      try {
         output.close();
      }
      catch (IOException e2) {
         System.out.println(e2);
         System.out.println(
               "Errore riscontrato nell'operazione di chiusura del file.");
      }

      System.out.println("Salvataggio Riuscito");
   }

   @SuppressWarnings("unchecked")
   public void caricaPartita(String partita) {
      ObjectInputStream input = null;

      try {
         input = new ObjectInputStream(new FileInputStream(partita));
      }
      // catturo il null pointer
      catch (NullPointerException e) {
         System.out.println("Nessun file selezionato.");
      }
      catch (FileNotFoundException e) {
         System.out.println(e);
         System.out.println("File non trovato.");
      }
      catch (IOException e) {
         System.out.println(e);
         System.err.println("Impossibile aprire il file.");
      }

      try {

         // Caricamento dei giocatori
         gestore.setGiocatori((ArrayList<Giocatore>) input.readObject());
         System.out.println(
               "La size mentre carico �: " + gestore.getGiocatori().size());

         // Caricamento dei turni
         gestore.setTurni((ArrayList<Integer>) input.readObject());

         // Caricamento variabili: numGiocatori, giocatoreAttuale e
         // statoIniziale
         gestore.setNumeroGiocatori((int) input.readObject());
         gestore.setGiocatoreAttuale((int) input.readObject());
         gestore.setStatoIniziale((boolean) input.readObject());
         this.nomeConfigurazione = (String) input.readObject();

         System.out.println(gestore.getGiocatori().toString());
         System.out.println(gestore.getTurni().toString());
         System.out.println(gestore.size());
         System.out.println(gestore.getGiocatoreAttuale());
         System.out.println(gestore.getStatoIniziale());

      }
      catch (IOException e1) {
         System.err.println("Impossibile caricare la partita.");
      }
      catch (ClassNotFoundException e1) {
         e1.getStackTrace();
         e1.getMessage();
      }

      try {
         input.close();
      }
      catch (IOException e2) {
         System.err.println(
               "Errore riscontrato nell'operazione di chiusura del file.");
      }

      // Allineamento dell'istanza della Tavola da gioco con gli oggetti
      // caricati

      System.out.println("Caricamento Riuscito");
   }

   public void oldPartita(String partita) {
      caricaPartita(partita);
      // OutputMediator.caricaGUI();
      setChanged();
      notifyObservers(new Stato(null, new Integer(0)));
   }

   public TavolaDiGioco getTavolaDiGioco() {
      return this.tdg;
   }

   public String toString() {

      StringBuffer sb = new StringBuffer();

      for (int i = 0; i < tdg.getTavola().length; i++) {
         for (int j = 0; j < tdg.getTavola()[0].length; j++)
            sb.append(tdg.getCasella(i, j) + " ");
         sb.append("\n");
      }
      return sb.toString();
   }

}
