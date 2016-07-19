package core;

import gui.panels.OutputMediator;
import jdbc.PrelevatoreDomanda;
import model.Risposta;
import networking.GameManagerNetwork;

public class AzioneDomanda extends AzioneAstratta {

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
         database.query();
      }

   }

   public boolean controllaEsitoEsame(String risposta, Giocatore g) {
      String rispostaGiocatore = risposta;
      if( gm instanceof GameManagerNetwork)
      ((GameManagerNetwork) gm).inviaLabelRisposta(risposta);

      if (rispostaGiocatore.equals("")) {
         OutputMediator.println("Hai finito il tempo!! crediti aggiornati");
         // aggiorna i crediti dal punto di vista logico
         g.aggiornaCrediti(crediti);
         // aggiorna i crediti dal punto di vista grafico
         gm.notificaAlgiocatore(5, g);
         return false;
      }

      if (rispostaGiocatore.equals(rispostaEsatta)) {
         OutputMediator.println("Risposta corretta! ");
         g.aggiornaCrediti(crediti);
         gm.notificaAlgiocatore(5, g);
         return true;
      }
      OutputMediator.println("Risposta errata! ");

      return false;

   }

}
