package core;

import gui.panels.OutputMediator;
import jdbc.PrelevatoreDomanda;
import model.Risposta;
import networking.GameManagerNetwork;

public class AzioneDomanda extends AzioneAstratta {

   private int codiceEsame;
   private String domanda;
   private Risposta ris;
   private String rispostaEsatta;
   private int crediti;

   public AzioneDomanda(GameManagerAstratta gm) {

      super(gm);

      this.ris = new Risposta();

   }

   public String getRispostaEsatta() {
      return rispostaEsatta;
   }

   public void setRispostaEsatta(String s) {
      this.rispostaEsatta = s;
   }

   public int getCrediti() {
      return crediti;
   }

   public void setCrediti(int s) {
      this.crediti = s;
   }

   public String getDomanda() {

      return domanda;
   }

   public void setDomanda(String domanda) {

      this.domanda = domanda;
   }

   public Risposta getRisposte() {

      return ris;
   }

   public void setRisposte(Risposta ris) {

      this.ris = ris;
   }

   @Override
   public void esegui(Giocatore g) {

      if (gm instanceof GameManagerNetwork
            && ((GameManagerNetwork) gm).isYourRound()) {
         PrelevatoreDomanda database = new PrelevatoreDomanda(this);
         database.query();
         ((GameManagerNetwork) gm).inviaEsame("12##14#" + domanda + ","
               + ris.getRisposte()[0] + "," + ris.getRisposte()[1] + ","
               + rispostaEsatta + "," + crediti);
      }
      if (gm instanceof GameManagerNetwork
            && !((GameManagerNetwork) gm).isRequestActive())
         ((GameManagerNetwork) gm).setYourRound(false);

      if (gm instanceof GameManager) {
         PrelevatoreDomanda database = new PrelevatoreDomanda(this);
         database.query2(g.getEsamiSvolti());
      }

   }

   public boolean controllaEsitoEsame(String risposta, Giocatore g) {
      String rispostaGiocatore = risposta;

      //questo per il giocoInRETE TO-DO
      if( gm instanceof GameManagerNetwork)
      ((GameManagerNetwork) gm).inviaLabelRisposta(risposta);

      if (rispostaGiocatore.equals("")) {
         OutputMediator.println("Hai finito il tempo!!\nCrediti aggiornati!!");
         g.inserisciEsameSvolto(this.getCodiceEsame());

         // aggiorna i crediti dal punto di vista logico
         g.aggiornaCrediti(crediti);
         // aggiorna i crediti dal punto di vista grafico
         gm.notificaAlgiocatore(5, g);

         return true;
      }
      if (rispostaGiocatore.equals(rispostaEsatta)) {
         OutputMediator.println("Risposta corretta!");
         OutputMediator.println("IlGiocatore guadagna "+crediti+" crediti!");
         g.inserisciEsameSvolto(this.getCodiceEsame());
         System.err.println("inserisco l'esame con codice: "+this.getCodiceEsame());
         g.aggiornaCrediti(crediti);
         gm.notificaAlgiocatore(5, g);
         return true;
      }
      g.inserisciEsameSvolto(this.getCodiceEsame());
      OutputMediator.println("Risposta errata!\nContinua cosi!!");
      return false;

   }

   public int getCodiceEsame() {
      return codiceEsame;
   }

   public void setCodiceEsame(int codice ) {
      this.codiceEsame = codice;
   }

}
